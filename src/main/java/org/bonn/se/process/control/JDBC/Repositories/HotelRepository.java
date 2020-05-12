package org.bonn.se.process.control.JDBC.Repositories;

import org.bonn.se.model.objects.entities.Hotel;
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

public class HotelRepository {

    public final static String table = TableNames.DBS_HOTEL_Table;
    private static List<Hotel> assignedHotels;



    public static void instantiateRepository() {
        if(assignedHotels == null) {
            assignedHotels = new ArrayList<Hotel>();
        }
    }


    public static  List<Hotel> getHotels(String ort) throws DataBaseException {
        List<Hotel> matchingList = new ArrayList<Hotel>();


        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;

        String sqlCommand = "SELECT * FROM " + table + " WHERE ort LIKE '%" + ort + "%'";

        if(DataBaseConnection.getConnectionType().equals(ConnectionTypes.CONNECTION_TYPE_PostgreSQL)) sqlCommand = "SELECT * FROM " + table + " WHERE LOWER(ort) LIKE LOWER('%" + ort + "%')";

        try {
            resultSet = sqlStatement.executeQuery(sqlCommand);


            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String location = resultSet.getString("ort");
                String description = resultSet.getString("description");
                matchingList.add(new Hotel(id, name, location, description));

            }


        } catch (SQLException e) {
            new DataBaseException("Fehler beim Suchen eines Users");
            e.printStackTrace();
        }

        finally{
            DataBaseConnection.closeConnection();
        }

        return matchingList;
    }


    public static  Hotel getHotel(int id) throws DataBaseException {
        Hotel result = null;


        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;

        String sqlCommand = "SELECT * FROM " + table + " WHERE id = '" + id + "'";

        if(DataBaseConnection.getConnectionType().equals(ConnectionTypes.CONNECTION_TYPE_PostgreSQL)) sqlCommand = "SELECT * FROM " + table + " WHERE id = '" + id + "'";

        try {
            resultSet = sqlStatement.executeQuery(sqlCommand);


            while(resultSet.next()) {
                int ident = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String location = resultSet.getString("ort");
                String description = resultSet.getString("description");

                result = new Hotel(ident, name, location, description);
            }



        } catch (SQLException e) {
            System.out.println(sqlCommand);
            new DataBaseException("Fehler beim Suchen eines Users");
            e.printStackTrace();
        }

        finally{
            DataBaseConnection.closeConnection();
            return result;
        }
    }



    public static void registerHotel(int id, String name, String ort,  String description) throws DataBaseException {

        Connection newConnection = DataBaseConnection.getSqlConnection();
        Statement sqlStatement = DataBaseConnection.getStatement();

        String sqlCommand = "INSERT INTO " + table + "  VALUES ('" + id + "','" + name + "','" + ort + "','" + description + "');";

        try {
            sqlStatement.executeUpdate(sqlCommand);
        } catch (SQLException e) {

            e.printStackTrace();
            throw new DataBaseException("Hotel konnte nicht eingefügt werden. ");

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
            assignedHotels.clear();
            resultSet = sqlStatement.executeQuery(sqlCommand);


            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String location = resultSet.getString("ort");
                String description = resultSet.getString("description");
                Hotel input = new Hotel(id, name, location, description);
                assignedHotels.add(input);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }


        finally {
            DataBaseConnection.closeConnection();

        }

    }


    public static List<Hotel> getAllRegisteredHotels() throws DataBaseException {
        if(assignedHotels == null) {
            throw new DataBaseException("Keine Entities in der Datenbank vorhanden");
        }
        return assignedHotels;
    }




}
