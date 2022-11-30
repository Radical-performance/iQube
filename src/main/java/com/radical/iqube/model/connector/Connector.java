package com.radical.iqube.model.connector;

import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class Connector {
    public      Connection con;
    @Resource(name = "jdbc/iqubedbtest")
    private DataSource ds;

    private static final Logger log = Logger.getLogger(Connector.class);
    public Connection connect(){

        try {
//            InitialContext ctx = new InitialContext();
//             ds = (DataSource)ctx.lookup("java:comp/env/jdbc/iqubedbtest");
            con =  ds.getConnection();
            con.setAutoCommit(false);
        } catch (SQLException e) {
            log.warn("Error occurred during connection establishing  " + e);
        }
        return con;
    }


    public void rollback(Connection connection, Savepoint savepoint){
        try {connection.rollback(savepoint);} catch (SQLException e) {e.printStackTrace();}
    }

    public void close(Connection con){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
