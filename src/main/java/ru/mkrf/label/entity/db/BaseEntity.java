package ru.mkrf.label.entity.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseEntity {
    protected Integer id;

    public BaseEntity() {
    }

    public BaseEntity(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public Boolean isNew() {
        return id == null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}