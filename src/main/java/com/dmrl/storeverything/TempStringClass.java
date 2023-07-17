package com.dmrl.storeverything;

public class TempStringClass {
    private String tempString;
    private Long ID;

    public TempStringClass() {}

    public TempStringClass(String input) {
        this.tempString = input;
    }
    public TempStringClass(Long input) {
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
