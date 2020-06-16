package org.bonn.se.meinhotelapp.gui.components;


import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.bonn.se.meinhotelapp.gui.windows.PortalUserWindow;
import org.bonn.se.meinhotelapp.gui.windows.UserPropertyWindow;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.services.util.Roles;

import java.util.Iterator;
import java.util.List;

public  class GridCallbackValueProvider
        implements ValueProvider<User, Layout> {


    private final Grid<User> grid;


    public GridCallbackValueProvider(Grid<User> grid) {
        this.grid = grid;
    }

    @Override
    public Layout apply(User user) {
        HorizontalLayout al = new HorizontalLayout();
        Button button = new Button();

        al.addComponent(button);
       
        button.setIcon(user.hasRole(Roles.ADMIN) ? VaadinIcons.KEY_O :
                   user.hasRole(Roles.NORMAL_USER) ? VaadinIcons.SMILEY_O  : VaadinIcons.FROWN_O);
        button.addStyleName(((user.hasRole(Roles.NORMAL_USER)) ? "blue" : "red" ));
        button.addClickListener( clickEvent -> {
            UserPropertyWindow userPropertyWindow = new UserPropertyWindow(user, grid);
            UI.getCurrent().addWindow(userPropertyWindow);

            grid.deselectAll();



        });
        return al;
    }

}