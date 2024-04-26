package com.chaabiamal.springboot_cassandra_demo.model;

import java.util.UUID;

public class Status {
        private int ID;
        private TypeStatus Value;
        private String Description;


//*******************************************


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public TypeStatus getValue() {
        return Value;
    }

    public void setValue(TypeStatus value) {
        Value = value;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    }
