package com.ozf.laiyw.manage.cas.auth;

import org.apache.shiro.dao.DataAccessException;
import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.security.GeneralSecurityException;

public class CustomQueryDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

    @NotNull
    private String sql;

    @Override
    protected HandlerResult authenticateUsernamePasswordInternal(
            UsernamePasswordCredential credential)
            throws GeneralSecurityException, PreventedException {
        if (this.sql.isEmpty() || getJdbcTemplate() == null) {
            throw new GeneralSecurityException("Authentication handler is not configured correctly.");
        }

        String username = credential.getUsername();
        String password = credential.getPassword();
        try {
            String dbPassword = getJdbcTemplate().queryForObject(this.sql, String.class, username);
            if (!password.equals(dbPassword))
                throw new FailedLoginException("Password does not match value on record.");
        } catch (final IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0)
                throw new AccountNotFoundException(username + " not found with SQL query.");
            else
                throw new FailedLoginException("Multiple records found for " + username);
        } catch (DataAccessException e) {
            throw new PreventedException("SQL exception while executing query for " + username, e);
        }
        return createHandlerResult(credential, this.principalFactory.createPrincipal(username), null);
    }

    @Autowired
    public void setSql(@Value("${cas.jdbc.authn.query.sql}") final String sql) {
        this.sql = sql;
    }

    @Override
    @Autowired(required = false)
    public void setDataSource(@Qualifier("queryDatabaseDataSource") DataSource dataSource) {
        super.setDataSource(dataSource);
    }
}
