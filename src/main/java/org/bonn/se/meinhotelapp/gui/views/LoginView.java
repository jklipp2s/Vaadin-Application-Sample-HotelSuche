package org.bonn.se.meinhotelapp.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.bonn.se.meinhotelapp.gui.components.MyLoginForm;
import org.bonn.se.meinhotelapp.gui.ui.MyUI;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.LoginControl;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.process.control.exceptions.NoSuchUserOrPassword;
import org.bonn.se.services.util.Views;


public class LoginView extends VerticalLayout implements View {


    public void setUp() {
        this.setId("loginform");
        Label label = new Label("Login");
        this.setSizeFull();


        Panel panel = new Panel();
        removeStyleName("v-margin-top");
        panel.setStyleName("loginpanel");


        MyLoginForm loginForm = new MyLoginForm();
        loginForm.setWidth("350");



        VerticalLayout verticalLayout = new VerticalLayout(label,loginForm);
        double panelwidth = loginForm.getWidth();
        verticalLayout.setWidth(String.valueOf(panelwidth));
        verticalLayout.setMargin(false);
        panel.setContent(verticalLayout);




        panel.setWidth(String.valueOf(panelwidth));



        this.addComponent(panel);
        this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);




        loginForm.getLoginButton().addClickListener(click -> {

            String login = loginForm.getUserNameField().getValue();
            String password = loginForm.getPasswordField().getValue();

            try {
                LoginControl.checkAuthentication(login,password);

            } catch (NoSuchUserOrPassword ex) {
                Notification.show("BenutzerFehler", "No such user or password", Notification.Type.ERROR_MESSAGE);
                loginForm.getUserNameField().clear();
                loginForm.getPasswordField().clear();





            } catch (DataBaseException e) {
                Notification.show("DBFEHLER", e.getReason() , Notification.Type.ERROR_MESSAGE);
                loginForm.getUserNameField().clear();
                loginForm.getPasswordField().clear();
            }


        });



    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

      //  User user = (User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);

        User user = ((MyUI) UI.getCurrent()).getUser();

        if(user != null) {
            UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
        } else {


            this.setUp();
        }
    }
}
