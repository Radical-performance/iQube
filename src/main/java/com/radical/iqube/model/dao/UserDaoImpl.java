package com.radical.iqube.model.dao;
import com.radical.iqube.model.connector.Connector;
import com.radical.iqube.model.entity.UserEntity;
import com.radical.iqube.model.entity.UserEntityBuilderImpl;
import org.apache.log4j.Logger;
import java.sql.*;

public class UserDaoImpl implements UserDao {
    public final Logger log = Logger.getLogger(UserDaoImpl.class);
    private final Connector connector;
    public UserDaoImpl() {
        this.connector = new Connector();
    }

    private final String CREATE = "INSERT INTO users(login,password,email,nickname) VALUES(?,?,?,?)";
    private final String DELETE = "DELETE FROM users WHERE login = (?)";
    private final String UPDATE= "UPDATE users SET value=(?) WHERE login = (?)";
    private final String GET = "SELECT * FROM users WHERE val = (?)";


    @Override
    public boolean create(UserEntity user) {
        boolean created = false;
        Savepoint savepoint;
        Connection con;

        try { con = getConnection();PreparedStatement st = con.prepareStatement(CREATE);
            savepoint =con.setSavepoint();
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.setString(4, user.getEmail());
            if(st.executeUpdate() != 0){con.commit(); created = true;}
            else{rollback(st.getConnection(), savepoint);}
        } catch (SQLException e) {log.warn("Exception sql: "+e.getMessage());}
        return created;
    }


    @Override
    public boolean update(String login,String updateValueName,String value) {
        boolean updated = false;
        Savepoint savepoint;
        PreparedStatement statement = null;
        try(Connection con = connector.connect()){
            savepoint = con.setSavepoint();
            switch (updateValueName){
                case "login":
                    if(get(updateValueName,value)==null)
                    {statement = con.prepareStatement(UPDATE.replace("value","login"));}break;
                case "email":
                    if(get(updateValueName,value)==null)
                    {statement = con.prepareStatement(UPDATE.replace("value","email"));}break;
                case "nickname":
                    if(get(updateValueName,value)==null)
                    {statement = con.prepareStatement(UPDATE.replace("value","nickname"));}break;
                case "password":
                   statement = con.prepareStatement(UPDATE.replace("value","password"));break;
            }
            if(statement!=null){
                statement.setString(1,value);
                statement.setString(2,login);
                if(statement.executeUpdate()==1){
                    updated = true;
                    con.commit();
                    statement.close();
                }else{connector.rollback(statement.getConnection(),savepoint);}
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
            e.printStackTrace();
        }
        return updated;
    }

    @Override
    public boolean remove(UserEntity user) {
        Savepoint savepoint;
        int removed = 0;
        try(Connection con = getConnection();PreparedStatement st = con.prepareStatement(DELETE)){
            savepoint = con.setSavepoint();
            st.setString(1,user.getLogin());
            if(st.executeUpdate()==0){con.rollback(savepoint);}
            else{con.commit(); removed = 1;}
        } catch (SQLException e) {log.warn(e.getCause());} //<=======LOGGER
        return removed != 0;
    }

    @Override
    public UserEntity get(String valueName,String value) {
        UserEntity user = null;
        ResultSet rs;
        PreparedStatement st = null;
        try(Connection con = getConnection()) {
            switch (valueName)
            {
                case "login": st = con.prepareStatement(GET.replace("val","login"));break;
                case "email": st = con.prepareStatement(GET.replace("val","email"));break;
                case "nickName": st = con.prepareStatement(GET.replace("val","nickName"));break;
            }
            if(st!=null)
            {
                st.setString(1, value);
                rs = st.executeQuery();
                while (rs.next())
                {
                    user = new UserEntityBuilderImpl()
                            .setLogin(rs.getString(1))
                            .setPassword(rs.getString(2))
                            .setEmail(rs.getString(3))
                            .setNickName(rs.getString(4))
                            .build();
                }
                st.close();
                rs.close();
            }
        } catch (SQLException e) {e.printStackTrace();}//<=======LOGGER
        return user;
    }
    public Connection getConnection() {
        return connector.connect();
    }
    public void rollback(Connection connection, Savepoint savepoint){};

}
