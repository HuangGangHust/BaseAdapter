package com.huanggang.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "hg" + MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSingleItemSample = (Button) findViewById(R.id.btn_single_item_sample);
        btnSingleItemSample.setOnClickListener(this);
        Button btnMultiItemSample = (Button) findViewById(R.id.btn_multi_item_sample);
        btnMultiItemSample.setOnClickListener(this);
    }

    private <T extends Activity> void startActivity(Class<T> targetClass) {
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_single_item_sample:
                Log.d(TAG, "onClick: 单一Item类型列表万能适配器示例");
                startActivity(SingleItemActivity.class);
                break;
            case R.id.btn_multi_item_sample:
                Log.d(TAG, "onClick: 多Item类型列表万能适配器示例");
                startActivity(MultiItemActivity.class);
                break;
            default:
                break;
        }
    }
}
