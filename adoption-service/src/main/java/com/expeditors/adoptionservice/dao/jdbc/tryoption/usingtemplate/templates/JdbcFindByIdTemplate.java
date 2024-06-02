package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate.templates;

import com.expeditors.adoptionservice.domain.AbstractEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JdbcFindByIdTemplate<TEntity extends AbstractEntity>
        extends JdbcAbstractBaseTemplate  {
    public JdbcFindByIdTemplate(DataSource source) {
        super(source);
    }

    public TEntity findById(int id, String sql) {

        TEntity entity = null;

        try (
                Connection conn = getConnection();
                PreparedStatement pStmt = conn.prepareStatement(sql);
        ) {
            pStmt.setInt(1, id);

            try(ResultSet rSet = pStmt.executeQuery()){

                if(rSet.next()){
                    entity = mapItem(rSet);
                }
                else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    public abstract TEntity mapItem(ResultSet rSet) throws SQLException;
}
