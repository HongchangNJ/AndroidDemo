package com.han.demo.developerdemo;

import android.net.wifi.ScanResult;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RestoreSRList {

    /**
     * @param resultListStr
     * @return
     */
    public static List<ScanResult> restoreScanResult(String resultListStr) {
        List<ScanResult> resultList = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(resultListStr);


            int size = jsonArray.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ScanResult scanResult =
                        (ScanResult) ReflectUtils.createObject("android.net.wifi.ScanResult");
                if (jsonObject.has("level")) {
                    scanResult.level = jsonObject.getInt("level");
                }

                if (jsonObject.has("BSSID")) {
                    scanResult.BSSID = jsonObject.getString("BSSID");
                }

                if (jsonObject.has("capabilities")) {
                    scanResult.capabilities = jsonObject.getString("capabilities");
                }

                if (jsonObject.has("SSID")) {
                    scanResult.SSID = jsonObject.getString("SSID");
                }

                if (jsonObject.has("hessid")) {
                    ReflectUtils.setFieldLong(scanResult, "hessid", jsonObject.getLong("hessid"));
                }

                //scanResult.operatorFriendlyName = wifi.operatorFriendlyName;
                if (jsonObject.has("anqpDomainId")) {
                    ReflectUtils.setFieldObject(scanResult, "anqpDomainId", jsonObject.getString("anqpDomainId"));
                }

                if (jsonObject.has("frequency")) {
                    scanResult.frequency = jsonObject.getInt("frequency");
                }

                if (jsonObject.has("distanceCm")) {
                    ReflectUtils.setFieldInt(scanResult, "distanceCm", jsonObject.getInt("distanceCm"));
                }

                if (jsonObject.has("distanceSdCm")) {
                    ReflectUtils.setFieldInt(scanResult, "distanceSdCm", jsonObject.getInt("distanceSdCm"));
                }

                if (jsonObject.has("timestamp")) {
                    scanResult.timestamp = jsonObject.getLong("timestamp");
                }

                if (jsonObject.has("wifiSsid")) {
                    Class clazz = Class.forName("android.net.wifi.WifiSsid");
                    Method method = ReflectUtils.getMethod(clazz, "createFromHex", String.class);
                    Object object = method.invoke(null, jsonObject.getString("wifiSsid"));
                    ReflectUtils.setFieldObject(scanResult, "wifiSsid", object);
                }

                if (jsonObject.has("channelWidth")) {
                    scanResult.channelWidth = jsonObject.getInt("channelWidth");
                }

                if (jsonObject.has("centerFreq0")) {
                    scanResult.centerFreq0 = jsonObject.getInt("centerFreq0");
                }

                if (jsonObject.has("centerFreq1")) {
                    scanResult.centerFreq1 = jsonObject.getInt("centerFreq1");
                }

                if (jsonObject.has("seen")) {
                    long seen = jsonObject.getLong("seen");
                    ReflectUtils.setFieldLong(scanResult, "seen", seen);
                }

                if (jsonObject.has("flags")) {
                    long flags = jsonObject.getLong("flags");
                    ReflectUtils.setFieldLong(scanResult, "flags", flags);
                }

                if (jsonObject.has("isCarrierAp")) {
                    boolean isCarrierAp = jsonObject.getBoolean("isCarrierAp");
                    ReflectUtils.setFieldBoolean(scanResult, "isCarrierAp", isCarrierAp);
                }

                if (jsonObject.has("carrierApEapType")) {
                    int carrierApEapType = jsonObject.getInt("carrierApEapType");
                    ReflectUtils.setFieldInt(scanResult, "carrierApEapType", carrierApEapType);
                }

                if (jsonObject.has("carrierName")) {
                    String carrierName = jsonObject.getString("carrierName");
                    ReflectUtils.setFieldObject(scanResult, "carrierName", carrierName);
                }

                resultList.add(scanResult);
            }

            //return resultList;
        } catch (Exception ex) {
            Log.d("hongchang", ex.getMessage());
        }

        return resultList;
    }
}
