package com.han.demo;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.han.framework.utils.DeviceUtils;
import com.han.framework.utils.ScreenUtils;
import com.han.framework.utils.SizeUtils;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main";

    private String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private String[] mContentArray = {
            "CoordinatorLayout",
            "APP"
    };

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //OkgoUtil.okgo_init_timeout(5000);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, 0);
        }

        initRV();
    }

    private void initRV() {
        mRecyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        mAdapter = new MainAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }


    public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

        private Context mContext;

        public MainAdapter(Context context) {
            super();
            this.mContext = context;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            TextView textView = new TextView(MainActivity.this);
            RecyclerView.LayoutParams params
                    = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    SizeUtils.dp2px(mContext, 40));
            textView.setLayoutParams(params);

            textView.setGravity(Gravity.CENTER);
            return new MainViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
            mainViewHolder.textView.setText(mContentArray[i]);
        }

        @Override
        public int getItemCount() {
            return mContentArray.length;
        }
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

}
