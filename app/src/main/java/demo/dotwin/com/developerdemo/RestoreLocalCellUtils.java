package demo.dotwin.com.developerdemo;

import android.telephony.CellLocation;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import org.json.JSONObject;

public class RestoreLocalCellUtils {

    public static CellLocation restoreLocalCell(String json) {

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
}
