package ru.mkrf.label.entity;

public class ResponseEntity<T> {
    private Integer status;
    private T data;

    public ResponseEntity(T data, Integer status) {
        this.status = status;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}