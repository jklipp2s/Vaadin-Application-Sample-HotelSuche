package org.bonn.se.meinhotelapp.gui.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.Header;
import org.bonn.se.meinhotelapp.gui.components.MinimizedTopBar;
import org.bonn.se.meinhotelapp.gui.components.MyTextField;
import org.bonn.se.meinhotelapp.gui.components.TopPanel;
import org.bonn.se.meinhotelapp.gui.windows.RegistrationSuccessWindow;
import org.bonn.se.model.objects.dto.UserDTO;
import org.bonn.se.process.control.JDBC.DataBaseConnection;
import org.bonn.se.process.control.JDBC.DataBaseCreation;
import org.bonn.se.process.control.JDBC.Repositories.UserRepository;
import org.bonn.se.process.control.RegistationControl;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.process.control.exceptions.RegisterFailException;
import org.bonn.se.services.util.ConnectionTypes;

import java.sql.Connection;
import java.util.regex.Pattern;

public class RegistrationView extends VerticalLayout implements View {


    private void setUp() {
        this.setId("registrationView");
        this.setSizeFull();


        VerticalLayout outer = new VerticalLayout();
        outer.setStyleName("registrationouter");
        this.setSizeFull();

        VerticalLayout form = new VerticalLayout();
        Panel panel = new Panel();

        form.setWidth("450px");


        Label headLabel = new Label("Registration");
        headLabel.setStyleName("registrationlabel");

        panel.setStyleName("registrationpanel");

        TextField nameField = getModifiedTextfield();
        Label nameIconLabel = new Label();
        nameIconLabel.setIcon(VaadinIcons.CARET_RIGHT);
        MyTextField nameTextField = new MyTextField(nameField, "enter your name....", nameIconLabel);
        HorizontalLayout nameWrapper = new HorizontalLayout(new Label("Name"), nameTextField);
        nameWrapper.setWidth("180px");

        nameWrapper.addStyleName("namefield");


        form.addComponents(headLabel, nameWrapper);


        TextField prenameField = getModifiedTextfield();
        Label prenameIconLabel = new Label();
        prenameIconLabel.setIcon(VaadinIcons.CARET_RIGHT);
        MyTextField prenameTextField = new MyTextField(prenameField, "enter your prename....", prenameIconLabel);
        HorizontalLayout prenameWrapper = new HorizontalLayout(new Label("Prename"), prenameTextField);
        prenameWrapper.setWidth("180px");


        TextField emailField = getModifiedTextfield();
        Label emailIconLabel = new Label();
        emailIconLabel.setIcon(VaadinIcons.ENVELOPE_O);
        MyTextField emailTextField = new MyTextField(emailField, "@userexample.de", emailIconLabel);
        HorizontalLayout emailWrapper = new HorizontalLayout(new Label("Email"), emailTextField);
        emailWrapper.setWidth("180px");


        TextField userField = getModifiedTextfield();
        Label userIconLabel = new Label();
        userIconLabel.setIcon(VaadinIcons.USER);
        MyTextField userTextField = new MyTextField(userField, "enter your username", userIconLabel);
        HorizontalLayout usernameWrapper = new HorizontalLayout(new Label("Username"), userTextField);
        usernameWrapper.setWidth("180px");


        PasswordField entrancePasswordField = new PasswordField();
        Label passwordIconLabel = new Label();
        passwordIconLabel.setIcon(VaadinIcons.KEY_O);
        MyTextField passwordTextField = new MyTextField(entrancePasswordField, "enter your password....", passwordIconLabel);
        HorizontalLayout passwordWrapper = new HorizontalLayout(new Label("Password"), passwordTextField);
        passwordWrapper.setWidth("180px");


        Button submit = new Button("register");
        submit.addStyleName("submitButton");

        submit.addClickListener(clickEvent -> {

            UserDTO user = new UserDTO();


            user.setName(nameField.getValue());
            user.setPrename(prenameField.getValue());
            user.setEmail(emailField.getValue());
            user.setUsername(userField.getValue());
            user.setPassword(entrancePasswordField.getValue());


            try {
                if (RegistationControl.register(user)) {

                    RegistrationSuccessWindow registr = new RegistrationSuccessWindow(userField.getValue());
                    UI.getCurrent().addWindow(registr);
                }


            } catch (DataBaseException e) {
                Notification.show(null, "Check if VPN is on!", Notification.Type.WARNING_MESSAGE);
            } catch (RegisterFailException e) {
                Notification.show(null, e.getReason(), Notification.Type.ERROR_MESSAGE);
            }


        });


        form.addComponents(prenameWrapper, emailWrapper, usernameWrapper, passwordWrapper, submit);
        form.setComponentAlignment(submit, Alignment.MIDDLE_CENTER);
        panel.setContent(form);
        panel.setWidthUndefined();

        this.addComponent(new MinimizedTopBar("Registration"));
        outer.addComponent(panel);

        this.addComponent(outer);

        final HorizontalLayout errorfield = new HorizontalLayout();
        Label error = new Label("Der Username ist bereits vergeben!");
        errorfield.addComponent(error);
        errorfield.setWidth("100%");
        errorfield.setStyleName("errorfieldwarning");

        userField.addValueChangeListener(valueChangeEvent -> {
            try {


                DataBaseConnection.setUPConnection(ConnectionTypes.CONNECTION_TYPE_PostgreSQL);
                if (UserRepository.userIsAlreadyDefined(userField.getValue()) && !userField.isEmpty() && form.getComponentIndex(errorfield) == -1) {

                    form.addComponent(errorfield, form.getComponentIndex(usernameWrapper));
                    errorfield.setComponentAlignment(error, Alignment.MIDDLE_CENTER);

                    form.removeComponent(submit);

                } else {
                    if (form.getComponentIndex(errorfield) != -1) {
                        form.removeComponent(form.getComponent(form.getComponentIndex(errorfield)));
                        form.addComponent(submit);
                        form.setComponentAlignment(submit, Alignment.MIDDLE_CENTER);

                    }
                }
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
        });
        userField.setValueChangeMode(ValueChangeMode.LAZY);

        final HorizontalLayout errorfield2 = new HorizontalLayout();
        ;
        Label error2 = new Label("Bitte wählen Sie eine valide Email!");
        errorfield2.addComponent(error2);
        errorfield2.setWidth("100%");

        errorfield2.setStyleName("errorfieldwarning");

        emailField.addBlurListener(valueChangeEvent -> {
            try {
                DataBaseConnection.setUPConnection(ConnectionTypes.CONNECTION_TYPE_PostgreSQL);
                if (!isValid(emailField.getValue()) && form.getComponentIndex(errorfield2) == -1 && !emailField.isEmpty()) {
                    form.addComponent(errorfield2, form.getComponentIndex(emailWrapper));
                    errorfield2.setComponentAlignment(error2, Alignment.MIDDLE_CENTER);
                    form.removeComponent(submit);
                }
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
        });

        emailField.addValueChangeListener(valueChangeEvent -> {
            try {
                DataBaseConnection.setUPConnection(ConnectionTypes.CONNECTION_TYPE_PostgreSQL);
                if (isValid(emailField.getValue()) && form.getComponentIndex(errorfield2) != -1 || form.getComponentIndex(errorfield2) != -1 && emailField.isEmpty()) {

                    form.removeComponent(form.getComponent(form.getComponentIndex(errorfield2)));
                    form.addComponent(submit);
                    form.setComponentAlignment(submit, Alignment.MIDDLE_CENTER);
                }
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
        });
        emailField.setValueChangeMode(ValueChangeMode.LAZY);

    }


    private TextField getModifiedTextfield() {

        TextField result = new TextField();
        result.setWidth("280px");

        return result;
    }


    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp();
    }


}


