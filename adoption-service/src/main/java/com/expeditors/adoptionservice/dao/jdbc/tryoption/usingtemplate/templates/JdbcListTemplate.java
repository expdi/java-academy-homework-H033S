package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate.templates;

import com.expeditors.adoptionservice.domain.AbstractEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcListTemplate<TEntity extends AbstractEntity>
        extends JdbcAbstractBaseTemplate {

    public JdbcListTemplate(DataSource source) {
        super(source);
    }

    public abstract TEntity mapItem(ResultSet rSet) throws SQLException;

    public List<TEntity> findAll(String sql){
        List<TEntity> entities = new ArrayList<>();

        try(
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rSet = stmt.executeQuery(sql);
        ) {

            while (rSet.next()){
                TEntity entity = mapItem(rSet);
                entities.add(entity);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entities;
    }
}
