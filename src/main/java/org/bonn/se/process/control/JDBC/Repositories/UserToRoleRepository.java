package org.bonn.se.process.control.JDBC.Repositories;

import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.JDBC.DataBaseConnection;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.services.util.TableNames;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserToRoleRepository {
    private static String table = TableNames.DBS_USER_TO_ROLE_Table;

    public static void insertIntoTable_User_To_Role(String username, String role) throws DataBaseException {

        Connection connection = DataBaseConnection.getSqlConnection();
        Statement state = DataBaseConnection.getStatement();

        String sqlBefehl = "INSERT INTO " + TableNames.DBS_USER_TO_ROLE_Table + " VALUES ('" + username + "', '"+ role  +"');\n";
        try {
            state.executeUpdate(sqlBefehl);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Fehler Im SQL Befehl");
        } finally {
            DataBaseConnection.closeConnection();
        }

    }



    public static boolean UserhasRole(String user, String role) throws DataBaseException {

        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;
        String output = null;
        boolean result = false;

        String sqlBefehl = "SELECT * FROM " + table + " WHERE role = '" + role +"'\n" +
                "AND username = '" + user + "';" ;
        try {
            resultSet = sqlStatement.executeQuery(sqlBefehl);
            if (resultSet.next()) {
                output = resultSet.getString("username");
                if(!output.isEmpty()){
                    result = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Fehler Im SQL Befehl");
        } finally {

            DataBaseConnection.closeConnection();
            return result;
        }

    }


    public static List<String> getRoles(User user) throws DataBaseException {


        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;
        List<String> result = new ArrayList<>();

        String sqlBefehl = "SELECT * FROM " + table + " WHERE username = '" + user.getUsername() +"';";
        try {
            resultSet = sqlStatement.executeQuery(sqlBefehl);
            while(resultSet.next()) {
                 result.add(resultSet.getString("role"));
                }
            } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataBaseException("Fehler Im SQL Befehl");

        }

        finally {

            DataBaseConnection.closeConnection();
            return result;
        }




    }
}
