package com.gallelloit.heroeditorbackend.core.dao.doc;

import java.io.Serializable;

public class Superpower{

    private String name;

    public Superpower(String name) {
        this.name = name;
    }

    public Superpower(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}