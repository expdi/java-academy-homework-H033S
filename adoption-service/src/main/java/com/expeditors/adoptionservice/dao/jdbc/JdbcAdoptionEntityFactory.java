package com.expeditors.adoptionservice.dao.jdbc;


import com.expeditors.adoptionservice.domain.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcAdoptionEntityFactory {

    public static Adoption generateAdoption(ResultSet rSet) throws SQLException {

        return new Adoption(
                rSet.getInt("adoption_id"),
                generateAdopter(rSet),
                generatePet(rSet),
                rSet.getDate("adoption_date").toLocalDate()
        );
    }

    public static Pet generatePet(ResultSet rSet) throws SQLException {

        return new Pet(
                rSet.getInt("pet_id"),
                PetBreed.valueOf(rSet.getString("pet_breed")),
                PetType.valueOf(rSet.getString("pet_type")),
                rSet.getString("pet_name")
        );
    }

    public static Adopter generateAdopter(ResultSet rSet) throws SQLException {

        return new Adopter(
                rSet.getInt("adopter_id"),
                rSet.getString("adopter_name"),
                rSet.getString("adopter_phone_number")
        );
    }
}
