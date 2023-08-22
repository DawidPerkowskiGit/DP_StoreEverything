package com.dmrl.storeverything;

/**
 * Workaround class used to convert View Date Picker value to String and other way around
 */
public class DataPickerStringConverter {
    private String tempString;
    private Long ID;

    public DataPickerStringConverter() {}

    public DataPickerStringConverter(String input) {
        this.tempString = input;
    }
    public DataPickerStringConverter(Long input) {
        this.ID = input;
    }


    public String getTempString() {
        return tempString;
    }

    public void setTempString(String tempString) {
        this.tempString = tempString;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
