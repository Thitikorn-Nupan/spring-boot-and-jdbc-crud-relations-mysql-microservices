package com.ttknp.runner;

import com.ttknp.connectdatasourceservice.ConnectDatasourceService;
import com.ttknp.logservice.LogService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {"com.ttknp"}) // do not forget this config if you work as microservice concept (default it looks at /runner so i don't want)
@SpringBootApplication
public class OneToManyRunnerApplication extends ConnectDatasourceService  {

    private final static LogService SERVICE = new LogService(OneToManyRunnerApplication.class);

    /*
    @Value("${path.context.mysql}") // work but on static prop not work
    private String pathContextMysql;
    */

    /*
    // on bean (xml) no need a driver but runner must have a driver
    private static final Set<String> ABS_PATHS_BEAN_XML = new HashSet<>(Collections.singletonList(
            "file:B:/practice-java-one-jetbrains/spring-boot-skills/lab_core_37/understand-relations-with-jdbc-concept-microservice/parent/application-resource-properties/src/main/resources/xml/spring-context-mysql-database.xml"
    ));
    */


    public static void main(String[] args) {
        // this runSpringBoot(...) work for running the context such as read resource database, and run
        runSpringBoot(OneToManyRunnerApplication.class,args);
    }


}
