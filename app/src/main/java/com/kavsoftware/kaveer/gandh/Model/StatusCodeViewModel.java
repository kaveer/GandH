package com.kavsoftware.kaveer.gandh.Model;

/**
 * Created by kaveer on 1/21/2018.
 */

public class StatusCodeViewModel {
    private int StatusCode;
    private String Message;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
