package com.jilet.nman.response;

import lombok.Data;

@Data
public class ResponseFormat {
    private String name;
    private String library;
    private String synopsis;
    private String description;
    private String returnValue;
    private String notes;
    private String seeAlso;
    private String example;
}
