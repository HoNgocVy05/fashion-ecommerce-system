package com.fashion.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class TestDbController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/test-db")
    public String test() throws Exception {
        Connection conn = dataSource.getConnection();
        return conn.isValid(2) ? "DB OK" : "DB FAIL";
    }
}