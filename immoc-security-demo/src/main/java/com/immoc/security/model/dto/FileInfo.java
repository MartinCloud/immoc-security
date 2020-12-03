package com.immoc.security.model.dto;

public class FileInfo {
    private String name;

    public FileInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
