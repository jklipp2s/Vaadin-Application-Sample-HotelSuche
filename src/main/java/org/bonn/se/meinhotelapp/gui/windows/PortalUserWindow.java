package org.bonn.se.meinhotelapp.gui.windows;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.bonn.se.meinhotelapp.gui.components.GridCallbackValueProvider;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.JDBC.Repositories.UserRepository;
import org.bonn.se.process.control.exceptions.DataBaseException;

import java.util.List;

public class PortalUserWindow extends Window {
    private Grid<User> data;
    List<User> list;
    public PortalUserWindow() {
        super(" Alle Nutzer Auf einen Blick!");
        this.setIcon(VaadinIcons.USER_CHECK);
        center();
        this.setSizeFull();
        setResizable(false);

        VerticalLayout layout = new VerticalLayout();



        data = new Grid<>();
        data.setStyleName("usergrid");
        data.addColumn(User::getId).setCaption("nr").setWidth(50);
        data.addColumn(User::getUsername).setCaption("Username");
        data.addColumn(User::getFullname).setCaption("Name");
        data.addColumn(User::getEmail).setCaption("Email");
        data.addColumn(User::getRolesAsList).setCaption("hinterlegt als");
        data.setSelectionMode(Grid.SelectionMode.SINGLE);
        data.setSizeFull();

        try {
            list = UserRepository.getAllRegisteredUsers();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        data.setItems(list);
        data.setHeightByRows(list.size());
        data.addComponentColumn(new GridCallbackValueProvider(data)).setCaption("status").setId("status").setWidth(85);



        layout.addComponent(data);

        this.setContent(layout);



    }
}
