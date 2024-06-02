package com.expeditors.adoptionservice.dao.jdbc;

public class AdoptionSqlQueries {

    public static String getFindAllQuery(){

        return """
                SELECT
                    ADOPTER.ID              AS "ADOPTER_ID",
                    ADOPTER.NAME            AS "ADOPTER_NAME",
                    ADOPTER.PHONE_NUMBER    AS "ADOPTER_PHONE_NUMBER",
                    PET.ID                  AS "PET_ID",
                    PET.NAME                AS "PET_NAME",
                    PET.TYPE                AS "PET_TYPE",
                    PET.BREED               AS "PET_BREED",
                    ADOPTION.ID             AS "ADOPTION_ID",
                    ADOPTION.ADOPTION_DATE  AS "ADOPTION_DATE"
                FROM ADOPTION
                    INNER JOIN ADOPTER ON ADOPTER.ID = ADOPTION.ADOPTER_ID
                    INNER JOIN PET ON PET.ID = ADOPTION.PET_ID
                """;
    }

    public static String getFindByIdQuery() {

        return """
                SELECT
                    ADOPTER.ID              AS "ADOPTER_ID",
                    ADOPTER.NAME            AS "ADOPTER_NAME",
                    ADOPTER.PHONE_NUMBER    AS "ADOPTER_PHONE_NUMBER",
                    PET.ID                  AS "PET_ID",
                    PET.NAME                AS "PET_NAME",
                    PET.TYPE                AS "PET_TYPE",
                    PET.BREED               AS "PET_BREED",
                    ADOPTION.ID             AS "ADOPTION_ID",
                    ADOPTION.ADOPTION_DATE  AS "ADOPTION_DATE"
                FROM ADOPTION
                    INNER JOIN ADOPTER ON ADOPTER.ID = ADOPTION.ADOPTER_ID
                    INNER JOIN PET ON PET.ID = ADOPTION.PET_ID
                WHERE ADOPTION.ID = ?
                """;
    }

    public static String getInsertQuery() {

        return """
               INSERT INTO ADOPTION (ADOPTION_DATE, PET_ID, ADOPTER_ID)
               VALUES (?, ?, ?)
               """;
    }

    public static String getUpdateQuery() {

        return """
               UPDATE ADOPTION SET
                    ADOPTION_DATE = ?,
                    PET_ID = ?,
                    ADOPTER_ID = ?
               WHERE ID = ?
               """;
    }

    public static String getDeleteQuery() {

        return """
                DELETE FROM ADOPTION
                WHERE ID = ?
                """;
    }
}
