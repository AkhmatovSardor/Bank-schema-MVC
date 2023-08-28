package com.example.Bank.Schema.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Value(value = "${spring.datasource.url}")
    private String url;
    @Value(value = "${spring.datasource.username}")
    private String user;
    @Value(value = "${spring.datasource.password}")
    private String password;

    @PostConstruct
    public void initMethod() throws SQLException {
        System.out.println("This init method execute!");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement roleTrigger = connection.prepareStatement("create or replace function create_role() " +
                "returns trigger " +
                "language plpgsql " +
                "as " +
                "$$ " +
                "BEGIN " +
                "insert into authorities (authority, customer_id, username) " +
                "values ('USER', new.customer_id, new.username);\n" +
                "return new;\n" +
                "END\n" +
                "$$;\n" +
                "create or replace trigger create_role\n" +
                "BEFORE insert\n" +
                "on customer\n" +
                "for each row\n" +
                "execute function create_role();");
        roleTrigger.execute();
    }

    @PreDestroy
    public void destroyMethod() {
        System.err.println("This destroy method execute!");
    }
}
