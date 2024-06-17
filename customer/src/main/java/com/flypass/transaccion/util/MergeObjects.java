package com.flypass.transaccion.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MergeObjects {

    private MergeObjects() {
    }

    public static <T> T mergeObjects(T dbObject, Object request) {
        try {
            Class<?> recordClass = dbObject.getClass();
            Field[] fields = recordClass.getDeclaredFields();

            Object[] initargs = new Object[fields.length];
            Class<?>[] parameterTypes = new Class<?>[fields.length];

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);

                String fieldName = field.getName();
                Method getter = recordClass.getDeclaredMethod(fieldName);
                Object recordValue = getter.invoke(dbObject);

                Field requestField = null;
                try {
                    requestField = request.getClass().getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    // Field does not exist in the request, continue with record value
                    initargs[i] = recordValue;
                    parameterTypes[i] = field.getType();
                    continue;
                }

                requestField.setAccessible(true);
                Object requestValue = requestField.get(request);

                initargs[i] = (requestValue != null) ? requestValue : recordValue;
                parameterTypes[i] = field.getType();
            }

            Constructor<?> constructor = recordClass.getDeclaredConstructor(parameterTypes);
            return (T) constructor.newInstance(initargs);
        } catch (Exception e) {
            throw new RuntimeException("Error updating record", e);
        }
    }

}
