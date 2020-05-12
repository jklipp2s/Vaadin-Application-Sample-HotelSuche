package org.bonn.se.process.control.JDBC;

import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.services.util.ConnectionTypes;
import org.bonn.se.services.util.Passwords;

import java.sql.*;
import java.util.Properties;


public abstract class DataBaseConnection {



    private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String DATABASE_URL = "jdbc:mysql://localhost/udemy_course?serverTimezone=UTC";
    private static String USER = "julian";
    private static String PASSWORD = Passwords.SQL_PASSWORD;


    private static String connectionType;
    private static Connection connection = null;




    public static void setUPConnection(String connType) throws DataBaseException {
        connectionType = connType;
    }

    public static String getConnectionType() {
        return connectionType;
    }




    private static void initConnection() throws DataBaseException{

    String connectionType = getConnectionType();

        try {

        if (connectionType.equals(ConnectionTypes.CONNECTION_TYPE_MySQL))  Class.forName(JDBC_DRIVER);
        if (connectionType.equals(ConnectionTypes.CONNECTION_TYPE_PostgreSQL)) DriverManager.registerDriver(new org.postgresql.Driver());


        } catch (ClassNotFoundException | SQLException e) {

                e.printStackTrace();
                throw new DataBaseException("TreiberFehler");
        }


    }



    public static Connection getSqlConnection() throws DataBaseException {

        initConnection();
        boolean isClosed = false;

        if(connection == null) {
            openConnection();
        }


        try {
             isClosed = connection.isClosed();
        } catch (SQLException e) {
            throw new DataBaseException("Fehler: Verbindung wurde vorzeitig geschlossen!");
        }
        
        if(isClosed) {
        openConnection();
        }
        


        return connection;
    }


    public static void openConnection() throws DataBaseException {

        // Werte für PostgreSQL!
        String url = "jdbc:postgresql://dumbo.inf.h-brs.de/";
        Properties props = new Properties();
        props.setProperty("user","mmuel72s");
        props.setProperty("password","mmuel72s");


        String connectionType = getConnectionType();

        try {

            if(connectionType.equals(ConnectionTypes.CONNECTION_TYPE_MySQL)) connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            if(connectionType.equals(ConnectionTypes.CONNECTION_TYPE_PostgreSQL)) {
                connection = DriverManager.getConnection(url, props);

            }

        } catch (SQLException throwables) {
            throw new DataBaseException("Fehler beim öffnen einer Verbindung" + "\nFehler beim Zugriff auf die DB! Sichere Verbindung vorhanden!?");
        }


    }


    public static void closeConnection() throws DataBaseException{
        try {
            connection.close();
        } catch (SQLException throwables) {
            throw new DataBaseException("Fehler Beim Schliessen einer Verbindung");
        }
    }


    public static Statement getStatement() throws DataBaseException {

        Statement newStatement = null;

        try {
             newStatement = connection.createStatement();
        } catch (SQLException throwables) {
            throw new DataBaseException("Fehler Beim Erstellen eines Statements");
        }


        return newStatement;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

}