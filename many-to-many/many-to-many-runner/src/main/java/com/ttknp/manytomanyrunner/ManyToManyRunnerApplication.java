package com.ttknp.manytomanyrunner;

import com.ttknp.connectdatasourceservice.ConnectDatasourceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.ttknp"}) // do not forget this config if you work as microservice concept (default it looks at /runner so i don't want)
@SpringBootApplication
public class ManyToManyRunnerApplication extends ConnectDatasourceService {

    public static void main(String[] args) {
        runSpringBootDynamic(ManyToManyRunnerApplication.class, args);
    }

}
