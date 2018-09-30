package com.han.demo.developerdemo;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {
    public static final String TAG = "GetDeviceInfo";
    public static Method getMethod(String method, Class clazz) {
        try {
            return clazz.getDeclaredMethod(method);
        } catch (NoSuchMethodException ex) {
            Log.d(TAG, "getMethod: " + method + ", " + ex.getMessage());
        }
        return null;
    }

    public static Method getMethod(Class clazz, String method, Class paramClazz) {
        try {
            return clazz.getDeclaredMethod(method, paramClazz);
        } catch (NoSuchMethodException ex) {
            Log.d(TAG, "getMethod: " + method + ", " + ex.getMessage());
        }
        return null;
    }


    public static Field getDeclaredField(Class clazz, String fieldName) {
        try {

            Field field =  clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.d(TAG, "getDeclaredField: " + fieldName + ", " + e.getMessage());
        }
        return null;
    }

    public static Object get(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getObjectValue(String fieldName, Object object) {
        try {
            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return get(field, object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Log.d(TAG, "getField: " + fieldName + ", " + e.getMessage());
        }
        return null;
    }

    public static void setFieldObject(Object object, String fieldName, Object value) {
        try {
            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception ex) {
            Log.d(TAG, "setField: " + fieldName + ", " + ex.getMessage());

        }
    }

    public static void setFieldInt(Object object, String fieldName, int value) {
        try {
            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setInt(object, value);
        } catch (Exception ex) {
            Log.d(TAG, "setField: " + fieldName + ", " + ex.getMessage());

        }
    }

    public static void setFieldBoolean(Object object, String fieldName, Boolean value) {
        try {
            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setBoolean(object, value);
        } catch (Exception ex) {
            Log.d(TAG, "setField: " + fieldName + ", " + ex.getMessage());

        }
    }

    public static void setFieldLong(Object object, String fieldName, long value) {
        try {
            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setLong(object, value);
        } catch (Exception ex) {
            Log.d(TAG, "setField: " + fieldName + ", " + ex.getMessage());

        }
    }

    public static void setFieldDouble(Object object, String fieldName, double value) {
        try {
            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setDouble(object, value);
        } catch (Exception ex) {
            Log.d(TAG, "setField: " + fieldName + ", " + ex.getMessage());

        }
    }

    public static boolean hasField(String fieldName, Class clazz) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return true;
        } catch (Exception ex) {
            Log.d(TAG, "hasField: " + fieldName + ", " + ex.getMessage());
            return false;
        }
    }


    public static Object createObject(String clazzName)  {
        try {
            Class<?> clazz = Class.forName(clazzName);
            return clazz.newInstance();
        } catch (ClassNotFoundException ex) {

        } catch (IllegalAccessException ex) {

        } catch (InstantiationException ex) {

        }

        return null;
    }
}
