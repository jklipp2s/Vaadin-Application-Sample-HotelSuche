package org.bonn.se.meinhotelapp.gui.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class MyTextField extends HorizontalLayout {
    TextField textField;
    Label textlabel;
    Label label;
    HorizontalLayout overLayout;

    public MyTextField(TextField textField, String placeholder, Label label) {

        overLayout = new HorizontalLayout();
        this.label = label;
        label.setStyleName("textfieldicon");
        textField.setWidth("280px");
        textlabel = new Label(placeholder);
        textlabel.setStyleName("textfieldplaceholder");
        overLayout.addComponents(label, textlabel);

        this.addComponents(textField, overLayout);



        textField.addFocusListener(focusEvent -> {

            if(overLayout.isAttached()) this.detachPlaceHolder();

        });

        textField.addBlurListener(blurEvent -> {
            if (textField.isEmpty()) this.attachPlaceHolder();
        });

        this.addLayoutClickListener(layoutClickEvent -> {

            textField.focus();

        });






    }


    public void detachPlaceHolder() {
        this.removeComponent(overLayout);
    }

    public void attachPlaceHolder() { this.addComponent(overLayout);}



}



