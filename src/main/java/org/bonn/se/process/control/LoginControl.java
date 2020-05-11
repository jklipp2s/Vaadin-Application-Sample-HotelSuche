package org.bonn.se.process.control;

import com.vaadin.ui.UI;
import org.bonn.se.meinhotelapp.gui.ui.MyUI;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.JDBC.DataBaseConnection;
import org.bonn.se.process.control.JDBC.Repositories.UserRepository;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.process.control.exceptions.NoSuchUserOrPassword;
import org.bonn.se.services.util.ConnectionTypes;
import org.bonn.se.services.util.Views;

public class LoginControl {


    public static void checkAuthentication(String login, String password) throws NoSuchUserOrPassword, DataBaseException {


        DataBaseConnection.setUPConnection(ConnectionTypes.CONNECTION_TYPE_PostgreSQL);

        User entranceUser = UserRepository.getLoginUser(login);

        if(entranceUser!=null && entranceUser.getPassword().equals(password)){
            //VaadinSession session = UI.getCurrent().getSession();
            //session.setAttribute(Roles.CURRENT_USER, entranceUser);

            ((MyUI) UI.getCurrent()).setUser(entranceUser);


            UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
        } else {
            throw new NoSuchUserOrPassword();
        }





    }


    public static void logoutUser() {

        UI.getCurrent().close();
        UI.getCurrent().getPage().setLocation("/MeineHotelSucheApp_war_exploded");
        // UI.getCurrent().getSession().close();
    }
}
