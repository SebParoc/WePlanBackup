package com.bakafulteam.weplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class WePlanApplication {

    public static void main (String[] args) {
        new File(System.getProperty("user.home") + File.separator + "WePlanFiles").mkdir();
        SpringApplication.run(WePlanApplication.class, args);
    }

}
