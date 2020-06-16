package org.bonn.se.meinhotelapp.gui.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

import javax.swing.*;

public class MyLoginForm extends LoginForm {

    private TextField userNameField;
    private PasswordField passwordField;
    private Button loginButton;






    @Override
    protected Button createLoginButton() {
        return new Button("Login", VaadinIcons.SIGN_IN);
    }

    @Override
    protected Component createContent(TextField userNameField, PasswordField passwordField, Button loginButton) {


      this.userNameField = userNameField;
      this.passwordField =passwordField;
      this.loginButton = loginButton;


      this.loginButton.addStyleName("loginbutton");

     VerticalLayout output =(VerticalLayout) super.createContent(userNameField, passwordField, loginButton);

     output.setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);


     return output;
    }


    public TextField getUserNameField() {
        return userNameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }
}

