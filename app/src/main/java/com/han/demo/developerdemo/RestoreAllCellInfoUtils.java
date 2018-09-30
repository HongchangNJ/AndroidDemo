package com.han.demo.developerdemo;

import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class RestoreAllCellInfoUtils {

    public static final String TEST = "";
    public static List<CellInfo> restoreAllCellInfo(String cellStr) {
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
}
