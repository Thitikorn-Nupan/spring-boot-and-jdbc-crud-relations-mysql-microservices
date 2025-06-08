package com.ttknp.connectdatasourceservice.services;


import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UsefulConfigService {

    public static Properties getProperties() {
        // way to read prop file on current module
        final Properties properties = new Properties();
        try (InputStream input = new ClassPathResource("application.properties").getInputStream()) {
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getPathContextMysql(String baseDirOnRunner, String projectName , String baseDirRelativePath) {
        final int indexOfTarget = baseDirOnRunner.indexOf(projectName);
        final String relativePathTarget = "../";
        // Check if the target string
        if (indexOfTarget != -1) {
            // clear format
            baseDirOnRunner = baseDirOnRunner.replace("\\", "/");
            baseDirRelativePath = baseDirRelativePath.replace(relativePathTarget,""); // cut all ../
            // Calculate the end index (end of the target string)
            final int endIndexTarget = indexOfTarget + projectName.length();
            StringBuilder stringBuilder  = new StringBuilder();
            stringBuilder.append("file:");
            // substring from the beginning to the end of the target string
            stringBuilder.append(baseDirOnRunner.substring(0, endIndexTarget)); // base dir (on main project name)
            stringBuilder.append("/");
            stringBuilder.append(baseDirRelativePath);
            return stringBuilder.toString();
        }
        throw new RuntimeException("target not found");
    }


}
