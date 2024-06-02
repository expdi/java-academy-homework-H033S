package com.expeditors.adoptionservice.dao.jdbc.template;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.dao.jdbc.AdoptionSqlQueries;
import com.expeditors.adoptionservice.dao.jdbc.JdbcAdoptionEntityFactory;
import com.expeditors.adoptionservice.domain.entities.*;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.JDBC_TEMPLATE;

@Repository
@Profile(value = {JDBC_TEMPLATE})
public class AdoptionJdbcDaoTemplate
        implements BaseDao<Adoption> {

    private final DataSource dataSource;

    public AdoptionJdbcDaoTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Adoption> findAll() {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.query(
                AdoptionSqlQueries.getFindAllQuery(),
                (rSet, rNumber) -> JdbcAdoptionEntityFactory.generateAdoption(rSet)
        );
    }

    @Override
    public Adoption findById(int id) {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        try{
            return template.queryForObject(
                    AdoptionSqlQueries.getFindByIdQuery(),
                    (rSet, rNumber) -> JdbcAdoptionEntityFactory.generateAdoption(rSet),
                    id
            );
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public Adoption insert(Adoption adoption) {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(
                con -> {
                    PreparedStatement pStmt = con.prepareStatement(
                            AdoptionSqlQueries.getInsertQuery(),
                            Statement.RETURN_GENERATED_KEYS
                    );

                    pStmt.setDate(1, Date.valueOf(adoption.getAdoptionDate()));
                    pStmt.setInt(2, adoption.getPet().getId());
                    pStmt.setInt(3, adoption.getAdopter().getId());
                    return pStmt;
                },
                keyHolder
        );

        if(Objects.isNull(keyHolder.getKeys())){
            return null;
        }
        adoption.setId((int)keyHolder.getKeys().get("id"));
        return adoption;

    }

    @Override
    public boolean update(Adoption adoption) {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        var rowsAffected = template.update(
                con -> {
                    PreparedStatement pStmt = con.prepareStatement(AdoptionSqlQueries.getUpdateQuery());
                    pStmt.setDate(1, Date.valueOf(adoption.getAdoptionDate()));
                    pStmt.setInt(2, adoption.getPet().getId());
                    pStmt.setInt(3, adoption.getAdopter().getId());
                    pStmt.setInt(4, adoption.getId());
                    return pStmt;
                }
        );

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(int id) {

       JdbcTemplate template = new JdbcTemplate(dataSource);
       var rowsAffected = template.update(
               AdoptionSqlQueries.getDeleteQuery(),
               id
       );

       return rowsAffected > 0;
    }


}
