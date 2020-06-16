package org.bonn.se.process.control.JDBC.Repositories;


import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.JDBC.DataBaseConnection;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.services.util.ConnectionTypes;
import org.bonn.se.services.util.TableNames;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class UserRepository {








    public final static String table = TableNames.DBS_USER_Table;//"portalusers";
    private static List<User> registeredUsers;


    public static void instantiateRepository() {
        if(registeredUsers == null) {
            registeredUsers = new ArrayList<User>();
        }
    }


    public static User getLoginUser(String user) throws DataBaseException {
        User loginUser = null;


            Connection newConnection = DataBaseConnection.getSqlConnection();
            Statement sqlStatement = DataBaseConnection.getStatement();
            ResultSet resultSet = null;

            String sqlCommand = "SELECT * FROM " + table + " WHERE username = BINARY '" + user + "'";

            if(DataBaseConnection.getConnectionType().equals(ConnectionTypes.CONNECTION_TYPE_PostgreSQL)) sqlCommand = "SELECT * FROM " + table + " WHERE username = '" + user + "'";

            try {
                resultSet = sqlStatement.executeQuery(sqlCommand);


                if(resultSet.next()) {
                    //int id = resultSet.getInt("id");
                    String prename = resultSet.getString("prename");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    loginUser = User.getreducedUser(prename,username,password);
                    loginUser.setEmail(email);

                }


            } catch (SQLException e) {
                new DataBaseException("Fehler beim Suchen eines Users");
                e.printStackTrace();
            }

            finally{
                DataBaseConnection.closeConnection();
            }



        return loginUser;
    }



    public static void registerUser(int id, String name, String prename, String email,  String username, String password) throws DataBaseException {

        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();

        String sqlCommand = "INSERT INTO " + table + "  VALUES ('" + id + "','" + name + "','" + prename + "','" + username  + "','" + email +"','" + password + "');";

        try {
            sqlStatement.executeUpdate(sqlCommand);
        } catch (SQLException e) {

            e.printStackTrace();
            throw new DataBaseException("User konnte nicht eingefügt werden. ");

        } finally {

            DataBaseConnection.closeConnection();
        }

    }


    public static int CountObjectsinDataBaseTable() throws DataBaseException {

        int result = 0;

        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;

        String sqlCommand = "SELECT COUNT(*) AS 'ANZAHL' FROM "  + table;

        if(DataBaseConnection.getConnectionType().equals(ConnectionTypes.CONNECTION_TYPE_PostgreSQL)) sqlCommand = "SELECT COUNT(*) AS ANZAHL FROM "  + table;


        try {

            resultSet = sqlStatement.executeQuery(sqlCommand);
            resultSet.next();

            result = resultSet.getInt("ANZAHL");

        } catch (SQLException e) {

        System.out.println("Tabelle ist Leer!");

        }

        finally {

            DataBaseConnection.closeConnection();
            return result;

        }


    }

    public static int getNextId() throws DataBaseException {

        int result = 0;

        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;

        String sqlCommand = "SELECT MAX(id) AS 'ANZAHL' FROM "  + table;

        if(DataBaseConnection.getConnectionType().equals(ConnectionTypes.CONNECTION_TYPE_PostgreSQL)) sqlCommand = "SELECT MAX(id) AS ANZAHL FROM "  + table;


        try {

            resultSet = sqlStatement.executeQuery(sqlCommand);
            resultSet.next();

            result = resultSet.getInt("ANZAHL");

        } catch (SQLException e) {

        }

        finally {

            DataBaseConnection.closeConnection();
            return result;

        }


    }




    public static void update() throws DataBaseException {


        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;



        String sqlCommand = "SELECT * FROM " + table;

        try {

            instantiateRepository();
            registeredUsers.clear();
            resultSet = sqlStatement.executeQuery(sqlCommand);


            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String prename = resultSet.getString("prename");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User input = new User(id, name, prename, username, password);
                input.setEmail(email);
                registeredUsers.add(input);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }


        finally {

            DataBaseConnection.closeConnection();

        }



    }


    public static List<User> getAllRegisteredUsers() throws DataBaseException {

        update();
        return registeredUsers;
    }



    public static boolean userIsAlreadyDefined(String user) throws DataBaseException {
        User loginUser = null;


        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;
        boolean userIsAlreadyDefined = false;

        String sqlCommand = "SELECT * FROM " + table + " WHERE username = BINARY '" + user + "'";

        if(DataBaseConnection.getConnectionType().equals(ConnectionTypes.CONNECTION_TYPE_PostgreSQL)) sqlCommand = "SELECT * FROM " + table + " WHERE username = '" + user + "'";

        try {
            resultSet = sqlStatement.executeQuery(sqlCommand);

            userIsAlreadyDefined = resultSet.next();

        } catch (SQLException e) {
            new DataBaseException("Fehler beim Suchen eines Users");
            e.printStackTrace();
        }

        finally{
            DataBaseConnection.closeConnection();
        }

        return userIsAlreadyDefined;
    }



    public static boolean emailIsAlreadyUsed(String email) throws DataBaseException {
        User loginUser = null;


        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;
        boolean emailIsAlreadyUsed = false;

        String sqlCommand = "SELECT * FROM " + table + " WHERE email =  '" + email + "'";

        if (DataBaseConnection.getConnectionType().equals(ConnectionTypes.CONNECTION_TYPE_PostgreSQL))
            sqlCommand = "SELECT * FROM " + table + " WHERE email = '" + email + "'";

        try {
            resultSet = sqlStatement.executeQuery(sqlCommand);

            emailIsAlreadyUsed = resultSet.next();

        } catch (SQLException e) {
            new DataBaseException("Fehler beim Suchen eines Users");
            e.printStackTrace();
        } finally {
            DataBaseConnection.closeConnection();
        }

        return emailIsAlreadyUsed;

    }






}
