package ru.mkrf.label.util.rowmapper;

public class RowMapperUtil {
    public static Integer checkString(String el) {
        return el == null ? null : new Integer(el);
    }
}
