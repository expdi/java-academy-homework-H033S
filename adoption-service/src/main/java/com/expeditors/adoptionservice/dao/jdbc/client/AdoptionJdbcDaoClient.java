package com.expeditors.adoptionservice.dao.jdbc.client;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.dao.jdbc.AdoptionSqlQueries;
import com.expeditors.adoptionservice.dao.jdbc.JdbcAdoptionEntityFactory;
import com.expeditors.adoptionservice.domain.entities.Adoption;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.JDBC_CLIENT;

@Repository
@Profile(JDBC_CLIENT)
public class AdoptionJdbcDaoClient implements BaseDao<Adoption> {

    private final DataSource dataSource;

    public AdoptionJdbcDaoClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Adoption> findAll() {

        JdbcClient client = JdbcClient.create(dataSource);
        return client
                .sql(AdoptionSqlQueries.getFindAllQuery())
                .query((rSet, rNumber) -> JdbcAdoptionEntityFactory.generateAdoption(rSet))
                .list();
    }

    @Override
    public Adoption findById(int id) {

        JdbcClient client = JdbcClient.create(dataSource);
        var adoptionFound = client
                .sql(AdoptionSqlQueries.getFindByIdQuery())
                .param(1, id)
                .query((rSet, rNumber) -> JdbcAdoptionEntityFactory.generateAdoption(rSet))
                .optional();
        return adoptionFound.orElse(null);
    }

    @Override
    public Adoption insert(Adoption adoption) {

        JdbcClient client = JdbcClient.create(dataSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        client
                .sql(AdoptionSqlQueries.getInsertQuery())
                .param(1, adoption.getAdoptionDate())
                .param(2, adoption.getPet().getId())
                .param(3, adoption.getAdopter().getId())
                .update(keyHolder);

        if(Objects.isNull(keyHolder.getKeys())){
            return null;
        }

        adoption.setId((int)keyHolder.getKeys().get("id"));
        return adoption;
    }

    @Override
    public boolean update(Adoption adoption) {

        JdbcClient client = JdbcClient.create(dataSource);
        var rowsAffected = client
                .sql(AdoptionSqlQueries.getUpdateQuery())
                .param(1, adoption.getAdoptionDate())
                .param(2, adoption.getPet().getId())
                .param(3, adoption.getAdopter().getId())
                .param(4, adoption.getId())
                .update();

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(int id) {

        JdbcClient client = JdbcClient.create(dataSource);
        var rowsAffected = client
                .sql(AdoptionSqlQueries.getDeleteQuery())
                .param(1, id)
                .update();

        return rowsAffected > 0;
    }
}
