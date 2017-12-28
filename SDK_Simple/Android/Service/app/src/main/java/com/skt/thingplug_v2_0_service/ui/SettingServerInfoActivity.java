package com.skt.thingplug_v2_0_service.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.skt.thingplug_v2_0_service.R;
import com.skt.thingplug_v2_0_service.data.UserInfo;

/**
 * activity for server information setting
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class SettingServerInfoActivity extends AppCompatActivity {
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_server_info);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.actionbar_setting_server_info);

        userInfo = UserInfo.getInstance(this);

        final EditText hostPortalURL = (EditText) findViewById(R.id.host_portal);
        final EditText hostServerURL = (EditText) findViewById(R.id.host_server);
        final CheckBox chkUseTLS = (CheckBox) findViewById(R.id.check_use_tls);

        hostPortalURL.setText(userInfo.getPortal());
        hostServerURL.setText(userInfo.getServer());
        chkUseTLS.setChecked(userInfo.getUseTLS());

        Button btnLoadDefault = (Button) findViewById(R.id.button_load_default);
        btnLoadDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfo.clear(true);
                hostPortalURL.setText(userInfo.getPortal());
                hostServerURL.setText(userInfo.getServer());
                chkUseTLS.setChecked(userInfo.getUseTLS());
            }
        });
        Button btnSave = (Button) findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfo.setPortal(hostPortalURL.getText().toString().trim());
                userInfo.setServer(hostServerURL.getText().toString().trim());
                userInfo.setUseTLS(chkUseTLS.isChecked());
                finish();
            }
        });
        Button btnCancel = (Button) findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
