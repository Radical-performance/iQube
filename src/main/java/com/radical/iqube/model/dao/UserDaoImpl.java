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

    private final String CREATE = "INSERT INTO userstest(login,password,email,nickname) VALUES(?,?,?,?)";
    private final String DELETE = "DELETE FROM users WHERE login = (?)";
    private final String UPDATE= "UPDATE users SET value=(?) WHERE login = (?)";
    private  String GET = "SELECT * FROM userstest WHERE value = (?)";


    @Override
    public boolean create(UserEntity user) {
        boolean created = false;
        Savepoint savepoint;
        Connection con = null;

        try {
            con = getConnection();PreparedStatement st = con.prepareStatement(CREATE);
            savepoint =con.setSavepoint();
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.setString(4, user.getEmail());
            if(st.executeUpdate() != 0){con.commit(); created = true;}
            else{rollback(st.getConnection(), savepoint);}
            connector.close(con);
        } catch (SQLException e) {log.warn("Exception sql: "+e.getMessage());
        } finally {
            if(con != null){connector.close(con);}
        }
        return created;
    }


    @Override
    public boolean update(String login,String updateValueName,String value) {
        boolean updated = false;
        Savepoint savepoint;
        Connection con = null;
        PreparedStatement statement = null;
        try{
            con = connector.connect();
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
        } finally { if(con != null) {connector.close(con);}}
        return updated;
    }

    @Override
    public boolean remove(UserEntity user) {
        Savepoint savepoint;
        Connection con = null;
        int removed = 0;
        try{
            con = connector.connect();
            PreparedStatement st = con.prepareStatement(DELETE);
            savepoint = con.setSavepoint();
            st.setString(1,user.getLogin());
            if(st.executeUpdate()==0){con.rollback(savepoint);}
            else{con.commit(); removed = 1;}
        } catch (SQLException e) {log.warn(e.getCause());//<=======LOGGER
        } finally { if(con != null){ connector.close(con);}}
        return removed != 0;
    }

    @Override
    public UserEntity get(String valueName,String value) {
        Connection con = null;
        UserEntity user = null;
        ResultSet rs;
        try {
            con = connector.connect();

            switch (valueName)
            {
                case "login": GET = GET.replace("value",valueName); break;
                case "email": GET.replace("val","email");break;
                case "nickName": GET.replace("val","nickname");break;
            }
            System.out.println(con);

            PreparedStatement st = con.prepareStatement(GET);
            st.setString(1,value);
            System.out.println(st);

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
        } catch (SQLException e) {e.printStackTrace();//<=======LOGGER
        } finally { if(con != null){connector.close(con);}
        }
        return user;
    }
    public Connection getConnection() {
        return connector.connect();
    }
    public void rollback(Connection connection, Savepoint savepoint){}

}
