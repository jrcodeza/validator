package com.jrcodeza.validator.engine;

import javax.validation.constraints.NotNull;

public class Sample {

    private String fieldA;

    @NotNull
    private Integer fieldB;


    public String getFieldA() {
        return fieldA;
    }

    public void setFieldA(String fieldA) {
        this.fieldA = fieldA;
    }

    public Integer getFieldB() {
        return fieldB;
    }

    public void setFieldB(Integer fieldB) {
        this.fieldB = fieldB;
    }
}
