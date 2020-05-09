package org.bonn.se.process.control.exceptions;

public class DataBaseException extends Exception{

    private String reason = null;

    public DataBaseException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
