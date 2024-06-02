package com.expeditors.adoptionservice.dao.jdbc.client;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.dao.jdbc.AdopterSqlQueries;
import com.expeditors.adoptionservice.domain.entities.Adopter;
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
public class AdopterJdbcDaoClient implements BaseDao<Adopter> {

    private final DataSource dataSource;

    public AdopterJdbcDaoClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Adopter> findAll() {

        JdbcClient client = JdbcClient.create(dataSource);
        return client
                .sql(AdopterSqlQueries.getFindAllQuery())
                .query( (rSet, rNumber) -> new Adopter(
                        rSet.getInt("id"),
                        rSet.getString("name"),
                        rSet.getString("phone_number")
                ))
                .list();
    }

    @Override
    public Adopter findById(int id) {

        JdbcClient client = JdbcClient.create(dataSource);
        var adopterFound = client
                .sql(AdopterSqlQueries.getFindByIdQuery())
                .param(1, id)
                .query( (rSet, rNumber) -> new Adopter(
                        rSet.getInt("id"),
                        rSet.getString("name"),
                        rSet.getString("phone_number")
                ))
                .optional();
        return adopterFound.orElse(null);
    }

    @Override
    public Adopter insert(Adopter adopter) {

        JdbcClient client = JdbcClient.create(dataSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        client
                .sql(AdopterSqlQueries.getInsertQuery())
                .param(1, adopter.getAdopterName())
                .param(2, adopter.getPhoneNumber())
                .update(keyHolder);

        if(Objects.isNull(keyHolder.getKey())){
            return null;
        }

        adopter.setId(keyHolder.getKey().intValue());
        return adopter;
    }

    @Override
    public boolean update(Adopter adopter) {

        JdbcClient client = JdbcClient.create(dataSource);
        var rowsAffected = client
                .sql(AdopterSqlQueries.getUpdateQuery())
                .param(1, adopter.getAdopterName())
                .param(2, adopter.getPhoneNumber())
                .param(3, adopter.getId())
                .update();

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(int id) {

        JdbcClient client = JdbcClient.create(dataSource);
        var rowsAffected = client
                .sql(AdopterSqlQueries.getDeleteQuery())
                .param(1, id)
                .update();
        return rowsAffected > 0;
    }
}
