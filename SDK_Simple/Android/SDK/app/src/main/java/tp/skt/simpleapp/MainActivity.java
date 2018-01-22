package tp.skt.simpleapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import tp.skt.simple.api.Simple;
import tp.skt.simple.element.ArrayElement;
import tp.skt.simple.net.mqtt.SimpleCallback;
import tp.skt.simple.net.mqtt.SimpleConfiguration;
import tp.skt.simple.net.mqtt.SimpleListener;

public class MainActivity extends AppCompatActivity {

    private Simple simple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SimpleConfiguration configuration = new SimpleConfiguration(Configuration.MQTT_SECURE_HOST, Configuration.SIMPLE_DEVICE_TOKEN, Configuration.SIMPLE_DEVICE_TOKEN, null);
        configuration.setEnableSecure(false);

        simple = new Simple(this, Configuration.SIMPLE_SERVICE_NAME, Configuration.SIMPLE_DEVICE_NAME, null,
                configuration,
                simpleListener, true);
        simple.tpSimpleConnect();
    }

    private void attribute() {
        ArrayElement element = new ArrayElement();
        element.addStringElement("sysHardwareVersion", "1.0");
        element.addStringElement("sysSerialNumber", "710DJC5I10000290");
        element.addNumberElement("sysErrorCode", 0);
        element.addNumberElement("sysLocationLongitude", 129.1338524);
        element.addNumberElement("sysLocationLatitude", 35.1689766);
        simple.tpSimpleAttribute(element, callback);
    }

    private void telemetry() {
        ArrayElement element = new ArrayElement();
        element.addNumberElement("temp1", 26.26);
        element.addNumberElement("humi1", 48);
        element.addNumberElement("light1", 267);
        simple.tpSimpleTelemetry(element, false, callback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(simple != null) {
            simple.tpSimpleDestroy();
        }
    }

    SimpleCallback callback = new SimpleCallback() {
        @Override
        public void onResponse(Object response) {

        }

        @Override
        public void onFailure(int errorCode, String message) {

        }
    };

    SimpleListener simpleListener = new SimpleListener() {

        @Override
        public void onPush(String message) {
            Log.e("SimpleApp", "onPush");
        }

        @Override
        public void onConnected() {
            Log.e("SimpleApp", "onConnected");
        }

        @Override
        public void onDisconnected() {

        }

        @Override
        public void onSubscribed() {
            Log.e("SimpleApp", "onSubscribed");
            attribute();
            telemetry();
        }

        @Override
        public void onSubscribeFailure() {
            Log.e("SimpleApp", "onSubscribFailure");
        }

        @Override
        public void onConnectFailure() {
            Log.e("SimpleApp", "onConnectFailure");
        }

        @Override
        public void onDisconnectFailure() {

        }

        @Override
        public void onConnectionLost() {

        }

        @Override
        public void onDelivered() {

        }
    };
}
