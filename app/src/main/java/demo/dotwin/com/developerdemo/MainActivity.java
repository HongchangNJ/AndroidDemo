package demo.dotwin.com.developerdemo;

import android.Manifest;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main";
    private String CONFIG_PATH = Environment.getExternalStorageDirectory().toString() + "/config.txt";
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        OkgoUtil.okgo_init_timeout(5000);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, 0);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(() -> {
                    //setRouterInfo();
                    setConfig("BSSID", "TEST-BSSID");
                }).start();
            }
        });
    }

    private void setConfig(String key,String value) {
        File configFile = new File(CONFIG_PATH);
        if (!configFile.exists()) {
            return;
        }

        Properties configProp = new Properties();
        InputStream in;
        try {
            // 加载config文件
            in = new FileInputStream(configFile);
            configProp.load(in);
            in.close();

            // setProp
            configProp.setProperty(key, value);

            // 保存config文件
            FileOutputStream out = new FileOutputStream(configFile);
            configProp.store(out, "");
            out.close();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private void setRouterInfo() {
        try {
            String filePath = getExternalCacheDir() + "/routerinfo.json";
            String url = "http://10.0.0.11:9000/router_stat/cgi/system/getRouterInfo?routerId=207693297e7c";
            String result = OkgoUtil.OkGo_Get(url);
            File outputFile = new File(filePath);
            if (outputFile.exists()) {
                outputFile.delete();
            }

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(Constant.TEST_ROUTER_INFO.getBytes());
            outputStream.flush();

            File inputFile = new File(filePath);
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }


            String resultStr = builder.toString();
            JSONObject object = new JSONObject(resultStr);
            String allCellStr = object.getString("allCell");
            String cellLocationStr = object.getString("cellLocation");
            String neighbListStr = object.getString("neighbList");
            String scanResultStr = object.getString("scanResult");

            List<CellInfo> cellList = RestoreRouterInfo.restoreAllCellInfo(filePath);
            CellLocation cellLocation = RestoreRouterInfo.restoreLocalCell(filePath);
            List<NeighboringCellInfo> neighboringCellInfoList = RestoreRouterInfo.restoreNeighbCell(filePath);
            List<ScanResult> scanResultList = RestoreRouterInfo.restoreScanResult(filePath);
            Log.d("", "");
        } catch (
                Exception ex)

        {
            Log.d("hongchang", ex.getMessage());
        }
    }

}
