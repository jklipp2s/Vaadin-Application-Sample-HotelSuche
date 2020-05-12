package org.bonn.se.meinhotelapp.gui.windows;

import com.vaadin.ui.*;
import org.bonn.se.model.objects.dto.BookingRequest;
import org.bonn.se.model.objects.entities.Hotel;
import org.bonn.se.process.control.BookingProcess;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingWindow extends Window {
    public BookingWindow(final Hotel hotel) {

        super("Buchung");
        center();
        setResizable(false);

        VerticalLayout content = new VerticalLayout();
        content.addComponent(new Label("Buchung für Hotel: " + hotel.getName()));
        content.setMargin(true);
        setContent(content);

        final DateField dateAnreise = new DateField();
        content.addComponent(dateAnreise);
        dateAnreise.setCaption("Anreise");
        dateAnreise.setDateFormat("yyyy-MM-dd");
        dateAnreise.setValue(LocalDate.now());




        final DateField dateAbreise = new DateField();
        content.addComponent(dateAbreise);
        dateAbreise.setCaption("Abreise");
        dateAbreise.setDateFormat("yyyy-MM-dd");
        dateAbreise.setValue(LocalDate.now());





        final ComboBox personNumber = new ComboBox();
        personNumber.setCaption("Anzahl Personen:");
        content.addComponent(personNumber);
        List<Integer> selections = new ArrayList<>();
        selections.add(1);
        selections.add(2);
        selections.add(3);
        personNumber.setItems(selections);



        final TextField ibanFeld = new TextField();
        ibanFeld.setCaption("IBAN:");
        content.addComponent(ibanFeld);


        setClosable(true);

        Button buchungsButton = new Button("Buche");
        buchungsButton.addClickListener(clickEvent -> {

            if(ibanFeld.getValue().isEmpty() || personNumber.isEmpty()) {
                Notification.show(null, "Bitte geben Sie einen Wert ein!", Notification.Type.ERROR_MESSAGE);
                return;
            }



            BookingRequest bookingRequest = new BookingRequest();
            bookingRequest.setAbreise(dateAbreise.getValue());
            bookingRequest.setAnreise(dateAnreise.getValue());
            bookingRequest.setIban(ibanFeld.getValue());
            bookingRequest.setNumber((Integer) personNumber.getValue());
            bookingRequest.setHotel(hotel);





            BookingProcess.getInstance().createBooking(bookingRequest, BookingWindow.this);


        });

        content.addComponent(buchungsButton);
        content.setComponentAlignment(buchungsButton, Alignment.MIDDLE_CENTER);




    }
}
