package org.bonn.se.meinhotelapp.gui.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.bonn.se.meinhotelapp.gui.views.LandingPage;
import org.bonn.se.meinhotelapp.gui.views.LoginView;
import org.bonn.se.meinhotelapp.gui.views.MainView;
import org.bonn.se.meinhotelapp.gui.views.RegistrationView;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.services.util.Views;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Title("MeinHotel")
@PreserveOnRefresh
public class MyUI extends UI {

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Navigator navi = new Navigator(this,this);
        navi.addView(Views.MAIN, MainView.class);
        navi.addView(Views.LOGIN, LoginView.class);
        navi.addView(Views.LANDINGPAGE, LandingPage.class);
        navi.addView(Views.REGISTRATION, RegistrationView.class);

        getCurrent().getNavigator().navigateTo(Views.LANDINGPAGE);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }


    public MyUI geMyUI() {
        return (MyUI) UI.getCurrent();
    }



}
