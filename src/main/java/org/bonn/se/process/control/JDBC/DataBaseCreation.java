package org.bonn.se.process.control.JDBC;

import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.services.util.ConnectionTypes;
import org.bonn.se.services.util.TableNames;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCreation {


    public static void createTableRole(String tablename) throws DataBaseException {

       Connection connection = DataBaseConnection.getSqlConnection();
        Statement state = DataBaseConnection.getStatement();

        String sqlBefehl = "CREATE TABLE " + tablename + " (\n" +
                "  bezeichnung VARCHAR(35) PRIMARY KEY);\n" +
                "INSERT INTO " + tablename + " VALUES ('user');\n" +
                "INSERT INTO " + tablename + " VALUES ('poweruser');\n" +
                "INSERT INTO " + tablename + " VALUES ('admin')\n;";
        try {
            state.executeUpdate(sqlBefehl);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Fehler Bei der DatenbankErstellung, wenden sich an den Administrator!");
        } finally {
            DataBaseConnection.closeConnection();
        }

    }


    public static void createTableUser_To_Role(String tablename) throws DataBaseException {

        Connection connection = DataBaseConnection.getSqlConnection();
        Statement state = DataBaseConnection.getStatement();

        String sqlBefehl = "CREATE TABLE " + tablename + " (\n" +
                "  username VARCHAR(30) REFERENCES  " +  TableNames.DBS_USER_Table     + " (username),\n" +
                "  role VARCHAR(35) REFERENCES  " + TableNames.DBS_ROLE_Table + " (bezeichnung),\n" +
                "  PRIMARY KEY(username, role)" +
                ");";

        try {
            state.executeUpdate(sqlBefehl);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Fehler Bei der DatenbankErstellung, wenden sich an den Administrator!");
        } finally {
            DataBaseConnection.closeConnection();
        }

    }

    public static void createTableUsers(String tablename) throws DataBaseException {

        Connection connection = DataBaseConnection.getSqlConnection();
        Statement state = DataBaseConnection.getStatement();

        String sqlBefehl = "CREATE TABLE " + tablename + " (\n" +
                "  id NUMERIC(6) NOT NULL,\n" +
                "  name VARCHAR(35) NOT NULL,\n" +
                "  prename VARCHAR (35) NOT NULL,\n" +
                "  username VARCHAR(35),\n" +
                "  password VARCHAR(16) NOT NULL,\n  " +
                "  UNIQUE (id), \n " +
                "  PRIMARY KEY (username) );";
        try {
            state.executeUpdate(sqlBefehl);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Fehler Bei der DatenbankErstellung, wenden sich an den Administrator!");
        } finally {
            DataBaseConnection.closeConnection();
        }

    }


    public static void createTableHotel(String tablename) throws DataBaseException {

        Connection connection = DataBaseConnection.getSqlConnection();
        Statement state = DataBaseConnection.getStatement();

        String sqlBefehl = "CREATE TABLE " + tablename + " (\n" +
                "  id NUMERIC(6) NOT NULL,\n" +
                "  name VARCHAR(35) NOT NULL,\n" +
                "  ort VARCHAR (35) NOT NULL,\n" +
                "  description VARCHAR(200),\n" +
                "  UNIQUE (id, name, ort), \n " +
                "  PRIMARY KEY (id) );";
        try {
            state.executeUpdate(sqlBefehl);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Fehler Bei der DatenbankErstellung, wenden sich an den Administrator!");
        } finally {
            DataBaseConnection.closeConnection();
        }

    }



    public static void createTableBookings(String tablename) throws DataBaseException {

        Connection connection = DataBaseConnection.getSqlConnection();
        Statement state = DataBaseConnection.getStatement();

        String sqlBefehl = "CREATE TABLE " + tablename + " (\n" +
                "id SERIAL,\n" +
                "anreise DATE," +
                "abreise DATE," +
                "iban VARCHAR(30)," +
                "anzahl NUMERIC(1)," +
                "userid VARCHAR(35) REFERENCES  " +  TableNames.DBS_USER_Table     + " (username),\n" +
                "datumBuchung DATE," +
                "hotel NUMERIC(6) REFERENCES  " + TableNames.DBS_HOTEL_Table + " (id),\n" +
                "PRIMARY KEY(id)" +
                ");";
        try {
            state.executeUpdate(sqlBefehl);
        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println(sqlBefehl);
            throw new DataBaseException("Fehler Bei der DatenbankErstellung, wenden sich an den Administrator!");
        } finally {
            DataBaseConnection.closeConnection();
        }

    }

    public static void setUpBookingView() throws DataBaseException {

        Connection connection = DataBaseConnection.getSqlConnection();
        Statement state = DataBaseConnection.getStatement();

        String sqlBefehl = "CREATE VIEW " + TableNames.Views.DBS_BOOKING_VIEW + " AS\n" +
                "SELECT b.id,  u.name || ' ' || u.prename AS NAME, b.anreise, b.abreise, b.anzahl AS ANZAHLPERSONEN, b.datumbuchung, h.name AS hotel, h.ort, u.username AS userid\n " +
                "FROM " + TableNames.DBS_BOOKINGS_Table + " b, " + TableNames.DBS_HOTEL_Table + " h , " + TableNames.DBS_USER_Table + " u\n" +
                "WHERE h.id = b.hotel\n" +
                "AND b.userid = u.username;";
        try {
            state.executeUpdate(sqlBefehl);
        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println(sqlBefehl);
            throw new DataBaseException("Fehler Bei der DatenbankErstellung, wenden sich an den Administrator!");
        } finally {
            DataBaseConnection.closeConnection();
        }

    }




    public static void setUpAllTables(){
        try {
            createTableUsers(TableNames.DBS_USER_Table);
            createTableRole(TableNames.DBS_ROLE_Table);
            createTableUser_To_Role(TableNames.DBS_USER_TO_ROLE_Table);
            createTableHotel(TableNames.DBS_HOTEL_Table);
            createTableBookings(TableNames.DBS_BOOKINGS_Table);

        } catch (DataBaseException e) {
            e.printStackTrace();
        }



    }





}
