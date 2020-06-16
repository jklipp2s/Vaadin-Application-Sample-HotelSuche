package org.bonn.se.meinhotelapp.gui.windows;

import com.vaadin.ui.*;
import org.bonn.se.services.util.Views;

public class RegistrationSuccessWindow extends Window {

    public RegistrationSuccessWindow(String user){
        this.center();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("250px");


        Label success = new Label("Willkommen " + user + "." );

        verticalLayout.addComponent(success);

        Button ok =new Button("ok");

        ok.addClickListener(clickEvent -> {
           this.close();
           UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
        });


        verticalLayout.addComponent(ok);
        verticalLayout.setComponentAlignment(ok, Alignment.MIDDLE_CENTER);
        this.setContent(verticalLayout);




    }


}
