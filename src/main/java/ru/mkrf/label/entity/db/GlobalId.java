package ru.mkrf.label.entity.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GlobalId {
    Integer treeId;

    public GlobalId() {
    }

    public GlobalId(Integer treeId) {
        this.treeId = treeId;
    }

    @JsonIgnore
    public Boolean isNew() {
        return treeId == null;
    }

    @JsonProperty("id")
    public Integer getTreeId() {
        return treeId;
    }

    public void setTreeId(Integer treeId) {
        this.treeId = treeId;
    }
}
