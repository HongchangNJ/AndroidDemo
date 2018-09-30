package demo.dotwin.com.developerdemo;

import android.telephony.NeighboringCellInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestoreNeighCellUtils {
    public static List<NeighboringCellInfo> restoreNeighbCell(String jsonStr) {
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
}
