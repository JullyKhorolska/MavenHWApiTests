package org.example.restTests;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateObj {
    private String name;
    private String job;

    public CreateObj(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public CreateObj() {
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

}
