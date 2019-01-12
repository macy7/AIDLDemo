package com.example.macy.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.macy.aidldemo.aidl.TestManager;
import com.example.macy.aidldemo.aidl.TestModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private TestManager testManager;
    private boolean isBind;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind = true;
            testManager = TestManager.Stub.asInterface(service);
            try {
                TestModel testModel = testManager.getTestModel();
                List<TestModel> testModels = testManager.getModels();
                tvMain.setText("服务绑定成功:\n" + "AIDL: server->client MainActivity \ntestModel: " + testModel.toString()
                        + "\ntestModels: " + testModels.toString());
                Log.e(TAG, "AIDL: server->client MainActivity \ntestModel: " + testModel.toString()
                        + "\ntestModels: " + testModels.toString());
                addTestModel();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };

    TextView tvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
        tvMain = findViewById(R.id.tvMain);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, ServerService.class);
        this.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void addTestModel() {
        TestModel testModel = new TestModel();
        testModel.setName("client->server");
        testModel.setAge(28);
        if (isBind) {
            try {
                testManager.addInTestModel(testModel);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(serviceConnection);
            isBind = false;
        }
    }
}
