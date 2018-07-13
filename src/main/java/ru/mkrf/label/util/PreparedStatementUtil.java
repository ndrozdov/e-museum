package ru.mkrf.label.util;

import org.springframework.web.bind.annotation.GetMapping;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class PreparedStatementUtil {
//    public static PreparedStatement createPreparedStatement(
//            Connection conn
//            , String sqlString
//            , Integer id
//    )  throws SQLException {
//        PreparedStatement ps = conn.prepareStatement(sqlString);
//        ps.setInt(1, id);
//        return ps;
//    }

//    public static Map<String, ?> genMapParameters(Object obj) throws IllegalAccessException {
//        Class cl = obj.getClass();
//        Map<String, Object> result = new HashMap<>();
//
//        do {
//            for (Field field : cl.getDeclaredFields()) {
//                field.setAccessible(true);
//
//                if(field.getType().isAssignableFrom(LocalDateTime.class))
//                    result.put(field.getName(), field.get(obj).toString());
//
//                result.put(field.getName(), field.get(obj));
//            }
//
//            cl = cl.getSuperclass();
//        } while (cl != null);
//
//        return result;
//    }

//    public static LocalDateTime parseLocalDateTime(String date) {
//        if(date == null)
//            return null;
//
//        try {
//            LocalDateTime res = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            return res;
//        } catch (DateTimeParseException ex) {
//            return null;
//        }
//    }


    public static String genInsertQuery(Class cl) throws IllegalArgumentException {
        final String template = "INSERT INTO :table_name ( :columns ) VALUES ( :values );";
        String tableName = genTableName(cl);
        List<String> fields = genFieldsName(cl);

        String columns = fields.stream().map(PreparedStatementUtil::toTableNotation).collect(Collectors.joining(", "));
        String values = fields.stream().map(f -> ":" + f).collect(Collectors.joining(", "));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("table_name", tableName);
        parameters.put("columns", columns);
        parameters.put("values", values);

        return genSqlResult(template, parameters);
    }

    public static String genUpdateQueryWithTreeId(Class cl) throws IllegalArgumentException {
        final String template = "UPDATE :table_name SET :column_and_value WHERE tree_id = :treeId;";
        String tableName = genTableName(cl);
        List<String> fields = genFieldsName(cl);

        String columnAndValue = fields.stream()
                .map(f -> toTableNotation(f) + " = :" + f)
                .collect(Collectors.joining(", "));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("table_name", tableName);
        parameters.put("column_and_value", columnAndValue);

        return genSqlResult(template, parameters);
    }

    public static String genUpdateQuery(Class cl) throws IllegalArgumentException {
        final String template = "UPDATE :table_name SET :column_and_value WHERE rowid = :rowid;";
        String tableName = genTableName(cl);
        List<String> fields = genFieldsName(cl);

        String columnAndValue = fields.stream()
                .map(f -> toTableNotation(f) + " = :" + f)
                .collect(Collectors.joining(", "));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("table_name", tableName);
        parameters.put("column_and_value", columnAndValue);

        return genSqlResult(template, parameters);
    }

    public static String genSelectQuery(Class cl) throws IllegalArgumentException {
        final String template = "SELECT :columns FROM :table_name;";
        String tableName = genTableName(cl);
        List<String> fields = genFieldsName(cl);

        String columns = fields.stream()
                .map(PreparedStatementUtil::toTableNotation)
                .collect(Collectors.joining(", "));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("table_name", tableName);
        parameters.put("columns", columns);

        return genSqlResult(template, parameters);
    }

    public static String genSelectQueryByRowId(Class cl) throws IllegalArgumentException {
        final String template = "SELECT :columns FROM :table_name WHERE rowid = :rowid;";

        String tableName = genTableName(cl);
        List<String> fields = genFieldsName(cl);

        String columns = fields.stream()
                .map(PreparedStatementUtil::toTableNotation)
                .collect(Collectors.joining(", "));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("table_name", tableName);
        parameters.put("columns", columns);

        return genSqlResult(template, parameters);
    }

    public static String genDeleteQuery(Class cl) throws IllegalArgumentException {
        final String template = "DELETE FROM :table_name WHERE rowid = :rowid;";
        String tableName = genTableName(cl);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("table_name", tableName);

        return genSqlResult(template, parameters);
    }

    private static String genTableName(Class cl) throws IllegalArgumentException {
        String tableName = cl.getSimpleName().toLowerCase();

        if(tableName.equals(""))
            throw new IllegalArgumentException("Table name is null");

        return tableName;
    }

    private static List<String> genFieldsName(Class cl) {
        List<String> fields = new ArrayList<>();

        do {
            for (Field field : cl.getDeclaredFields()) {
                field.setAccessible(true);
                if((field.getModifiers() & Modifier.FINAL) == 0)
                    fields.add(field.getName());
            }

            cl = cl.getSuperclass();
        } while (cl != null);

        return fields;
    }

    private static String toTableNotation(String str) {
        return Arrays.stream(str.split("(?<=[a-z])(?=[A-Z])"))
                .map(String::toLowerCase)
                .collect(Collectors.joining("_"));
    }

    private static String genSqlResult(String template, Map<String, String> parameters) {
        for(Map.Entry<String, String> entry : parameters.entrySet()) {
            template = template.replaceAll(":" + entry.getKey(), entry.getValue());
        }

        return template;
    }

//    public static String genUpdateQuery(Class cl) throws IllegalArgumentException {
//        String tableName = "";
//
//        if(cl.isAnnotationPresent(Table.class))
//            tableName = ((Table)cl.getAnnotation(Table.class)).name();
//
//        if(tableName.equals(""))
//            throw new IllegalArgumentException("Table name is null");
//
//        Column columnAnnontaion;
//        StringBuilder result = new StringBuilder(
//                "UPDATE \n" + tableName + "\n" +
//                "SET \n"
//        );
//
////        do {
//            for (Field field : cl.getDeclaredFields()) {
//                field.setAccessible(true);
//
//                if (field.isAnnotationPresent(Column.class)) {
//                    columnAnnontaion = field.getAnnotation(Column.class);
//                    result.append(columnAnnontaion.name() + " = :" + field.getName() + ", ");
//                }
//            }
//
//            cl = cl.getSuperclass();
////        } while (cl != null);
//
//        result
//                .deleteCharAt(result.lastIndexOf(","))
//                .append(" \n")
//                .append("WHERE \nrowid = :id");
//
//        return result.toString();
//    }

//    public static void mapResultSetToObject(SqlRowSet rs, Class cl, Object obj) throws SQLException, IllegalAccessException {
//        Class _cl;
//        SqlRowSetMetaData srsmt = rs.getMetaData();
//        String columnName;
//        Object columnValue;
//
//        for (int i = 1; i < srsmt.getColumnCount() + 1; i++) {
//            _cl = cl;
//            columnName = srsmt.getColumnName(i);
//            columnValue = rs.getObject(i);
//            Column annotation;
//
//            do {
//                for (Field field : _cl.getDeclaredFields()) {
//                    field.setAccessible(true);
//
//                    if (field.isAnnotationPresent(Column.class)) {
//                        annotation = field.getAnnotation(Column.class);
//
//                        if (annotation.name().equalsIgnoreCase(columnName)) {
//                            field.set(obj, columnValue);
//                        }
//                    }
//                }
//
//                _cl = _cl.getSuperclass();
//            } while (_cl != null);
//        }
//    }

    public static String getUrlRestMethod(Method method) {
        if(method.isAnnotationPresent(GetMapping.class)) {
            GetMapping annotation = method.getAnnotation(GetMapping.class);
            return annotation.value()[0];
        } else {
            return null;
        }
    }
}