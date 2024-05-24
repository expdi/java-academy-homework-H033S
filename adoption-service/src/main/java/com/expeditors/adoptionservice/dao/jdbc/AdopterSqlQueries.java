package com.expeditors.adoptionservice.dao.jdbc;

public class AdopterSqlQueries {

    public static String getFindAllQuery(){

        return "SELECT ID, NAME, PHONE_NUMBER FROM ADOPTER";
    }

    public static String getFindByIdQuery() {

        return """
        SELECT ID, NAME, PHONE_NUMBER FROM ADOPTER
        WHERE ID = ?
        """;
    }

    public static String getInsertQuery() {

        return """
        INSERT INTO ADOPTER (NAME, PHONE_NUMBER)
        VALUES (?, ?)
        """;
    }

    public static String getUpdateQuery() {

        return "";
    }

    public static String getDeleteQuery() {

        return "";
    }
}
