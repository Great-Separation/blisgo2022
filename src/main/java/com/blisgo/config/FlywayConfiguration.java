package com.blisgo.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfiguration {
    //hibernate가 실행된 이후에 flyway가 수행됨
    //flyway_schema_history가 지워지면 마이그레이션 수행됨
//    @Autowired
//    public FlywayConfiguration(DataSource dataSource) {
//        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
//    }
}
