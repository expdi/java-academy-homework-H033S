package com.expeditors.adoptionservice.dao.jdbc;

public class PetSqlQueries {
    public static String getFindAllQuery() {

        return """
               SELECT ID, NAME, BREED, TYPE FROM PET
               """;
    }

    public static String getFindByIdQuery() {
        return """
                SELECT ID, NAME, BREED, TYPE FROM PET
                WHERE ID = ?
                """;
    }

    public static String getInsertQuery() {
        return """
                INSERT INTO PET (NAME, BREED, TYPE)
                VALUES (?,  ?, ?)
                """;
    }

    public static String getUpdateQuery() {
        return """
                UPDATE PET SET
                    NAME = ?,
                    BREED = ?,
                    TYPE = ?
                WHERE ID = ?
                """;
    }

    public static String getDeleteQuery() {

        return """
                DELETE FROM PET
                WHERE ID = ?
                """;
    }
}
