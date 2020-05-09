package org.bonn.se.meinhotelapp.gui.windows;

import com.vaadin.ui.*;

public class ConfirmationWindow extends Window {
    public ConfirmationWindow(String text){
        super("Confirmation");
        center();

        //Some basic content for the window
        VerticalLayout content = new VerticalLayout();
        content.addComponent(new Label(text));
        content.setMargin(true);
        setContent(content);

        Button buchungsButton = new Button("OK");
        buchungsButton.addClickListener(event -> {
            close();
        });
        content.addComponent(buchungsButton);
        content.setComponentAlignment(buchungsButton, Alignment.MIDDLE_CENTER);
    }
}
