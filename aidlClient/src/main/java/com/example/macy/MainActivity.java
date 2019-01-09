package com.example.macy;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.macy.aidldemo.aidl.TestManager;
import com.example.macy.aidldemo.aidl.TestModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
    }

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
                Log.e("tag", "AIDL: client \ntestModel: " + testModel.toString()
                        + "\ntestModels: " + testModels.toString());
                addInTestModel();
                addOutTestModel();
                addInoutTestModel();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.example.macy.aidldemo.ServerService");
        intent.setPackage("com.example.macy.aidldemo");
        this.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void addInTestModel() {
        TestModel testModel = new TestModel();
        testModel.setName("client->server In模式");
        testModel.setAge(111);
        if (isBind) {
            try {
                TestModel testModel1 = testManager.addInTestModel(testModel);
                Log.e("tag","AIDL: client In: \n服务器返回: " + testModel1
                        + "\n客户端本地: " + testModel);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void addOutTestModel() {
        TestModel testModel = new TestModel();
        testModel.setName("client->server Out模式");
        testModel.setAge(222);
        if (isBind) {
            try {
                TestModel testModel1 = testManager.addOutTestModel(testModel);
                Log.e("tag","AIDL: client Out: \n服务器返回: " + testModel1
                        + "\n客户端本地: " + testModel);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void addInoutTestModel() {
        TestModel testModel = new TestModel();
        testModel.setName("client->server Inout模式");
        testModel.setAge(333);
        if (isBind) {
            try {
                TestModel testModel1 = testManager.addInOutTestModel(testModel);
                Log.e("tag","AIDL: client InOut: \n服务器返回: " + testModel1
                        + "\n客户端本地: " + testModel);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
