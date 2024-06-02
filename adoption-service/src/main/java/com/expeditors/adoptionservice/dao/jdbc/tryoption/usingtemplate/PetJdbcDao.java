package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.dao.jdbc.PetSqlQueries;
import com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate.templates.*;
import com.expeditors.adoptionservice.domain.entities.Pet;
import com.expeditors.adoptionservice.domain.entities.PetBreed;
import com.expeditors.adoptionservice.domain.entities.PetType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.*;

@Repository
@Profile({JDBC})
public class PetJdbcDao implements BaseDao<Pet> {

    private final DataSource dataSource;

    public PetJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Pet> findAll() {

        JdbcListTemplate<Pet> template = new JdbcListTemplate<>(dataSource) {
            @Override
            public Pet mapItem(ResultSet rSet) throws SQLException {
                return new Pet(
                        rSet.getInt("id"),
                        PetBreed.valueOf(rSet.getString("breed")),
                        PetType.valueOf(rSet.getString("type")),
                        rSet.getString("name")
                );
            }
        };

        return template.findAll(PetSqlQueries.getFindAllQuery());
    }

    @Override
    public Pet findById(int id) {

        JdbcFindByIdTemplate<Pet> template = new JdbcFindByIdTemplate<>(dataSource) {
            @Override
            public Pet mapItem(ResultSet rSet) throws SQLException {
                return new Pet(
                        rSet.getInt("id"),
                        PetBreed.valueOf(rSet.getString("breed")),
                        PetType.valueOf(rSet.getString("type")),
                        rSet.getString("name")
                );
            }
        };

        return template.findById(id, PetSqlQueries.getFindByIdQuery());
    }

    @Override
    public Pet insert(Pet pet) {

        JdbcInsertTemplate<Pet> template = new JdbcInsertTemplate<>(dataSource) {
            @Override
            public void prepareStatement(PreparedStatement pStmt, Pet petToInsert) throws SQLException {
                pStmt.setString(1, petToInsert.getPetName());
                pStmt.setString(2, petToInsert.getPetBreed().name());
                pStmt.setString(3, petToInsert.getPetType().name());
            }
        };

        return template.insert(pet, PetSqlQueries.getInsertQuery());
    }

    @Override
    public boolean update(Pet pet) {

        JdbcUpdateTemplate<Pet> template = new JdbcUpdateTemplate<>(dataSource) {
            @Override
            public void prepareStatement(PreparedStatement pStmt, Pet petToInsert) throws SQLException {
                pStmt.setString(1, petToInsert.getPetName());
                pStmt.setString(2, petToInsert.getPetBreed().name());
                pStmt.setString(3, petToInsert.getPetType().name());
                pStmt.setInt(4, petToInsert.getId());
            }
        };

        return template.update(pet, PetSqlQueries.getUpdateQuery());
    }

    @Override
    public boolean delete(int id) {

        JdbcDeleteTemplate template = new JdbcDeleteTemplate(dataSource) {};
        return template.delete(id, PetSqlQueries.getDeleteQuery());
    }
}
