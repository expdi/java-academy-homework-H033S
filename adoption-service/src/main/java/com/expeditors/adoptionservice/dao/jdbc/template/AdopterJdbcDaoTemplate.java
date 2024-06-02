package com.expeditors.adoptionservice.dao.jdbc.template;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.dao.jdbc.AdopterSqlQueries;
import com.expeditors.adoptionservice.domain.entities.Adopter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.JDBC_TEMPLATE;

@Repository
@Profile({JDBC_TEMPLATE})
public class AdopterJdbcDaoTemplate
        implements BaseDao<Adopter> {

    private final DataSource dataSource;

    public AdopterJdbcDaoTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Adopter> findAll() {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.query(
                AdopterSqlQueries.getFindAllQuery(),
                (rs, rn) -> new Adopter(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone_number")
                ));
    }

    @Override
    public Adopter findById(int id) {

        JdbcTemplate template = new JdbcTemplate(dataSource);

        try{
            return template.queryForObject(
                    AdopterSqlQueries.getFindByIdQuery(),
                    (rs, rowNum) -> new Adopter(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone_number")
                    ),
                    id
            );
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public Adopter insert(Adopter adopter) {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update((con) -> {
                PreparedStatement pStmt = con.prepareStatement(
                        AdopterSqlQueries.getInsertQuery(),
                        PreparedStatement.RETURN_GENERATED_KEYS);

                pStmt.setString(1, adopter.getAdopterName());
                pStmt.setString(2, adopter.getPhoneNumber());
                return pStmt;
                },
                keyHolder
        );

        if(Objects.isNull(keyHolder.getKey())){
            return null;
        }
        adopter.setId(keyHolder.getKey().intValue());
        return adopter;

    }

    @Override
    public boolean update(Adopter adopter) {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        int affectedRows = template.update(con -> {

            PreparedStatement pStmt = con.prepareStatement(AdopterSqlQueries.getUpdateQuery());
            pStmt.setString(1, adopter.getAdopterName());
            pStmt.setString(2, adopter.getPhoneNumber());
            pStmt.setInt(3, adopter.getId());
            return pStmt;
        });
        return affectedRows > 0;
    }

    @Override
    public boolean delete(int id) {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        int rowsAffected = template.update(
                con -> {
                    PreparedStatement pStmt = con.prepareStatement(AdopterSqlQueries.getDeleteQuery());
                    pStmt.setInt(1, id);
                    return pStmt;
                }
        );

        return rowsAffected > 0;
    }
}
