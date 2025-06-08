package com.ttknp.onetoonerunner;

import com.ttknp.connectdatasourceservice.ConnectDatasourceService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.ttknp"}) // do not forget this config if you work as microservice concept (default it looks at /runner so i don't want)
@SpringBootApplication
public class OneToOneRunnerApplication extends ConnectDatasourceService {

    public static void main(String[] args) {
        //SpringApplication.run(OneToOneRunnerApplication.class, args);
        runSpringBootDynamic(OneToOneRunnerApplication.class, args);
    }

}
