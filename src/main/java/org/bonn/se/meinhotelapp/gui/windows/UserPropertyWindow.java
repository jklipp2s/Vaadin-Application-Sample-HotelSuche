package org.bonn.se.meinhotelapp.gui.windows;

import com.vaadin.ui.*;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.JDBC.Repositories.UserRepository;
import org.bonn.se.process.control.JDBC.Repositories.UserToRoleRepository;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.services.util.Roles;

import java.util.ArrayList;
import java.util.List;

public class UserPropertyWindow extends Window {
    public UserPropertyWindow(User user, Grid<User> grid) {
        super("UserProperties");
        center();
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("Id: " +user.getId()));
        layout.addComponent(new Label("user: " +user.getUsername()));
        layout.addComponent(new Label("Name: " +user.getFullname()));


        ComboBox<String> roles = new ComboBox<>();
        roles.setCaption("Zur Domäne Hinzufügen");
        List<String> rolelist = new ArrayList<>();
        rolelist.add(Roles.NORMAL_USER);
        rolelist.add(Roles.POWER_USER);
        rolelist.add(Roles.ADMIN);
        roles.setItems(rolelist);
        roles.setTextInputAllowed(false);

        layout.addComponent(roles);

        Button button = new Button("anwenden");

        button.addClickListener(clickEvent -> {


                if(roles.getValue() == null) {
                    Notification.show(null, "Keine Änderungen durchgeführt", Notification.Type.HUMANIZED_MESSAGE);
                    this.close();
                    return;
                }
                if(user.hasRole(roles.getValue())){
                    Notification.show(null, "Sie können einem User nicht doppelt eine Domäne übergeben.", Notification.Type.WARNING_MESSAGE);
                    return;
                }

            try {
                UserToRoleRepository.insertIntoTable_User_To_Role(user.getUsername(),roles.getValue());
                grid.setItems(UserRepository.getAllRegisteredUsers());
                this.close();

            } catch (DataBaseException e) {
                e.printStackTrace();
            }
        });

        layout.addComponent(button);
        layout.setComponentAlignment(button, Alignment.BOTTOM_CENTER);


        this.setContent(layout);




    }
}
