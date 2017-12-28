package com.skt.thingplug_v2_0_service.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.skt.thingplug_v2_0_service.Const;
import com.skt.thingplug_v2_0_service.R;
import com.skt.thingplug_v2_0_service.data.GoogleDriveHandler;
import com.skt.thingplug_v2_0_service.data.SensorInfo;
import com.skt.thingplug_v2_0_service.data.SensorType;
import com.skt.thingplug_v2_0_service.data.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tp.skt.simple.common.Define;
import tp.skt.simple.element.ArrayElement;
import tp.skt.simple.element.Subscribe;
import tp.skt.simple.net.mqtt.SimpleCallback;

/**
 * activity for sensor list
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class SensorListActivity extends AppCompatActivity {

    private final static String TAG = SensorListActivity.class.getSimpleName();

    public static final String EXTRA_SENSOR_TYPE = "sensorType";

    private final int SETTING_RESULT = 100;
    public static final int SETTING_RESULT_SAVE = 1;
    public static final int SETTING_RESULT_LOGOUT = 2;

    private static List<SensorInfo> sensorInfos = new ArrayList<>();

    private UserInfo userInfo;
    private SimpleWorker simpleWorker;

    private Timer listViewInvalidateTimer;

    private ListItemClickListener listItemClickListener = new ListItemClickListener();
    private ActivateClickListener activateClickListener = new ActivateClickListener();
    private ActuatorRunClickListener actuatorRunClickListener = new ActuatorRunClickListener();

    private ListView listView;
    private View contentView;
    private TextView content;

    private boolean showContent = false;

    /**
     * get sensor info list
     *
     * @return sensor info list
     */
    public static List<SensorInfo> getSensorInfos() {
        return sensorInfos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);

        listView = (ListView) findViewById(R.id.listView);
        listView.setSmoothScrollbarEnabled(true);

        // header view
        contentView = getLayoutInflater().inflate(R.layout.listview_header, null, false);
        listView.addHeaderView(contentView);
        content = (TextView) findViewById(R.id.content);

        userInfo = UserInfo.getInstance(this);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(getString(R.string.actionbar_list) + " (" + userInfo.getDeviceName() + ")");

        simpleWorker = SimpleWorker.getInstance();
        simpleWorker.setStateListener(new SimpleWorkerListener());

        // create sensor list
        createSensorList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sensor_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivityForResult(intent, SETTING_RESULT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SETTING_RESULT) {
            switch (resultCode) {
                case SETTING_RESULT_SAVE:
//                    int newTransferInterval = userInfo.loadTransferInterval();
//                    if (oldTransferInterval != newTransferInterval) {
//                        SensorInfo deviceInfo = new SensorInfo(SensorType.DEVICE);
//                        controlDevice(deviceInfo, newTransferInterval);
//                    }
//                    clearSensorList();
//                    createSensorList();
                    break;
                case SETTING_RESULT_LOGOUT:
                    userInfo.clear(false);
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy!");
        simpleWorker.disconnect();
        // clear sensor list
        clearSensorList();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed!");
        Subscribe sub = new Subscribe("delist", userInfo.getServiceName(), userInfo.getDeviceName(), null, false, new ArrayList<String>(Arrays.asList("*")), new ArrayList<String>(Arrays.asList("*")), 1);
        simpleWorker.subscribe(sub);
        super.onBackPressed();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewInvalidateTimer = new Timer();
        listViewInvalidateTimer.schedule(getListViewTimerTask(), 0, userInfo.getListInterval());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (listViewInvalidateTimer != null) {
            listViewInvalidateTimer.cancel();
        }
    }

    /**
     * create sensor list
     */
    private void createSensorList() {
        // add sensor item
        for (SensorType sensorType : SensorType.values()) {
            if (sensorType != SensorType.NONE && sensorType != SensorType.DEVICE) {
                SensorInfo sensorInfo = new SensorInfo(sensorType);
//                sensorInfo.setActivated(false);
                sensorInfos.add(sensorInfo);
            }
        }

        // add to listview
        listView.setAdapter(new SensorListAdapter(this, sensorInfos));
        Subscribe sub = new Subscribe(Define.ENLIST, userInfo.getServiceName(), userInfo.getDeviceName(), null, false, new ArrayList<String>(Arrays.asList("*")), new ArrayList<String>(Arrays.asList("*")), 1);
        simpleWorker.subscribe(sub);

//        if(userInfo.loadShowContent() == true) {
//            if(listView.getHeaderViewsCount() == 0) {
//                contentView = getLayoutInflater().inflate(R.layout.listview_header, null, false);
//                listView.addHeaderView(contentView);
//                content = (TextView) findViewById(R.id.content);
//            }
//            contentView.setVisibility(View.VISIBLE);
//        } else {
//            listView.removeHeaderView(contentView);
//            contentView.setVisibility(View.GONE);
//        }

//        if(userInfo.isShowContent() == true) {
//            contentView.setVisibility(View.VISIBLE);
//        } else {
//            content.setText("content");
//            contentView.setVisibility(View.GONE);
//        }
    }

    /**
     * clear sensor list
     */
    private void clearSensorList() {
        // clear view
        sensorInfos.clear();
        listView.deferNotifyDataSetChanged();
    }

    /**
     * list adapter
     */
    private class SensorListAdapter extends ArrayAdapter<SensorInfo> {
        private final Context context;
        private final List<SensorInfo> sensorInfos;

        /**
         * constructor
         *
         * @param context     context
         * @param sensorInfos sensor info list
         */
        public SensorListAdapter(Context context, List<SensorInfo> sensorInfos) {
            super(context, R.layout.sensor_list_item, sensorInfos);

            this.context = context;
            this.sensorInfos = sensorInfos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.sensor_list_item, parent, false);
            LinearLayout itemLayout = (LinearLayout) rowView.findViewById(R.id.item_layout);
            ImageView itemImageView = (ImageView) rowView.findViewById(R.id.item_image);
            TextView itemNameView = (TextView) rowView.findViewById(R.id.item_name);
            TextView itemStatus = (TextView) rowView.findViewById(R.id.item_status);
            ToggleButton itemEnable = (ToggleButton) rowView.findViewById(R.id.item_enable);
            ToggleButton itemActivate = (ToggleButton) rowView.findViewById(R.id.item_activate);
            Button itemActuatorRun = (Button) rowView.findViewById(R.id.item_actuator_run);
            itemEnable.setVisibility(View.GONE);

            SensorInfo sensorInfo = sensorInfos.get(position);
            itemImageView.setImageResource(sensorInfo.getType().getImage());
            itemNameView.setText(sensorInfo.getType().getNickname());
//            itemEnable.setChecked(sensorInfo.isEnable());
            if (sensorInfo.getType().getCategory() == SensorType.Category.ACTUATOR) {
                itemStatus.setVisibility(View.GONE);
                itemActivate.setVisibility(View.GONE);
                itemActuatorRun.setEnabled(sensorInfo.getCmdId() == 0); // && !sensorInfo.isSuspend());
            } else {
                itemActuatorRun.setVisibility(View.GONE);
                itemStatus.setText(sensorInfo.toString());
                itemActivate.setChecked(sensorInfo.isActivated());
                itemActivate.setEnabled(sensorInfo.getCmdId() == 0); // !sensorInfo.isSuspend());
            }

            // sensor item click
            itemLayout.setTag(sensorInfo);
            itemLayout.setOnClickListener(listItemClickListener);

            // sensor activation button click
            itemActivate.setTag(sensorInfo);
            itemActivate.setOnClickListener(activateClickListener);

            // actuator run button click
            itemActuatorRun.setTag(sensorInfo);
            itemActuatorRun.setOnClickListener(actuatorRunClickListener);

            return rowView;
        }
    }

    /**
     * list item click listener
     */
    private class ListItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            final SensorInfo sensorInfo = (SensorInfo) view.getTag();

            if (sensorInfo.getType().getCategory() == SensorType.Category.ACTUATOR) {
                switch (sensorInfo.getType()) {
                    case CAMERA:
                        showImageViewDialog();
                        break;
                }
            } else if (sensorInfo.isActivated()) {
                Intent intent = new Intent(SensorListActivity.this, SensorDetailActivity.class);
                intent.putExtra(EXTRA_SENSOR_TYPE, sensorInfo.getType());
                startActivity(intent);
            }
        }
    }

    /**
     * sensor activate button click listener
     */
    private class ActivateClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            SensorInfo sensorInfo = (SensorInfo) view.getTag();
            ToggleButton button = (ToggleButton) view;

            // send sensor activation command to device App.
            controlDevice(sensorInfo, button.isChecked() ? 1 : 0, button);
        }
    }

    /**
     * actuator run button click listener
     */
    private class ActuatorRunClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            SensorInfo sensorInfo = (SensorInfo) view.getTag();
            Button button = (Button) view;

//            if (!sensorInfo.isActivated()) {
//                Toast.makeText(SensorListActivity.this, R.string.actuator_disabled, Toast.LENGTH_SHORT).show();
//                return;
//            }

            switch (sensorInfo.getType()) {
                case BUZZER:
                    showBuzzerControlDialog(sensorInfo, button);
                    break;
                case LED:
                    showLedControlDialog(sensorInfo, button);
                    break;
                case CAMERA:
                    showCameraControlDialog(sensorInfo, button);
                    break;
            }
        }
    }

    /**
     * show buzzer control dialog
     */
    private void showBuzzerControlDialog(final SensorInfo sensorInfo, final Button button) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.actuator_dialog_title);
        dialog.setSingleChoiceItems(R.array.buzzer_items, 0, null)
                .setPositiveButton(R.string.actuator_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ListView lv = ((AlertDialog) dialog).getListView();
                                int selectedItem = 0;
                                switch (lv.getCheckedItemPosition()) {
                                    case 0:
                                        selectedItem = 0;
                                        break;
                                    case 1:
                                        selectedItem = 1;
                                        break;
                                    case 2:
                                        selectedItem = 2;
                                        break;
                                    case 3:
                                        selectedItem = 4;
                                        break;
                                }

                                // send buzzer control command to device App
                                controlDevice(sensorInfo, selectedItem, button);
                            }
                        }).setNegativeButton(R.string.actuator_dialog_cancel, null);
        dialog.show();
    }

    /**
     * show LED control dialog
     */
    private void showLedControlDialog(final SensorInfo sensorInfo, final Button button) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.actuator_dialog_title);
        dialog.setSingleChoiceItems(R.array.led_items, 0, null)
                .setPositiveButton(R.string.actuator_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ListView lv = ((AlertDialog) dialog).getListView();
                                int selectedItem = lv.getCheckedItemPosition();

                                // send LED control command to device App.
                                controlDevice(sensorInfo, selectedItem, button);
                            }
                        }).setNegativeButton(R.string.actuator_dialog_cancel, null);
        dialog.show();
    }

    /**
     * show camera control dialog
     */
    private void showCameraControlDialog(final SensorInfo sensorInfo, final Button button) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.actuator_dialog_title);
        dialog.setSingleChoiceItems(R.array.camera_items, 0, null)
                .setPositiveButton(R.string.actuator_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ListView lv = ((AlertDialog) dialog).getListView();
                                int selectedItem = lv.getCheckedItemPosition();

                                // send camera control command to device App.
                                controlDevice(sensorInfo, selectedItem, button);
                            }
                        }).setNegativeButton(R.string.actuator_dialog_cancel, null);
        dialog.show();
    }

    /**
     * send sensor control command
     *
     * @param sensorInfo sensor info
     * @param command    data for sending
     * @param button     control button
     */
    private void controlDevice(final SensorInfo sensorInfo, int command, final Button button) {
        button.setEnabled(false);
//        sensorInfo.setActivated(!sensorInfo.isActivated());
        ArrayElement element = new ArrayElement();
        element.addNumberElement(sensorInfo.getType().getName()[0], command);
        final int cmdId = simpleWorker.getCmdId();
        sensorInfo.setCmdId(cmdId);
        // setAttribute if actuator
        if(sensorInfo.getType().getCategory() == SensorType.Category.ACTUATOR) {
            simpleWorker.setAttribute(cmdId, element, new SimpleCallback() {
                @Override
                public void onResponse(Object o) {
                    Log.i(TAG, "setAttribute success");
                    final Handler handler = new Handler();
                    final Runnable statusChecker = new Runnable() {
                        @Override
                        public void run() {
                            if (sensorInfo.getCmdId() > 0) {
                                onFailure(cmdId, "fail set_attr");
                            }
                        }
                    };
                    handler.postDelayed(statusChecker, Const.SENSOR_CONTROL_CHECK_DELAY);
                }

                @Override
                public void onFailure(int errorCode, String message) {
                    Log.e(TAG, errorCode + " : " + message);
                    resultControlDevice(false, R.string.control_fail, sensorInfo);
                }
            });
        }
        // jsonRpcReq
        else {
            simpleWorker.setActivate(element, cmdId, new SimpleCallback() {
                @Override
                public void onResponse(Object o) {
                    Log.i(TAG, "setActivate success");
                    final Handler handler = new Handler();
                    final Runnable statusChecker = new Runnable() {
                        @Override
                        public void run() {
                            if (sensorInfo.getCmdId() > 0) {
                                onFailure(cmdId, "fail jsonRpcReq");
                            }
                        }
                    };
                    handler.postDelayed(statusChecker, Const.SENSOR_ACTIVATE_CHECK_DELAY);
                }

                @Override
                public void onFailure(int errorCode, String message) {
                    Log.e(TAG, errorCode + " : " + message);
                    resultControlDevice(false, R.string.control_fail, sensorInfo);
                }
            });
        }
    }

    private void resultControlDevice(boolean isSuccess, int resString, SensorInfo sensorInfo) {
        sensorInfo.setCmdId(0);
        if(isSuccess == true) {
            if (sensorInfo.getType().getCategory() != SensorType.Category.ACTUATOR) {
                sensorInfo.setActivated(!sensorInfo.isActivated());
            }
        }
        String toastMessage = String.format(getResources().getString(resString), sensorInfo.getType().getNickname());
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        if (listView != null) {
            listView.invalidateViews();
        }
    }

    private void showImageViewDialog(String base64EncodedImage) {
        byte[] imageData = Base64.decode(base64EncodedImage, Base64.DEFAULT);

        String fileName = getFilesDir().getPath() + "/temp.jpg";
        try {
            FileOutputStream fo = new FileOutputStream(fileName);
            fo.write(imageData);
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int rotate = 0;
        try {
            ExifInterface exif = new ExifInterface(fileName);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    rotate = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Bitmap image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        // create bitmap from file
        Bitmap image = BitmapFactory.decodeFile(fileName);
        if (image == null) {
            return;
        }
        // rotate by orientation
        Matrix mat = new Matrix();
        mat.postRotate(rotate);
        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), mat, true);

        // create dialog
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_image_view);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.image_view);
        imageView.setImageBitmap(image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * show dialog for Google drive image
     */
    private void showImageViewDialog() {
        int rotate = 0;
        String fileName = getFilesDir().getPath() + "/temp.jpg";
        try {
            ExifInterface exif = new ExifInterface(fileName);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    rotate = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // create bitmap from file
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        if (bitmap == null) {
            return;
        }

        // rotate by orientation
        Matrix mat = new Matrix();
        mat.postRotate(rotate);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);

        // create dialog
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_image_view);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.image_view);
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * Google drive command listener
     */
    private class GoogleDriveCommandListener implements GoogleDriveHandler.CommandListener {

        @Override
        public void onSavedPicture(boolean result, String resourceId) {
            Log.i(TAG, "onSavedPicture : " + result + ", resourceId:" + resourceId);
        }

        @Override
        public void onLoadedPicture(boolean result, byte[] imageData) {
            Log.i(TAG, "onLoadedPicture : " + result);

            // find camera sensor
            SensorInfo cameraSensor = null;
            for (SensorInfo sensorInfo : sensorInfos) {
                if (sensorInfo.getType() == SensorType.CAMERA) {
                    cameraSensor = sensorInfo;
                    break;
                }
            }

            if (result) {
                // save file
                String fileName = getFilesDir().getPath() + "/temp.jpg";
                try {
                    FileOutputStream fo = new FileOutputStream(fileName);
                    fo.write(imageData);
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                cameraSensor.setSuspend(false);

                // show image dialog
                showImageViewDialog();
            } else {
                resultControlDevice(false, R.string.control_fail, cameraSensor);
            }
        }
    }

    /**
     * oneM2M worker state listener
     */
    private class SimpleWorkerListener implements SimpleWorker.StateListener {

        @Override
        public void onConnected(boolean result) {
        }

        @Override
        public void onDisconnected(boolean result) {
        }

//        @Override
//        public void onRegistered(boolean result, String dKey, String nodeLink) {
//        }

        @Override
        public void onUnregistered(boolean result) {
        }

        @Override
        public RESULT onReceiveCommand(String message) {
            return RESULT.SUSPEND;
        }

        @Override
        public void onMessageReceived(String message) {
            if (userInfo.isShowContent()) {
                content.setText(message);
            } else {
                content.setText("");
            }

            try {
                JSONObject json = new JSONObject(message);
                String[] names;
                String name;
                int length;
                if(json.has("photo")) {
                    JSONArray photoArray = json.getJSONArray("photo");
                    String base64Image = photoArray.getString(1);
                    showImageViewDialog(base64Image);
                } else if (json.has("cmd") == true) {
                    int cmdId = json.getInt("cmdId");
                    String result = json.getString("result");
                    for (SensorInfo sensorInfo : sensorInfos) {
                        if(sensorInfo.getCmdId() == cmdId) {
                            if (result.equals("success")) {
                                resultControlDevice(true, R.string.control_success, sensorInfo);
                            } else {
                                resultControlDevice(false, R.string.control_fail, sensorInfo);
                            }
                            break;
                        }
                    }
                } else {
                    for (SensorInfo sensorInfo : sensorInfos) {
                        names = sensorInfo.getType().getName();
                        length = names.length;
                        for (int i = 0; i < length; i++) {
                            name = names[i];
                            if (json.has(name)) {
                                float value = (float) json.getJSONArray(name).getDouble(1);
                                if(value != -9999) {
                                    sensorInfo.setActivated(true);
                                    sensorInfo.setValue(i, value);
                                } else {
                                    sensorInfo.setActivated(false);
                                }

//                                if (sensorInfo.getType().getCategory() == SensorType.Category.ACTUATOR) {
//                                    resultControlDevice(true, R.string.control_success, sensorInfo);
//                                }
                                break;
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * screen refresh TimerTask
     *
     * @return
     */
    private TimerTask getListViewTimerTask() {
        final Handler handler = new Handler();
        TimerTask listViewTimerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.invalidateViews();
                        Log.e(TAG, "invalidate");
                    }
                });
            }
        };
        return listViewTimerTask;
    }
}
