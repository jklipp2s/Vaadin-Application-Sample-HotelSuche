package org.bonn.se.meinhotelapp.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.bonn.se.services.util.TableNames;
import org.bonn.se.services.util.Views;

public class LandingPage extends VerticalLayout implements View {


    public void setUp() {

        this.setMargin(false);


        this.setId("landingPage");

        this.setSizeFull();

        HorizontalLayout outer = new HorizontalLayout();


        Button registerButton = new Button("Registrieren");
        Button loginButton = new Button("Login");

        loginButton.addClickListener(event -> {
           UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
        });

        registerButton.addClickListener(event -> {
            UI.getCurrent().getNavigator().navigateTo(Views.REGISTRATION);
        });


        Label welcomeText = new Label("Thank you for visiting our Homepage! Please register to use our Functions!");
        Image logo = new Image(null, new ThemeResource("img/Logo.png"));
        logo.setWidth( 300 ,Unit.PIXELS);

        VerticalLayout innerVert = new VerticalLayout();
        VerticalLayout innerVert2 = new VerticalLayout();


        innerVert.setStyleName("innerVert");
        innerVert2.setStyleName("innerVert2");

        VerticalLayout buttons= new VerticalLayout();
        buttons.addComponent(registerButton);
        buttons.addComponent(loginButton);
        registerButton.setStyleName("registerButton");
        loginButton.setStyleName("loginButton");
        buttons.setStyleName("buttonBox");



        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("outer-inner");

        innerVert2.addComponent(buttons);
        innerVert.addComponents(logo, welcomeText);
        outer.addComponents(innerVert, innerVert2);
        horizontalLayout.addComponent(outer);
        addComponent(horizontalLayout);
        outer.setStyleName("MainContainer");

        welcomeText.addStyleName("welcomeText");






    }






    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.setUp();
    }
}
