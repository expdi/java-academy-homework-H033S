package com.expeditors.adoptionservice.dao.jdbc.tryoption.usingtemplate.templates;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcAbstractBaseTemplate {

    private final DataSource source;

    public JdbcAbstractBaseTemplate(DataSource source) {
        this.source = source;
    }

    protected Connection getConnection() throws SQLException {
        return source.getConnection();
    }
}
