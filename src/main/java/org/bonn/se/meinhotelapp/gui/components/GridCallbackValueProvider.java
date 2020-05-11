package org.bonn.se.meinhotelapp.gui.components;


import com.vaadin.data.ValueProvider;
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
        HorizontalLayout al = new HorizontalLayout(new Label("bearbeiten"));

        //al.setWidth("100px");
        //al.setHeight("30px");
        al.addStyleName(((user.hasRole(Roles.NORMAL_USER)) ? "blue bearbeiten" : "red bearbeiten" ));
        al.addLayoutClickListener( clickEvent -> {
            UserPropertyWindow userPropertyWindow = new UserPropertyWindow(user, grid);
            UI.getCurrent().addWindow(userPropertyWindow);

            grid.deselectAll();



        });
        return al;
    }

}