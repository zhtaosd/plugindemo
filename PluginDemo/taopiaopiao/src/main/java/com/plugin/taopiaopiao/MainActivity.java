package com.plugin.taopiaopiao;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(that,"-------->",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(that,SecondActivity.class));
                startService(new Intent(that, OneService.class));

                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.dongnao.alvin.taopiaopiao.MainActivity");
                registerReceiver(new MyReceiver(), intentFilter);
            }
        });
        findViewById(R.id.sendBroad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.dongnao.alvin.taopiaopiao.MainActivity");
                sendBroadcast(intent);
            }
        });
    }
}
