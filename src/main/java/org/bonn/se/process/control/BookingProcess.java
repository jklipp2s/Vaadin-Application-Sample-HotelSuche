package org.bonn.se.process.control;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.bonn.se.meinhotelapp.gui.ui.MyUI;
import org.bonn.se.meinhotelapp.gui.windows.ConfirmationWindow;
import org.bonn.se.model.factories.BookingFactory;
import org.bonn.se.model.objects.dto.BookingDetail;
import org.bonn.se.model.objects.dto.BookingRequest;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.model.objects.entities.Booking;
import org.bonn.se.process.control.JDBC.Repositories.BookingRepository;
import org.bonn.se.process.control.exceptions.DataBaseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingProcess {

    public static BookingProcess process = null;


    public BookingProcess() {
    }

    public static BookingProcess getInstance() {
        if(process == null) {
            process = new BookingProcess();
        }
        return process;
    }


    public void createBooking(BookingRequest request, Window window) {
        //User user = (User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);
        User user = ((MyUI) UI.getCurrent()).getUser();

        Booking booking = BookingFactory.createBooking(request, user);

        boolean result = false;
        try {
        result =  BookingRepository.addBooking(booking);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        //NAvigation auf Basis des Buchungsausgangs
        if(result == true){
            window.close();
            UI.getCurrent().addWindow(new ConfirmationWindow("Buchung erfolgreich! ID: " + booking.getId() ));
        } else {
            UI.getCurrent().addWindow(new ConfirmationWindow("Buchung fehlgeschlagen!" ));
        }


    }

    public void deleteBookingByID(int id) {
        BookingRepository.deleteBookingBy(id);
        UI.getCurrent().addWindow(new ConfirmationWindow("Die Reise wurde storniert!"));

    }


    public List<BookingDetail> getAllBookingsForUser(User user) {
        List<BookingDetail> bookings = new ArrayList<>();

        try {
            bookings = BookingRepository.getAllBookingsForUser(user);
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        finally {
            return bookings;
        }
    }
}
