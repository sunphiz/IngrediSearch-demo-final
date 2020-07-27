package com.demo.ingredisearch.mockitodemo.case1;

public class CalcService {

    private AddService addService;

    public CalcService(AddService addService) {
        this.addService = addService;
    }

    public int calc(int a, int b) {
        return addService.add(a, b);
    }
}