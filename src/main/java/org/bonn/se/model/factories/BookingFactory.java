package org.bonn.se.model.factories;

import org.bonn.se.model.objects.dto.BookingDetail;
import org.bonn.se.model.objects.dto.BookingRequest;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.model.objects.entities.Booking;

import java.sql.*;
import java.time.LocalDate;

public class BookingFactory {


    public static Booking createBooking(BookingRequest request, User user) {
        Booking book = new Booking();

        book.setAbreise(request.getAbreise());
        book.setAnreise(request.getAnreise());
        book.setHotel(request.getHotel());
        book.setIban(request.getIban());
        book.setNumber(request.getPersonNumber());


        book.setUser(user);

        book.setDatumBuchung(LocalDate.now());


        return book;
    }



    public static BookingDetail createBookingDetail(int id, String customer, Date anreise, Date abreise, String hotel,
                                                    int personNumber, Date datumBuchung) {

        BookingDetail book = new BookingDetail();

        book.setId(id);
        book.setCustomername(customer);
        book.setAbreise(abreise.toLocalDate());
        book.setAnreise(abreise.toLocalDate());
        book.setHotel(hotel);
        book.setNumber(personNumber);

        book.setDatumBuchung(datumBuchung.toLocalDate());


        return book;
    }




}
