package com.gallelloit.heroeditorbackend.core.dao.doc;

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

    @Override
    public int hashCode() {
        int hashed = 1;
        if (name != null) {
            hashed = hashed * 31 + name.hashCode();
        }
        return hashed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        if (obj == this)
            return true;
        Superpower other = (Superpower) obj;
        return this.hashCode() == other.hashCode();
    }
}