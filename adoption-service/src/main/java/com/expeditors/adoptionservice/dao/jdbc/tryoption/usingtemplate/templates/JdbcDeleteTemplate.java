package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate.templates;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcDeleteTemplate
        extends JdbcAbstractBaseTemplate{

    public JdbcDeleteTemplate(DataSource source) {
        super(source);
    }

    public boolean delete(int id, String sql) {

        int numberOfRowsDeleted = 0;

        try(
                Connection conn = getConnection();
                PreparedStatement pStmt = conn.prepareStatement(sql);
                ){

            pStmt.setInt(1, id);
            numberOfRowsDeleted = pStmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return numberOfRowsDeleted > 0;
    }
}
