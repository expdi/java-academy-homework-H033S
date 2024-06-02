package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.dao.jdbc.AdoptionSqlQueries;
import com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate.templates.*;
import com.expeditors.adoptionservice.domain.entities.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.expeditors.adoptionservice.dao.utils.profiles.Profiles.*;

@Repository
@Profile({JDBC})
public class AdoptionJdbcDao
        implements BaseDao<Adoption> {
    private final DataSource dataSource;

    public AdoptionJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Adoption> findAll() {

        JdbcListTemplate<Adoption> template = new JdbcListTemplate<>(dataSource) {
            @Override
            public Adoption mapItem(ResultSet rSet) throws SQLException {
                return createAdoptionFromResultSet(rSet);
            }
        };

        return template.findAll(AdoptionSqlQueries.getFindAllQuery());
    }

    @Override
    public Adoption findById(int adoptionId) {

        JdbcFindByIdTemplate<Adoption> template = new JdbcFindByIdTemplate<>(dataSource) {
            @Override
            public Adoption mapItem(ResultSet rSet) throws SQLException {
                return createAdoptionFromResultSet(rSet);
            }
        };

        return template.findById(adoptionId, AdoptionSqlQueries.getFindByIdQuery());
    }

    private static Adoption createAdoptionFromResultSet(ResultSet rSet) throws SQLException {

        return new Adoption(
                rSet.getInt("adoption_id"),
                new Adopter(
                        rSet.getInt("adopter_id"),
                        rSet.getString("adopter_name"),
                        rSet.getString("adopter_phone_number")
                ),
                new Pet(
                        rSet.getInt("pet_id"),
                        PetBreed.valueOf(rSet.getString("pet_breed")),
                        PetType.valueOf(rSet.getString("pet_type")),
                        rSet.getString("pet_name")
                ),
                rSet.getDate("adoption_date").toLocalDate()
        );
    }

    @Override
    public Adoption insert(Adoption adoption) {

        JdbcInsertTemplate<Adoption> template = new JdbcInsertTemplate<>(dataSource) {
            @Override
            public void prepareStatement(
                    PreparedStatement pStmt,
                    Adoption adoptionToInsert) throws SQLException {

                pStmt.setDate(1, Date.valueOf(adoptionToInsert.getAdoptionDate()));
                pStmt.setInt(2, adoptionToInsert.getPet().getId());
                pStmt.setInt(3, adoptionToInsert.getAdopter().getId());
            }
        };

        return template.insert(adoption, AdoptionSqlQueries.getInsertQuery());
    }

    @Override
    public boolean update(Adoption adoption) {

        JdbcUpdateTemplate<Adoption> template = new JdbcUpdateTemplate<>(dataSource) {
            @Override
            public void prepareStatement(
                    PreparedStatement pStmt,
                    Adoption adoptionToUpdate) throws SQLException {

                pStmt.setDate(1, Date.valueOf(adoptionToUpdate.getAdoptionDate()));
                pStmt.setInt(2, adoptionToUpdate.getPet().getId());
                pStmt.setInt(3, adoptionToUpdate.getAdopter().getId());
                pStmt.setInt(4, adoptionToUpdate.getId());
            }
        };

        return template.update(adoption, AdoptionSqlQueries.getUpdateQuery());
    }

    @Override
    public boolean delete(int id) {

        JdbcDeleteTemplate template = new JdbcDeleteTemplate(dataSource) {};
        return template.delete(id, AdoptionSqlQueries.getDeleteQuery());
    }
}
