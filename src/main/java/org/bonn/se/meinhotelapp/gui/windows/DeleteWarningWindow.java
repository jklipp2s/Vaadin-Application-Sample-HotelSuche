package org.bonn.se.meinhotelapp.gui.windows;


import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.bonn.se.process.control.BookingProcess;

public class DeleteWarningWindow extends Window {
    private boolean zustimmung;


    public DeleteWarningWindow() {
    super("Achtung!!!");

    this.setStyleName("DeleteWarningWindow");

    /*
        this.setHeight("200px");
        this.setWidthUndefined();
        this.setWidth("350px");

     */
        center();
        VerticalLayout verticalLayout = new VerticalLayout();

        Label  label = new Label("Wollen Sie die Buchung wirklich\n stornieren?");


        verticalLayout.addComponent(label);

        Button ok = new Button("Ok", VaadinIcons.CHECK);
        ok.addStyleName("ok");
        Button abbrechen = new Button("abbrechen");
        abbrechen.setStyleName("abbrechen");
        HorizontalLayout horizontalLayout = new HorizontalLayout(ok, abbrechen);
        horizontalLayout.setStyleName("deleteButtons");
        horizontalLayout.setMargin(false);
        horizontalLayout.setSpacing(false);

        verticalLayout.addComponent(horizontalLayout);

        ok.addClickListener(clickEvent -> {


        ok();
        close();

        });


        abbrechen.addClickListener(clickEvent -> {
            abbrechen();
            close();

        });



        this.setContent(verticalLayout);
        verticalLayout.setSizeFull();
    }


    public void abbrechen() { zustimmung =  false;}
    public void ok() { zustimmung = true;}

    public boolean hatZustimmung() {
        return zustimmung;
    }
}
