package ru.mkrf.label.util;

import java.lang.reflect.Field;

public class TestUtil {
//    public static void genObjectForSave(Class cl, Object obj) throws IllegalAccessException {
//        Column annotation;
//
//        do {
//            for (Field field : cl.getDeclaredFields()) {
//                field.setAccessible(true);
//
//                if (field.isAnnotationPresent(Column.class)) {
//                    annotation = field.getAnnotation(Column.class);
//
//                    if(field.getType().equals(String.class)) {
//                        field.set(obj, annotation.name() + "_Insert");
//                    } else if(field.getType().equals(Integer.class)) {
////                        field.set(obj, 0);
//                    }
//                }
//            }
//
//            cl = cl.getSuperclass();
//        } while (cl != null);
//    }
//
//    public static void genObjectForUpdate(Class cl, Object obj) throws IllegalAccessException {
//        Column annotation;
//
//        do {
//            for (Field field : cl.getDeclaredFields()) {
//                field.setAccessible(true);
//
//                if (field.isAnnotationPresent(Column.class)) {
//                    annotation = field.getAnnotation(Column.class);
//
//                    if(field.getType().equals(String.class)) {
//                        field.set(obj, annotation.name() + "_Update");
//                    } else if(field.getType().equals(Integer.class)) {
////                        field.set(obj, 0);
//                    }
//                }
//            }
//
//            cl = cl.getSuperclass();
//        } while (cl != null);
//    }
}
