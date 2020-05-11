package org.bonn.se.process.control.JDBC.Repositories;

import org.bonn.se.process.control.JDBC.DataBaseConnection;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.services.util.TableNames;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RoleRepository {


    public static void insertIntoTableRole(String role) throws DataBaseException {

        Connection connection = DataBaseConnection.getSqlConnection();
        Statement state = DataBaseConnection.getStatement();

        String sqlBefehl = "INSERT INTO " + TableNames.DBS_ROLE_Table + " VALUES ('" + role + "')\n;";
        try {
            state.executeUpdate(sqlBefehl);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Fehler Im SQL Befehl");
        } finally {
            DataBaseConnection.closeConnection();
        }

    }
}
