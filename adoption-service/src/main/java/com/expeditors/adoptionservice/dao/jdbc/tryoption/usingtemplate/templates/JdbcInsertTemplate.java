package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate.templates;

import com.expeditors.adoptionservice.domain.AbstractEntity;

import javax.sql.DataSource;
import java.sql.*;

public abstract class JdbcInsertTemplate<TEntity extends AbstractEntity>
        extends JdbcAbstractBaseTemplate{

    public JdbcInsertTemplate(DataSource source) {
        super(source);
    }

    public TEntity insert(TEntity entity, String sql){
        try(
                Connection conn = getConnection();
                PreparedStatement pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            prepareStatement(pStmt, entity);
            pStmt.executeUpdate();

            try(ResultSet rSet = pStmt.getGeneratedKeys()){
                rSet.next();
                entity.setId(rSet.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    public abstract void prepareStatement(PreparedStatement pStmt, TEntity entity) throws SQLException;
}
