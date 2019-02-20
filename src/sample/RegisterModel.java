package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Lampel on 10/02/2019.
 */
public class RegisterModel {

    private SimpleStringProperty registerName;
    private SimpleStringProperty offsetValue;

    public RegisterModel(String registerName, String offsetValue) {

        this.registerName = new SimpleStringProperty(registerName);
        this.offsetValue = new SimpleStringProperty(offsetValue);
    }

    public String getRegisterName() {
        return registerName.get();
    }

    public SimpleStringProperty registerNameProperty() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName.set(registerName);
    }

    public String getOffsetValue() {
        return offsetValue.get();
    }

    public SimpleStringProperty offsetValueProperty() {
        return offsetValue;
    }

    public void setOffsetValue(String offsetValue) {
        this.offsetValue.set(offsetValue);
    }
}
