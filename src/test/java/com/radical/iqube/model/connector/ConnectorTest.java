package com.radical.iqube.model.connector;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.After;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectorTest {
    private   MysqlDataSource ds;
    private Connection connection;
    public static final String dbPath = "java:comp/env/jdbc/iqubedbtest";

    @Mock
    private InitialContext context;

    @BeforeClass
    public void init(){
        this.context = Mockito.mock(InitialContext.class);
    }

    public Connection connect() throws SQLException {
        ds = new MysqlDataSource();
        ds.setDatabaseName("iqubedbtest");
        ds.setUser("test");
        ds.setPassword("test");
        ds.setURL("jdbc:mysql://localhost/iqubedbtest");

        connection = ds.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    @Test
    public void connectTest() throws NamingException, SQLException {
        Mockito.when(context.lookup(dbPath)).thenReturn(new MysqlDataSource());
        ds = (MysqlDataSource) context.lookup(dbPath);
        Mockito.verify(context,Mockito.times(1)).lookup(dbPath);

        ds.setDatabaseName("iqubedbtest");
        ds.setUser("test");
        ds.setPassword("test");
        ds.setURL("jdbc:mysql://localhost:3306/iqubedbtest");

        Assert.assertEquals("test",ds.getUser());
        Assert.assertEquals("jdbc:mysql://localhost:3306/iqubedbtest",ds.getURL());
        Assert.assertEquals("iqubedbtest",ds.getDatabaseName());

        connection = ds.getConnection();
        connection.setAutoCommit(false);

        Assert.assertNotNull(connection);
        Assert.assertFalse(connection.getAutoCommit());

    }

    @AfterClass
    public void destroy(){
        ds = null;
    }

    @After
    public void close() throws SQLException {
            connection.close();
    }


}
