package org.bonn.se.meinhotelapp.gui.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.services.util.TableNames;
import org.bonn.se.services.util.Views;

public class MinimizedTopBar extends HorizontalLayout {

    public MinimizedTopBar(String location){

        this.setStyleName("TopPanel");

        this.setWidth("100%");
        this.setHeight("55px");

        Label headLabel = new Label("MeinHotel - <i>das Reservierungssystem</i>//" + location, ContentMode.HTML);
        Button backbutton = new Button(VaadinIcons.ARROW_BACKWARD);
        backbutton.setStyleName("backbutton");

        backbutton.addClickListener(clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(Views.LANDINGPAGE);
        });


        headLabel.setStyleName("headLabel");
        headLabel.setSizeUndefined();


        this.addComponent(headLabel);
        this.addComponent(backbutton);
        this.setComponentAlignment(headLabel, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(backbutton, Alignment.MIDDLE_RIGHT);




    }

}
