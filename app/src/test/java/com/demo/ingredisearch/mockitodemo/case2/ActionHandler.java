package com.demo.ingredisearch.mockitodemo.case2;

public class ActionHandler {

    private Service service;
    private String value;

    public ActionHandler(Service service) {
        this.service = service;
    }

    public void doRequest() {
        service.getResponse("our-request", new Callback() {
            @Override
            public void reply(Response response) {
                if (response.isSuccessful()) {
                    setValue(response.getData());
                } else {
                    setValue(null);
                }
            }
        });
    }

    private void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}