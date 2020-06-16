package org.bonn.se.process.control.exceptions;

public class RegisterFailException extends Exception {
    private String reason;

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }
}
