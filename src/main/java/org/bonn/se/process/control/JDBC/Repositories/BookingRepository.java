package org.bonn.se.process.control.JDBC.Repositories;

import org.bonn.se.model.factories.BookingFactory;
import org.bonn.se.model.objects.dto.BookingDetail;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.model.objects.entities.Booking;
import org.bonn.se.process.control.JDBC.DataBaseConnection;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.services.util.TableNames;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public abstract class BookingRepository {



    public static void deleteBookingBy(int id)  {
        try {
        DataBaseConnection.getSqlConnection();
        Statement statement = DataBaseConnection.getStatement();

        String sql = "DELETE FROM " + TableNames.DBS_BOOKINGS_Table + " WHERE id = " + id +";";

            statement.execute(sql);
        } catch(SQLException| DataBaseException e) {
            e.printStackTrace();
        }
    }

    public static boolean addBooking(Booking booking) throws SQLException, DataBaseException {
        DataBaseConnection.getSqlConnection();
        String sql = "INSERT INTO " + TableNames.DBS_BOOKINGS_Table + " VALUES(default,?,?,?,?,?,?,?);";
        PreparedStatement statement = DataBaseConnection.getPreparedStatement(sql);

        java.sql.Date.valueOf(booking.getAnreise());


        statement.setDate(1, Date.valueOf(booking.getAnreise()));
        statement.setDate(2, Date.valueOf(booking.getAbreise()));
        statement.setString(3,booking.getIban());
        statement.setInt(4,booking.getNumber());
        statement.setString(5, booking.getUser().getUsername());
        statement.setDate(6, Date.valueOf(booking.getAnreise()));
        statement.setInt(7, booking.getHotel().getId());

        statement.executeUpdate();


        setBuchungsID(booking);

        return true;

    }

    private static void setBuchungsID(Booking booking) throws DataBaseException {
    DataBaseConnection.getSqlConnection();
    Statement statement = DataBaseConnection.getStatement();
    ResultSet resultSet = null;

    String sqlBefehl = "SELECT MAX(" + TableNames.DBS_BOOKINGS_Table +".id) AS NR" +
            " FROM " + TableNames.DBS_BOOKINGS_Table + ";";


        try {
            resultSet = statement.executeQuery(sqlBefehl);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int currentValue = 0;

        try {
            resultSet.next();
            currentValue = resultSet.getInt("nr");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        booking.setId(currentValue);


    }


    public static List<BookingDetail> getAllBookingsForUser(User user) throws DataBaseException {
        DataBaseConnection.getSqlConnection();
        Statement statement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;
        List<BookingDetail> bookings = new ArrayList<>();

        String sql = "SELECT * FROM " + TableNames.Views.DBS_BOOKING_VIEW +
                     " WHERE userid LIKE '" + user.getUsername() + "';";

        try {
            resultSet = statement.executeQuery(sql);


            while(resultSet.next()) {

            int id = resultSet.getInt("id");
                String customer = resultSet.getString("name");
                Date anreise = resultSet.getDate("anreise");
            Date abreise = resultSet.getDate("abreise");
            int anzahl = resultSet.getInt("anzahlpersonen");
            Date datumBuchung = resultSet.getDate("datumbuchung");
            String hotel = resultSet.getString("hotel");





                bookings.add(BookingFactory.createBookingDetail(id, customer, anreise, abreise, hotel, anzahl, datumBuchung));



            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(sql);
        }


        return bookings;
    }






    public static List<BookingDetail> getAllBookings() throws DataBaseException {
        DataBaseConnection.getSqlConnection();
        Statement statement = DataBaseConnection.getStatement();
        ResultSet resultSet = null;
        List<BookingDetail> bookings = new ArrayList<>();

        String sql = "SELECT * FROM " + TableNames.Views.DBS_BOOKING_VIEW + ";";

        try {
            resultSet = statement.executeQuery(sql);


            while(resultSet.next()) {

                int id = resultSet.getInt("id");
                String customer = resultSet.getString("name");
                Date anreise = resultSet.getDate("anreise");
                Date abreise = resultSet.getDate("abreise");
                int anzahl = resultSet.getInt("anzahlpersonen");
                Date datumBuchung = resultSet.getDate("datumbuchung");
                String hotel = resultSet.getString("hotel");


                bookings.add(BookingFactory.createBookingDetail(id, customer, anreise, abreise, hotel, anzahl, datumBuchung));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(sql);
        }


        return bookings;
    }





    
    
}
