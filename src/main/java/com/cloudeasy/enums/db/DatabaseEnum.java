package com.cloudeasy.enums.db;

/**
 * Enum contains databases which we will be able to install on instance
 */
public enum DatabaseEnum {


    MySQL("MySQL"),
    POSTGRE_SQL("PostgreSQL"),
    MONGO_DB("MongoDB"),
    MARIA_DB("MariaDB");

    private String databaseName;

    DatabaseEnum(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
