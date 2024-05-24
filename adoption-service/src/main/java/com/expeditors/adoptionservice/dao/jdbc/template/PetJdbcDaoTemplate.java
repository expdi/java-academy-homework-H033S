package com.expeditors.adoptionservice.dao.jdbc.template;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.dao.jdbc.PetSqlQueries;
import com.expeditors.adoptionservice.domain.entities.Pet;
import com.expeditors.adoptionservice.domain.entities.PetBreed;
import com.expeditors.adoptionservice.domain.entities.PetType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Objects;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.JDBC_TEMPLATE;

@Repository
@Profile({JDBC_TEMPLATE})
public class PetJdbcDaoTemplate
        implements BaseDao<Pet> {

    private final DataSource dataSource;

    public PetJdbcDaoTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Pet> findAll() {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.query(
                PetSqlQueries.getFindAllQuery(),
                new PetRowMapper());
    }

    @Override
    public Pet findById(int id) {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        try{
            return template.queryForObject(
                    PetSqlQueries.getFindByIdQuery(),
                    new PetRowMapper(),
                    id
            );
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Pet insert(Pet pet) {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(
                (conn) -> {

                    PreparedStatement stmt = conn.prepareStatement(
                            PetSqlQueries.getInsertQuery(),
                            Statement.RETURN_GENERATED_KEYS);

                    stmt.setString(1, pet.getPetName());
                    stmt.setString(2, pet.getPetType().name());
                    stmt.setString(3, pet.getPetType().name());
                    return stmt;
                },
                keyHolder);

        if(Objects.isNull(keyHolder.getKey())){
            return null;
        }
        pet.setId(keyHolder.getKey().intValue());
        return pet;
    }

    @Override
    public boolean update(Pet pet) {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        var updatedRows = template.update(
                (conn) -> {
                    PreparedStatement stmt = conn.prepareStatement(PetSqlQueries.getUpdateQuery());
                    stmt.setString(1, pet.getPetName());
                    stmt.setString(2, pet.getPetType().name());
                    stmt.setString(3, pet.getPetType().name());
                    stmt.setInt(4, pet.getId());
                    return stmt;
                });
        return updatedRows > 0;
    }

    @Override
    public boolean delete(int id) {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        var rowsDeleted = template.update(PetSqlQueries.getDeleteQuery(), id);
        return rowsDeleted > 0;
    }
}

class PetRowMapper implements RowMapper<Pet>{

    @Override
    public Pet mapRow(ResultSet rSet, int rowNum) throws SQLException {
        return new Pet(
                rSet.getInt("id"),
                PetBreed.valueOf(rSet.getString("breed")),
                PetType.valueOf(rSet.getString("type")),
                rSet.getString("name"));
    }
}
