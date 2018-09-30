package com.han.demo;

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

import com.han.demo.developerdemo.Constant;
import com.han.demo.developerdemo.OkgoUtil;
import com.han.demo.developerdemo.RestoreRouterInfo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import demo.dotwin.com.developerdemo.R;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main";

    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkgoUtil.okgo_init_timeout(5000);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, 0);
        }
    }

}
