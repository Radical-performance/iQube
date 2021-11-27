package com.radical.iqube.model.dao;

import com.radical.iqube.model.connector.ConnectorTest;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.sql.*;

public class UserDaoImplTest {
    private static final Logger log = Logger.getLogger(UserDaoImplTest.class);
    private ConnectorTest connector;
    private Savepoint savepoint;
    private  Connection connection;

    public UserDaoImplTest() {connector = new ConnectorTest();}


    @BeforeClass
    @Test
    public void tearUp() {insertUser();}

    @AfterClass
    public void tearDown() {connector = null;}

    @After
    public void afterEach(){
        savepoint = null;
    }

    @Test
    void initTest() {Assert.assertNotNull(connector);}

    @Test(dependsOnMethods = {"remove"})
    public void create() {
        boolean created = false;
        int updValue;
        connection = getConnect();
        try {
            Assert.assertNotNull(connection);
            savepoint = connection.setSavepoint();
            Assert.assertNotNull(savepoint);

            PreparedStatement st = connection.prepareStatement("INSERT INTO userstest VALUES(?,?,?,?)");
            Assert.assertNotNull(st);

            for (int x = 1; x < 5; x++) {
                st.setString(x, "test");
            }
            updValue = st.executeUpdate();

            Assert.assertNotEquals(0, updValue);

            if (updValue != 0) {created = true;}
            Assert.assertTrue(created);

            st.close();
            connection.rollback(savepoint);
            connection.close();
            Assert.assertTrue(connection.isClosed());
            connection = null;
            Assert.assertNull(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"tearUp"})
    public void get() throws SQLException {
        connection = getConnect();
        Assert.assertFalse(connection.isClosed());
        Assert.assertNotNull(connection);
        PreparedStatement st = connection.prepareStatement("SELECT * FROM userstest WHEre login = (?)");
        st.setString(1,"testValue");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {Assert.assertEquals(rs.getString(1), "testValue");}
        st.close();
        connection.close();
        Assert.assertTrue(connection.isClosed());
        connection = null;
        Assert.assertNull(connection);
    }

    @Test(dependsOnMethods = {"get"})
    public void update() {
        String query;
        PreparedStatement st = null;
        int updated;
        for(int i = 0; i<4; i++){
            try {
                connection = getConnect();
                Savepoint savepoint = connection.setSavepoint();
                if(i == 0){
                    query = "UPDATE userstest SET login=(?) where login = (?)";
                    st = connection.prepareStatement(query);
                    Assert.assertNotNull(st);
                    st.setString(1,"updatedLogin");
                    st.setString(2,"testValue");
                }else if(i == 1){
                    query = "UPDATE userstest SET email=(?) where email = (?)";
                    st = connection.prepareStatement(query);
                    Assert.assertNotNull(st);
                    st.setString(1,"updatedEmail");
                    st.setString(2,"testValue");
                }else if(i == 2){
                    query = "UPDATE userstest SET nickname=(?) where nickname = (?)";
                    st = connection.prepareStatement(query);
                    Assert.assertNotNull(st);
                    st.setString(1,"updatedNickname");
                    st.setString(2,"testValue");
                }else {
                    query = "UPDATE userstest SET password=(?) WHERE login = (?)";
                    st = connection.prepareStatement(query);
                    Assert.assertNotNull(st);
                    st.setString(1, "updatedPassword");
                    st.setString(2,"updatedLogin");
                }
                updated = st.executeUpdate();
                if(updated == 1){
                    st.close();
                    connection.commit();
                }else{
                    st.close();
                    connection.rollback(savepoint);
                }
                Assert.assertEquals(updated,1);
                connection.close();
                connection =null;
            }catch (SQLException e){e.printStackTrace();}
            }

    }


    @Test(dependsOnMethods = {"update"})
    public void remove() throws SQLException {
        int removed;
        connection = getConnect();
        Assert.assertNotNull(connection);
        Assert.assertFalse(connection.isClosed());

        savepoint = connection.setSavepoint();
        Assert.assertNotNull(savepoint);

        PreparedStatement st = connection.prepareStatement("DELETE FROM userstest WHERE login = ?");
        st.setString(1, "updatedLogin");
        removed = st.executeUpdate();
        Assert.assertEquals(removed, 1);
        st.close();
        connection.commit();
        connection.close();
        Assert.assertTrue(connection.isClosed());
        connection = null;
        Assert.assertNull(connection);
    }


    @Test
    public void getConnectionTest() throws SQLException {
        getConnect();
        Assert.assertNotNull(connection);
        connection.close();
        connection = null;
    }

    public Connection getConnect() {
        try {connection = connector.connect();} catch (SQLException e) {e.printStackTrace();}
        return connection;
    }

    public void insertUser() {

        connection = getConnect();
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO userstest VALUES(?,?,?,?)");
            for (int x = 1; x < 5; x++) {st.setString(x, "testValue");}
            st.executeUpdate();
            connection.commit();
            st.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
