package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate.templates;

import com.expeditors.adoptionservice.domain.AbstractEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcUpdateTemplate<TEntity extends AbstractEntity>
        extends JdbcAbstractBaseTemplate{

    public JdbcUpdateTemplate(DataSource source) {
        super(source);
    }

    public boolean update(TEntity entity, String sql){

        int numberOfRowsUpdated = 0;

        try(
                Connection conn = getConnection();
                PreparedStatement pStmt = conn.prepareStatement(sql);
        ) {
            prepareStatement(pStmt, entity);
            numberOfRowsUpdated = pStmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return numberOfRowsUpdated > 0;
    }

    public abstract void prepareStatement(PreparedStatement pStmt, TEntity entity) throws SQLException;

}
