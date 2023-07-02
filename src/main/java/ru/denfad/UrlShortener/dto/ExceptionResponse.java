package ru.denfad.UrlShortener.dto;

import java.util.Date;

public class ExceptionResponse {

    private Date date;
    private String message;
    private int code;

    public ExceptionResponse(Date date, String message, int code) {
        this.date = date;
        this.message = message;
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
