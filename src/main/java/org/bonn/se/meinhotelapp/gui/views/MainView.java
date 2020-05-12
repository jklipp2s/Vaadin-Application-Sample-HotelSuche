package org.bonn.se.meinhotelapp.gui.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.bonn.se.meinhotelapp.gui.windows.BookingWindow;
import org.bonn.se.meinhotelapp.gui.components.TopPanel;
import org.bonn.se.meinhotelapp.gui.ui.MyUI;
import org.bonn.se.model.objects.entities.Hotel;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.HotelSearch;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.services.util.Views;

import java.util.List;
import java.util.Set;


public class MainView extends VerticalLayout implements View {
    Grid<Hotel> data = null;



    public void setUp(){

    this.setId("MainView");
    this.setStyleName("Main");
    this.setSizeFull();
    //this.addComponent(new Label("<hr />", ContentMode.HTML));



        //User user = (User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);

        User user = ((MyUI) UI.getCurrent()).getUser();

    String vorname = null;
        if(user != null) {
            vorname = user.getPrename();
        }
    Label label = new Label( vorname + ", Wo möchtest Du hin?");
    label.setStyleName("suchelabel");
    final TextField textField = new TextField();
    final Button button = new Button("Search", VaadinIcons.SEARCH);





    Button bucheButton = new Button("Buchen");

    HorizontalLayout horizLayout = new HorizontalLayout( label, textField, button);
    horizLayout.addStyleName("Suchleiste");




    button.addClickListener((Button.ClickListener) event -> {
        String ort = textField.getValue();



        if (ort.equals("")) {

            Notification.show(null, "Bitte Ort eingeben", Notification.Type.WARNING_MESSAGE);
        } else {


            List<Hotel> liste = null;
            try {
                liste = HotelSearch.getInstance().getHotelByOrt(ort);
            } catch (DataBaseException e) {
                Notification.show(null, e.getReason(), Notification.Type.HUMANIZED_MESSAGE);
            }

            if(liste.isEmpty()){
                    bucheButton.setVisible(false);
                    this.removeGrid(this, this.data);
                    Notification.show(null,"Die Suche war leider ergebnislos....", Notification.Type.WARNING_MESSAGE);
                    return;

                }

                this.removeGrid(this, this.data);
                this.data = this.createTable(liste.size());
                this.data.setItems(liste);
                this.addComponents(this.data, bucheButton);
                this.setComponentAlignment(this.data, Alignment.MIDDLE_CENTER);
                this.setComponentAlignment(bucheButton, Alignment.MIDDLE_CENTER);
                this.data.setWidth("90%");
                this.data.setHeightByRows(liste.size());
            bucheButton.setVisible(true);






        }
    });

    bucheButton.addClickListener(click -> {


    Set output =  this.data.getSelectedItems();


    if(output.isEmpty()){
        Notification.show(null, "Um zu buchen müssen Sie ein Hotel selektieren", Notification.Type.WARNING_MESSAGE);
        return;
    }


    //Hotel outputObject = (Hotel) output.iterator().next();
        Hotel myHotel = (Hotel) output.iterator().next();

        BookingWindow window = new BookingWindow(myHotel);
        UI.getCurrent().addWindow(window);



    });


        TopPanel panel = new TopPanel();
        this.addComponent(panel);
        this.addComponent(horizLayout);
        this.setComponentAlignment(panel, Alignment.TOP_CENTER);
        this.setComponentAlignment(horizLayout, Alignment.TOP_CENTER);





}

    private Grid<Hotel> createTable(int treffer){
        Grid<Hotel> table =  new Grid<>();
        table.setCaption("Treffer " + treffer);
        table.addColumn(Hotel::getName).setCaption("Name");
        table.addColumn(Hotel::getOrt).setCaption("Ort");
        table.addColumn(Hotel::getDescription).setCaption("Kurzbeschreibung");
        table.setSelectionMode(Grid.SelectionMode.SINGLE);

        table.getDefaultHeaderRow().setStyleName("mytableheader");
        table.setStyleName("mytable");



        return table;
    }


    private static void removeGrid(Layout layout, Component old) {

        if(old == null) return;

        layout.removeComponent(old);




    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        //User user = (User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);
        User user = ((MyUI) UI.getCurrent()).getUser();
        if( user == null) {
            UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
        } else {
            this.setUp();
        }


    }
}
