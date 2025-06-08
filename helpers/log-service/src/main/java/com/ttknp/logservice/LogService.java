package com.ttknp.logservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogService {

   public Logger log;

    public LogService(Class<?> aClass) {
        this.log = LoggerFactory.getLogger(aClass);
    }



}
