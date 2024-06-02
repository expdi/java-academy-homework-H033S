package com.expeditors.adoptionservice.dao.jdbc.client;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.dao.jdbc.PetSqlQueries;
import com.expeditors.adoptionservice.dao.utils.profiles.Profiles;
import com.expeditors.adoptionservice.domain.entities.Pet;
import com.expeditors.adoptionservice.domain.entities.PetBreed;
import com.expeditors.adoptionservice.domain.entities.PetType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Repository
@Profile(Profiles.JDBC_CLIENT)
public class PetJdbcDaoClient implements BaseDao<Pet> {

    private final DataSource dataSource;

    public PetJdbcDaoClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Pet> findAll() {

        JdbcClient client = JdbcClient.create(dataSource);
        return client
                .sql(PetSqlQueries.getFindAllQuery())
                .query(
                        (rSet, rNum) -> new Pet(
                                rSet.getInt("id"),
                                PetBreed.valueOf(rSet.getString("breed")),
                                PetType.valueOf(rSet.getString("type")),
                                rSet.getString("name")
                        )
                )
                .list();
    }

    @Override
    public Pet findById(int id) {

        JdbcClient client = JdbcClient.create(dataSource);
        var petFound = client
                .sql(PetSqlQueries.getFindByIdQuery())
                .param(1, id)
                .query(
                        (rSet, rNum) -> new Pet(
                                rSet.getInt("id"),
                                PetBreed.valueOf(rSet.getString("breed")),
                                PetType.valueOf(rSet.getString("type")),
                                rSet.getString("name")
                        )
                )
                .optional();

        return petFound.orElse(null);
    }

    @Override
    public Pet insert(Pet pet) {

        JdbcClient client = JdbcClient.create(dataSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        client
                .sql(PetSqlQueries.getInsertQuery())
                .param(1, pet.getPetName())
                .param(2, pet.getPetBreed().name())
                .param(3, pet.getPetType().name())
                .update(keyHolder);

        if(Objects.isNull(keyHolder.getKey())){
            return null;
        }

        pet.setId(keyHolder.getKey().intValue());
        return pet;
    }

    @Override
    public boolean update(Pet pet) {

        JdbcClient client = JdbcClient.create(dataSource);
        var rowsAffected = client
                .sql(PetSqlQueries.getUpdateQuery())
                .param(1, pet.getPetName())
                .param(2, pet.getPetBreed().name())
                .param(3, pet.getPetType().name())
                .param(4, pet.getId())
                .update();

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(int id) {

        JdbcClient client = JdbcClient.create(dataSource);
        var rowsAffected = client
                .sql(PetSqlQueries.getDeleteQuery())
                .param(1, id)
                .update();

        return rowsAffected > 0;
    }
}
