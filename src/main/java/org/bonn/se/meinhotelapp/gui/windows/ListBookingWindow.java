package org.bonn.se.meinhotelapp.gui.windows;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;
import org.bonn.se.meinhotelapp.gui.ui.MyUI;
import org.bonn.se.model.objects.dto.BookingDetail;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.BookingProcess;
import org.bonn.se.services.util.Roles;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListBookingWindow extends Window {
    private List<BookingDetail> liste = new ArrayList<>();
    private boolean allUsers;
    private Grid <BookingDetail> data;

    public ListBookingWindow() {
        super("Liste Aller Buchungen");
        center();
        setResizable(false);
        VerticalLayout layout = new VerticalLayout();



        User user = ((MyUI) UI.getCurrent()).getUser();

        liste = BookingProcess.getInstance().getAllBookingsForUser(user);

        if(user.hasRole(Roles.ADMIN)) {
            MenuBar adminmenu = new MenuBar();
            allUsers = false;



            MenuBar.MenuItem item1 =   adminmenu.addItem("options",VaadinIcons.COG,  null);
                    item1.addItem("all Users", VaadinIcons.CHECK, new MenuBar.Command() {
                        @Override
                        public void menuSelected(MenuBar.MenuItem menuItem) {

                            allUsers = allUsers ==true ? false : true;
                            if(allUsers == true){ menuItem.setIcon(VaadinIcons.CLOSE);} else {
                                menuItem.setIcon(VaadinIcons.CHECK);
                            }
                            liste.clear();
                            liste = allUsers == true
                                    ? BookingProcess.getInstance().getAllBookings()
                                    : BookingProcess.getInstance().getAllBookingsForUser(user);
                            data.setItems(liste);
                            data.setHeightByRows(liste.size());


                        }
                    });


            layout.addComponent(adminmenu);
            layout.setComponentAlignment(adminmenu, Alignment.TOP_RIGHT);
        }


        data = new Grid<BookingDetail>();
        data.addColumn(BookingDetail::getId).setCaption("nr");
        if(user.hasRole(Roles.ADMIN)) data.addColumn(BookingDetail::getCustomername).setCaption("Kunde");
        data.addColumn(BookingDetail::getHotel).setCaption("Hotel");
        data.addColumn(BookingDetail::getAnreise).setCaption("Anreise");
        data.addColumn(BookingDetail::getAbreise).setCaption("Abreise");
        data.getDefaultHeaderRow().setStyleName("mytableheader");
        data.setSelectionMode(Grid.SelectionMode.SINGLE);
        data.setItems(liste);
        this.setSizeFull();
        layout.addComponent(data);

        data.setSizeFull();

        if (!liste.isEmpty()) {
            data.setHeightByRows(liste.size());
        } else {
            data.setHeightByRows(1);
        }
        layout.addComponent(data);


        if(user.hasRole(Roles.ADMIN)) {
            Button deleteButton = new Button("Storniere Reise");
            layout.addComponent(deleteButton);
            layout.setComponentAlignment(deleteButton, Alignment.MIDDLE_CENTER);

            deleteButton.addClickListener(clickEvent -> {


                Set output = data.getSelectedItems();

                if (output.isEmpty()) {
                    Notification.show(null, "Selektieren Sie eine Zeile um diese zu Löschen", Notification.Type.HUMANIZED_MESSAGE);
                } else {

                    DeleteWarningWindow warning = new DeleteWarningWindow();

                    UI.getCurrent().addWindow(warning);


                    warning.addCloseListener(closeEvent -> {
                        deleteItem(data, warning);
                    });


                }

            });
        }


        this.setContent(layout);


    }


    public void deleteItem(Grid data, DeleteWarningWindow w) {

        if(!w.hatZustimmung()) return;

        User user = ((MyUI) UI.getCurrent()).getUser();

        Set output =  data.getSelectedItems();

        if(!output.isEmpty()) {

            BookingDetail bookingDetail = (BookingDetail) output.iterator().next();
            BookingProcess.getInstance().deleteBookingByID(bookingDetail.getId());
            liste.clear();
            liste = allUsers == true
                    ? BookingProcess.getInstance().getAllBookings()
                    : BookingProcess.getInstance().getAllBookingsForUser(user);



            data.setItems(liste);

            if(liste.isEmpty()) return;
            data.setHeightByRows(liste.size());
        } else {
            Notification.show(null, "Selektieren Sie eine Zeile um diese zu Löschen", Notification.Type.HUMANIZED_MESSAGE);
        }

    }




}
