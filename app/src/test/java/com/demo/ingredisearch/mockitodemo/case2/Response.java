package com.demo.ingredisearch.mockitodemo.case2;

public class Response {

    private String data;

    public Response(String data) {
        super();
        this.data = data;
    }

    public boolean isSuccessful() {
        return this.data != null;
    }

    public String getData() {
        return data;
    }
}