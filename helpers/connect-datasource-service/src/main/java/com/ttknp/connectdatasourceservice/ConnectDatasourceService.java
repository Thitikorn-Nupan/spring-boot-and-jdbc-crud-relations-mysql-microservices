package com.ttknp.connectdatasourceservice;

import com.ttknp.connectdatasourceservice.services.UsefulConfigService;
import com.ttknp.logservice.LogService;
import org.springframework.boot.SpringApplication;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


public abstract class ConnectDatasourceService {

    private final static LogService SERVICE = new LogService(ConnectDatasourceService.class);

    /*protected static void runSpringBootWithoutImportResourceAnnotation(Class<?> theClass, Set<String> absolutePathsBeanOnXml , String... theArgs) {
        SERVICE.log.info("Starting Spring Boot Application");
        // ConfigurableEnvironment configurableEnvironment = null;
        // start app
        SpringApplication application = new SpringApplication(theClass);
        // application.addListeners(new ApplicationPidFileWriter());
        // set resource paths to app
        application.setSources(absolutePathsBeanOnXml);
        // application.setSources(new HashSet<>(Collections.singletonList(getPathContextMysql())));
        // get env from app
        ConfigurableEnvironment configurableEnvironment = application.run(theArgs).getEnvironment();
        SERVICE.log.info(
                "visual name {} \nenvironment resource {} \n applicaiton name & port {} & {} \n",
                ManagementFactory.getRuntimeMXBean().getName(),
                configurableEnvironment.getPropertySources(),
                configurableEnvironment.getProperty("spring.application.name"),
                configurableEnvironment.getProperty("server.port")
        );
        SERVICE.log.info("Spring Boot Application completed");
    }*/

    protected static void runSpringBoot(Class<?> theClass, Set<String> absolutePathsBeanOnXml , String... theArgs) {
        SERVICE.log.info("Starting Spring Boot Application");
        // start app
        SpringApplication application = new SpringApplication(theClass);
        // set resource paths to app
        application.setSources(absolutePathsBeanOnXml);
        // run app
        application.run(theArgs);
        SERVICE.log.info("Spring Boot Application completed");
    }

    protected static void runSpringBoot(Class<?> theClass, String... theArgs) {
        SERVICE.log.info("Starting Spring Boot Application");
        SpringApplication application = new SpringApplication(theClass);
        String absolutePathsBeanOnXml = UsefulConfigService.getProperties().getProperty("abs.path.context.mysql");
        application.setSources(new HashSet<>(Collections.singleton(absolutePathsBeanOnXml)));
        application.run(theArgs);
        SERVICE.log.info("Spring Boot Application completed");
    }

    protected static void runSpringBootDynamic(Class<?> theClass, String... theArgs) {
        SERVICE.log.info("Starting Spring Boot Application");
        SpringApplication application = new SpringApplication(theClass);
        Properties properties = UsefulConfigService.getProperties();
        String projectName = properties.getProperty("main.project.name"); //
        String baseDirOnRunner = properties.getProperty("main.basedir"); // it follows dir on runner
        String baseDirRelativePath = properties.getProperty("main.basedir.relativePath"); // it's parent module
        final String pathContextMysql = UsefulConfigService.getPathContextMysql(baseDirOnRunner,projectName,baseDirRelativePath);
        application.setSources(new HashSet<>(Collections.singleton(pathContextMysql)));
        application.run(theArgs);
        SERVICE.log.info("Spring Boot Application completed");
    }


}


