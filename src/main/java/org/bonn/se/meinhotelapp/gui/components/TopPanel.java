package org.bonn.se.meinhotelapp.gui.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.meinhotelapp.gui.ui.MyUI;
import org.bonn.se.meinhotelapp.gui.windows.ListBookingWindow;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.LoginControl;
import org.bonn.se.services.util.Roles;

public class TopPanel extends HorizontalLayout {

    public TopPanel() {
        this.setStyleName("TopPanel");

        this.setWidth("100%");
        this.setHeight("55px");

        Label headLabel = new Label("MeinHotel - <i>das Reservierungssystem</i>", ContentMode.HTML);
        headLabel.setStyleName("headLabel");
        headLabel.setSizeUndefined();


        this.addComponent(headLabel);
        this.setComponentAlignment(headLabel, Alignment.MIDDLE_LEFT);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("MenuBar");
        //User user = (User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);
        User user = ((MyUI) UI.getCurrent()).getUser();

        String vorname = null;
        if(user != null) {
            vorname = user.getPrename();
        }
        Label loggedLabel = new Label("Welcome: " + vorname +"!");
        loggedLabel.setSizeUndefined();

        horizontalLayout.addComponent(loggedLabel);
        //horizontalLayout.setComponentAlignment(loggedLabel, Alignment.MIDDLE_CENTER);

        MenuBar bar = new MenuBar();
        MenuBar.MenuItem item1 = bar.addItem("Menu", null);
        bar.setWidthUndefined();

        item1.addItem("Logout", VaadinIcons.SIGN_OUT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                LoginControl.logoutUser();
            }
        });

        if(user.hasRole(Roles.NORMAL_USER)) {
            item1.addItem("Buchungen", VaadinIcons.CALC_BOOK, new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem menuItem) {
                ListBookingWindow window = new ListBookingWindow();
                UI.getCurrent().addWindow(window);
                }
            });
        }






        horizontalLayout.addComponent(bar);
        this.addComponent(horizontalLayout);
        this.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_RIGHT);


    }


}
