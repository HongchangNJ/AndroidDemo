package com.han.demo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.han.demo.appbarlayout.CoordinatorActivity;
import com.han.demo.drawview.CustomViewActivity;
import com.han.framework.utils.SizeUtils;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private MainActivity mainActivity;
    private Context mContext;
    private String[] mContentArray = {
            "CoordinatorLayout",
            "仿APP",
            "自定义View"
    };


    public MainAdapter(MainActivity mainActivity, Context context) {
        super();
        this.mainActivity = mainActivity;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView textView = new TextView(mainActivity);
        RecyclerView.LayoutParams params
                = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                SizeUtils.dp2px(mContext, 40));
        textView.setLayoutParams(params);

        textView.setGravity(Gravity.CENTER);
        return new MainAdapter.MainViewHolder(textView);

    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainViewHolder mainViewHolder, int i) {
        mainViewHolder.textView.setText(mContentArray[i]);
        mainViewHolder.itemView.setOnClickListener(view -> processItemClick(i));
    }

    private void processItemClick(int position) {
        switch (position) {
            case 0:
                startActivity(CoordinatorActivity.class);
                break;
            case 1:
                break;
            case 2:
                startActivity(CustomViewActivity.class);
                break;
            default:
                break;
        }
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(mContext, clazz);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mContentArray.length;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
