package com.ttknp.manytomanyrestapicontroller.dto;

import com.ttknp.manytomanyrestapicontroller.entities.Actor;
import com.ttknp.manytomanyrestapicontroller.entities.Movie;
import com.ttknp.manytomanyrestapicontroller.services.ModelJdbcExecute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ActorDTO extends ModelJdbcExecute<Actor> {

    private static final Logger log = LoggerFactory.getLogger(ActorDTO.class);

    @Override
    public List<Actor> findAll() {
        return jdbcSelectExecuteHelper.selectAll(Actor.class);
    }

    @Override
    public Set<Actor> findAllRelationsModels() {
        return null;
    }

    @Override
    public <U> List<U> findAllOnlyColumn(String columnName) {
        Class<?> typeClass = null;
        switch (columnName) {
            case "aid":
                typeClass = String.class;
                break;
            case "fullname":
                typeClass = String.class;
                break;
            case "born":
                typeClass = LocalDate.class;
                break;
            case "contact":
                typeClass = String.class;
                break;
        }
        return jdbcSelectExecuteHelper.selectAllOnlyColumn(Actor.class,typeClass,columnName);
    }

    @Override
    public <U> Actor findOneByPk(U pk) {
        return (Actor) jdbcSelectExecuteHelper.selectOne(Actor.class,"aid",pk);
    }

    public <U> Actor findOneRelationByPk(U pk) {
        return (Actor) jdbcSelectExecuteHelper.selectRelationWhereMain(Actor.class, Movie.class,"actors_movies","aid","mid",new ActorJoinMoviesResultSetExtractor(),pk);
    }

    @Override
    public Integer saveModel(Actor model) {
        try {
            return jdbcUpdateExecuteHelper.insertOne(Actor.class,model);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer saveRelationModel(String aid, String mid) {
        try {

            @Table(name = "actors_movies")
            class ActorMovie {

                private String aid;
                private String mid;

                public ActorMovie(String aid, String mid) {
                    this.aid = aid;
                    this.mid = mid;
                }

                public ActorMovie() {
                }

                public String getAid() {
                    return aid;
                }

                public void setAid(String aid) {
                    this.aid = aid;
                }

                public String getMid() {
                    return mid;
                }

                public void setMid(String mid) {
                    this.mid = mid;
                }

                @Override
                public String toString() {
                    final StringBuffer sb = new StringBuffer("ActorMovie{");
                    sb.append("aid='").append(aid).append('\'');
                    sb.append(", mid='").append(mid).append('\'');
                    sb.append('}');
                    return sb.toString();
                }
            }

            ActorMovie actorMovie = new ActorMovie(aid,mid);
            log.debug("actorMovie {}",actorMovie);
            return jdbcUpdateExecuteHelper.insertOne(ActorMovie.class,actorMovie);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <U> Integer updateModelByPk(Actor model, U pk) {
        try {
            model.setAid(pk.toString());
            return jdbcUpdateExecuteHelper.updateOne(Actor.class,"aid",model);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <U> Integer deleteModelByPk(U pk) {
        // this way can't remove which row has relation
        return jdbcUpdateExecuteHelper.deleteOne(Actor.class,"aid",pk);
    }

    private static class ActorJoinMoviesResultSetExtractor implements ResultSetExtractor<Actor> {
        @Override
        public Actor extractData(ResultSet rs) throws SQLException {
            Actor actor = new Actor();
            Set<Movie> movieAsSet = new HashSet<>(); // Note set won't cut the duplicate object if you forget set Override equals() and hashCode() on your POJOs

            while (rs.next()) {

                actor.setAid(rs.getString("aid"));
                actor.setFullname(rs.getString("fullname"));
                actor.setBorn(LocalDate.parse(rs.getString("born")));
                actor.setContact(rs.getString("contact"));

                Movie movie = new Movie();
                movie.setMid(rs.getString("mid"));
                movie.setTitle(rs.getString("title"));
                movie.setCategories(rs.getString("categories"));
                movie.setRate(rs.getBigDecimal("rate"));
                movie.setYear(LocalDate.parse(rs.getString("year")));

                movieAsSet.add(movie);
            }
            actor.setMovies(movieAsSet);
            return actor;
        }
    }
}
