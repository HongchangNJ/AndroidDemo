package com.han.demo.developerdemo;

import android.net.wifi.ScanResult;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.NeighboringCellInfo;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RestoreRouterInfo {

    /**
     * 恢复NeighboringL
     * @param filePath
     * @return
     */
    public static List<NeighboringCellInfo> restoreNeighbCell(String filePath) {
        String jsonStr = getJsonStr(filePath, "neighbList");

        List<NeighboringCellInfo> neighborList = new ArrayList<>();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonStr);
            int length = jsonArray.length();
            for (int i = 0; i < length; i ++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                NeighboringCellInfo cellInfo = new NeighboringCellInfo();

                if (jsonObject.has("cid")) {
                    //cellInfo.setCid(jsonObject.getInt("cid"));
                    ReflectUtils.setFieldInt(cellInfo, "mCid", jsonObject.getInt("cid"));
                }

                if (jsonObject.has("rssi")) {
                    //cellInfo.setRssi(jsonObject.getInt("rssi"));
                    ReflectUtils.setFieldInt(cellInfo, "mRssi", jsonObject.getInt("rssi"));
                }

                if (jsonObject.has("lac")) {
                    ReflectUtils.setFieldInt(cellInfo, "mLac", jsonObject.getInt("lac"));
                }

                if (jsonObject.has("psc")) {
                    ReflectUtils.setFieldInt(cellInfo, "mPsc", jsonObject.getInt("psc"));
                }

                if (jsonObject.has("networkType")) {
                    ReflectUtils.setFieldInt(cellInfo, "mNetworkType", jsonObject.getInt("networkType"));
                }
                neighborList.add(cellInfo);
            }

        } catch (Exception ex) {

        }

        return neighborList;
    }


    /**
     * 恢复 CellLocation
     * @param filePath
     * @return
     */
    public static CellLocation restoreLocalCell(String filePath) {

        String json = getJsonStr(filePath, "cellLocation");

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            String type = jsonObject.getString("mNetworkType");
            if ("gsm".equals(type)) {
                return getGSMCellLocation(jsonObject);
            } else if ("cdma".equals(type)) {
                return getCdmaCellLocation(jsonObject);
            }

        } catch (Exception ex) {

        }
        return  null;
    }

    /**
     * 恢复AllCellInfo
     * @param filePath
     * @return
     */
    public static List<CellInfo> restoreAllCellInfo(String filePath) {
        String cellStr = getJsonStr(filePath, "allCell");

        if (TextUtils.isEmpty(cellStr)) {
            return null;
        }
        JSONArray jsonArray = null;
        List<CellInfo> cellInfoList = new ArrayList<>();

        try {
            jsonArray = new JSONArray(cellStr);
            int length = jsonArray.length();
            for (int i = 0; i < length; i ++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String type = object.getString("mNetworkType");
                if ("gsm".equals(type)) {
                    cellInfoList.add(getGsmCellInfo(object));
                } else if ("cdma".equals(type)) {
                    cellInfoList.add(getCdmaCellInfo(object));
                } else if ("lte".equals(type)) {
                    cellInfoList.add(getLteCellInfo(object));
                } else if("wcdma".equals(type)) {
                    cellInfoList.add(getWcdmaCellInfo(object));
                }
            }
        } catch (Exception ex) {
        }

        return cellInfoList;
    }

    /**
     * 恢复ScanResult
     * @param filePath
     * @return
     */
    public static List<ScanResult> restoreScanResult(String filePath) {

        String resultListStr = getJsonStr(filePath, "scanResult");

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


    private static CellInfoGsm getGsmCellInfo(JSONObject object) {
        try {
            Constructor constructor = CellIdentityGsm.class.getConstructor(int.class, int.class, int.class, int.class);
            constructor.setAccessible(true);

            int mcc = -1;
            int mnc = -1;
            int lac = -1;
            int cid = -1;

            if (object.has("mMcc")) {
                mcc = object.getInt("mMcc");
            }

            if (object.has("mMnc")) {
                mnc = object.getInt("mMnc");
            }

            if (object.has("mLac")) {
                lac = object.getInt("mLac");
            }

            if (object.has("mCid")) {
                cid = object.getInt("mCid");
            }

            CellIdentityGsm gsmIdentity = (CellIdentityGsm) constructor.newInstance(mcc, mnc, lac, cid);

            CellInfoGsm cellInfoGsm = CellInfoGsm.class.newInstance();
            ReflectUtils.setFieldObject(cellInfoGsm, "mCellIdentityGsm", gsmIdentity);

            if (object.has("mRegistered")) {
                ReflectUtils.setFieldBoolean(cellInfoGsm, "mRegistered", object.getBoolean("mRegistered"));
            }

            CellSignalStrengthGsm gsmLength = CellSignalStrengthGsm.class.newInstance();
            if (object.has("mSignalStrength")) {
                ReflectUtils.setFieldInt(gsmLength, "mSignalStrength", object.getInt("mSignalStrength"));
            }

            if (object.has("mBitErrorRate")) {
                ReflectUtils.setFieldInt(gsmLength, "mBitErrorRate",  object.getInt("mBitErrorRate"));
            }

            if (object.has("mTimingAdvance")) {
                ReflectUtils.setFieldInt(gsmLength, "mTimingAdvance",  object.getInt("mTimingAdvance"));
            }
            ReflectUtils.setFieldObject(cellInfoGsm, "mCellSignalStrengthGsm", gsmLength);

            return cellInfoGsm;
        } catch (Exception ex) {
            Log.d("Resotre", ex.getMessage());
        }
        return null;
    }

    private static CellInfo getCdmaCellInfo(JSONObject object) {
        try {
            Constructor constructor = CellIdentityCdma.class.getConstructor(int.class, int.class,
                    int.class, int.class, int.class);
            constructor.setAccessible(true);

            int mNetworkId = Integer.MAX_VALUE;
            int  mSystemId = Integer.MAX_VALUE;
            int mBasestationId = Integer.MAX_VALUE;
            int mLongitude = Integer.MAX_VALUE;
            int mLatitude = Integer.MAX_VALUE;

            if (object.has("mNetworkId")) {
                mNetworkId = object.getInt("mNetworkId");
            }

            if (object.has("mSystemId")) {
                mSystemId = object.getInt("mSystemId");
            }

            if (object.has("mBasestationId")) {
                mBasestationId = object.getInt("mBasestationId");
            }

            if (object.has("mLongitude")) {
                mLongitude = object.getInt("mLongitude");
            }

            if (object.has("mLatitude")) {
                mLatitude = object.getInt("mLatitude");
            }

            CellIdentityCdma cdmaIdentity = (CellIdentityCdma) constructor.newInstance(mNetworkId, mSystemId, mBasestationId, mLongitude, mLatitude);

            CellInfoCdma cellInfoCdma = CellInfoCdma.class.newInstance();
            ReflectUtils.setFieldObject(cellInfoCdma, "mCellIdentityCdma", cdmaIdentity);

            if (object.has("mRegistered")) {
                ReflectUtils.setFieldBoolean(cellInfoCdma, "mRegistered", object.getBoolean("mRegistered"));
            }

            CellSignalStrengthCdma cdmaLength = CellSignalStrengthCdma.class.newInstance();
            if (object.has("mCdmaDbm")) {
                ReflectUtils.setFieldInt(cdmaLength, "mCdmaDbm", object.getInt("mCdmaDbm"));
            }

            if (object.has("mCdmaEcio")) {
                ReflectUtils.setFieldInt(cdmaLength, "mCdmaEcio",  object.getInt("mCdmaEcio"));
            }

            if (object.has("mEvdoDbm")) {
                ReflectUtils.setFieldInt(cdmaLength, "mEvdoDbm",  object.getInt("mEvdoDbm"));
            }

            if (object.has("mEvdoEcio")) {
                ReflectUtils.setFieldInt(cdmaLength, "mEvdoEcio",  object.getInt("mEvdoEcio"));
            }

            if (object.has("mEvdoSnr")) {
                ReflectUtils.setFieldInt(cdmaLength, "mEvdoSnr",  object.getInt("mEvdoSnr"));
            }
            ReflectUtils.setFieldObject(cellInfoCdma, "mCellSignalStrengthCdma", cdmaLength);
            return cellInfoCdma;

        }  catch (Exception ex) {
            Log.d("Resotre", ex.getMessage());
        }
        return null;
    }

    private static CellInfo getLteCellInfo(JSONObject object) {
        try {
            Constructor constructor = CellIdentityLte.class.getConstructor(int.class,
                    int.class, int.class, int.class, int.class);
            constructor.setAccessible(true);

            int mMcc = Integer.MAX_VALUE;
            int mMnc = Integer.MAX_VALUE;
            int mCi = Integer.MAX_VALUE;
            int mPci = Integer.MAX_VALUE;
            int mTac = Integer.MAX_VALUE;

            if (object.has("mMcc")) {
                mMcc = object.getInt("mMcc");
            }

            if (object.has("mMnc")) {
                mMnc = object.getInt("mMnc");
            }

            if (object.has("mCi")) {
                mCi = object.getInt("mCi");
            }

            if (object.has("mPci")) {
                mPci = object.getInt("mPci");
            }

            if (object.has("mTac")) {
                mTac = object.getInt("mTac");
            }

            CellIdentityLte lteIdentity = (CellIdentityLte) constructor.newInstance(mMcc, mMnc, mCi, mPci, mTac);
            CellInfoLte cellInfoLte = CellInfoLte.class.newInstance();
            ReflectUtils.setFieldObject(cellInfoLte, "mCellIdentityLte", lteIdentity);

            if (object.has("mRegistered")) {
                ReflectUtils.setFieldBoolean(lteIdentity, "mRegistered", object.getBoolean("mRegistered"));
            }


            CellSignalStrengthLte lteLength = CellSignalStrengthLte.class.newInstance();
            if (object.has("mSignalStrength")) {
                ReflectUtils.setFieldInt(lteLength, "mSignalStrength", object.getInt("mSignalStrength"));
            }

            if (object.has("mRsrp")) {
                ReflectUtils.setFieldInt(lteLength, "mRsrp",  object.getInt("mRsrp"));
            }

            if (object.has("mRsrq")) {
                ReflectUtils.setFieldInt(lteLength, "mRsrq",  object.getInt("mRsrq"));
            }

            if (object.has("mRssnr")) {
                ReflectUtils.setFieldInt(lteLength, "mRssnr",  object.getInt("mRssnr"));
            }

            if (object.has("mCqi")) {
                ReflectUtils.setFieldInt(lteLength, "mCqi",  object.getInt("mCqi"));
            }

            if (object.has("mTimingAdvance")) {
                ReflectUtils.setFieldInt(lteLength, "mTimingAdvance",  object.getInt("mTimingAdvance"));
            }
            ReflectUtils.setFieldObject(cellInfoLte, "mCellSignalStrengthLte", lteLength);
            return cellInfoLte;

        }  catch (Exception ex) {
            Log.d("Resotre", ex.getMessage());
        }
        return null;
    }

    private static CellInfo getWcdmaCellInfo(JSONObject object) {
        try {
            Constructor constructor = CellIdentityWcdma.class.getConstructor(int.class,
                    int.class, int.class, int.class, int.class);
            constructor.setAccessible(true);

            int mMcc = Integer.MAX_VALUE;
            int mMnc = Integer.MAX_VALUE;
            int mLac = Integer.MAX_VALUE;
            int mCid = Integer.MAX_VALUE;
            int mPsc = Integer.MAX_VALUE;

            if (object.has("mMcc")) {
                mMcc = object.getInt("mMcc");
            }

            if (object.has("mMnc")) {
                mMnc = object.getInt("mMnc");
            }

            if (object.has("mLac")) {
                mLac = object.getInt("mLac");
            }

            if (object.has("mCid")) {
                mCid = object.getInt("mCid");
            }

            if (object.has("mPsc")) {
                mPsc = object.getInt("mPsc");
            }

            CellIdentityWcdma wcdmaIdentity = (CellIdentityWcdma) constructor.newInstance(mMcc, mMnc, mLac, mCid, mPsc);
            CellInfoWcdma cellInfoWcdma = CellInfoWcdma.class.newInstance();
            ReflectUtils.setFieldObject(cellInfoWcdma, "mCellIdentityWcdma", wcdmaIdentity);

            if (object.has("mRegistered")) {
                ReflectUtils.setFieldBoolean(wcdmaIdentity, "mRegistered", object.getBoolean("mRegistered"));
            }

            CellSignalStrengthWcdma wcdmaLength = CellSignalStrengthWcdma.class.newInstance();
            if (object.has("mSignalStrength")) {
                ReflectUtils.setFieldInt(wcdmaLength, "mSignalStrength", object.getInt("mSignalStrength"));
            }

            if (object.has("mBitErrorRate")) {
                ReflectUtils.setFieldInt(wcdmaLength, "mBitErrorRate",  object.getInt("mBitErrorRate"));
            }

            ReflectUtils.setFieldObject(cellInfoWcdma, "mCellSignalStrengthWcdma", wcdmaLength);
            return cellInfoWcdma;

        }  catch (Exception ex) {
            Log.d("Resotre", ex.getMessage());
        }
        return null;
    }



    private static CdmaCellLocation getCdmaCellLocation(JSONObject object) {
        CdmaCellLocation location = new CdmaCellLocation();
        int mBaseStationId = -1;
        int mBaseStationLatitude = -1;
        int mBaseStationLongitude = -1;
        int mSystemId = -1;
        int mNetworkId = -1;

        try {
            if (object.has("mBaseStationId")) {
                mBaseStationId = object.getInt("mBaseStationId");
            }

            if (object.has("mBaseStationLatitude")) {
                mBaseStationLatitude = object.getInt("mBaseStationLatitude");
            }

            if (object.has("mBaseStationLongitude")) {
                mBaseStationLongitude = object.getInt("mBaseStationLongitude");
            }

            if (object.has("mSystemId")) {
                mSystemId = object.getInt("mSystemId");
            }

            if (object.has("mNetworkId")) {
                mNetworkId = object.getInt("mNetworkId");
            }
        } catch (Exception ex) {

        }

        location.setCellLocationData(mBaseStationId, mBaseStationLatitude, mBaseStationLongitude, mSystemId, mNetworkId);
        return location;
    }


    private static GsmCellLocation getGSMCellLocation(JSONObject object) {
        GsmCellLocation location = new GsmCellLocation();

        try {
            int mLac = -1;
            int mCid = -1;
            int mPsc = -1;

            if (object.has("mLac")) {
                mLac = object.getInt("mLac");
            }

            if (object.has("mCid")) {
                mCid = object.getInt("mCid");
            }

            if (object.has("mPsc")) {
                mPsc = object.getInt("mPsc");
                ReflectUtils.setFieldInt(location, "mPsc", mPsc);
            }

            location.setLacAndCid(mLac, mCid);

        } catch (Exception ex) {

        }

        return location;
    }

    private static String getJsonStr(String filePath, String key) {
        try {
            File inputFile = new File(filePath);
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            String routerStr = builder.toString();

            JSONObject routerObject = new JSONObject(routerStr);
            if (routerObject.has(key)) {
                return routerObject.getString(key);
            }

        } catch (Exception ex) {

        }

        return null;
    }
}
