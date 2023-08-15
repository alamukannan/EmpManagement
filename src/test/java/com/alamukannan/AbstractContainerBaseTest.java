package com.alamukannan;


import org.testcontainers.containers.MySQLContainer;
public abstract class AbstractContainerBaseTest {

    protected  static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest");
        MY_SQL_CONTAINER.start();
    }
}