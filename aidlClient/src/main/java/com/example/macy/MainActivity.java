package com.example.macy;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macy.aidldemo.aidl.TestManager;
import com.example.macy.aidldemo.aidl.TestModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "AIDL";
    private TextView addInTv, addOutTv, addInOutTv, tvBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();

        tvBind = findViewById(R.id.tvBind);

        Button addInBtn = findViewById(R.id.btnAddIn);
        Button addOutBtn = findViewById(R.id.btnAddOut);
        Button addInOutBtn = findViewById(R.id.btnAddInOut);

        addInTv = findViewById(R.id.tvAddIn);
        addOutTv = findViewById(R.id.tvAddOut);
        addInOutTv = findViewById(R.id.tvAddInOut);

        addInBtn.setOnClickListener(this);
        addOutBtn.setOnClickListener(this);
        addInOutBtn.setOnClickListener(this);
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
                tvBind.setText("服务绑定成功：AIDL: client " +
                        "\ntestModel: " + testModel.toString()
                        + "\ntestModels: " + testModels.toString());
                Log.e(TAG, "AIDL: client \ntestModel: " + testModel.toString()
                        + "\ntestModels: " + testModels.toString());
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

    private void addInTestModel() throws RemoteException {
        if (!isBind) return;
        testManager.getModels().clear();
        TestModel testModelClient = new TestModel();
        testModelClient.setName("Client--->Server In模式");
        testModelClient.setAge(111);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Client--->Server In模式")
                .append("\nClient最初创建的Info: ")
                .append(testModelClient);
        try {
            TestModel testModelServer = testManager.addInTestModel(testModelClient);
            stringBuilder
                    .append("\n经过Server对Client传入Info属性的修改后:")
                    .append("\n客户端本地Info: ")
                    .append(testModelClient)
                    .append("\n服务器返回Info: ")
                    .append(testModelServer);
            addInTv.setText(stringBuilder.toString());
            Log.e(TAG, "Client--->Server In模式"
                    + "\n客户端本地: " + testModelClient
                    + "\n服务器返回: " + testModelServer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void addOutTestModel() throws RemoteException {
        if (!isBind) return;
        testManager.getModels().clear();
        TestModel testModelClient = new TestModel();
        testModelClient.setName("Client--->Server Out模式");
        testModelClient.setAge(222);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Client--->Server Out模式")
                .append("\nClient最初创建的Info: ")
                .append(testModelClient);
        try {
            TestModel testModelServer = testManager.addOutTestModel(testModelClient);
            stringBuilder
                    .append("\n经过Server对Client传入Info属性的修改后:")
                    .append("\n客户端本地Info: ")
                    .append(testModelClient)
                    .append("\n服务器返回Info: ")
                    .append(testModelServer);
            addOutTv.setText(stringBuilder.toString());
            Log.e(TAG, "Client--->Server In模式"
                    + "\n客户端本地: " + testModelClient
                    + "\n服务器返回: " + testModelServer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void addInOutTestModel() throws RemoteException {
        if (!isBind) return;
        testManager.getModels().clear();
        TestModel testModelClient = new TestModel();
        testModelClient.setName("Client--->Server InOut模式");
        testModelClient.setAge(333);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Client--->Server InOut模式")
                .append("\nClient最初创建的Info: ")
                .append(testModelClient);
        try {
            TestModel testModelServer = testManager.addInOutTestModel(testModelClient);
            stringBuilder
                    .append("\n经过Server对Client传入Info属性的修改后:")
                    .append("\n客户端本地Info: ")
                    .append(testModelClient)
                    .append("\n服务器返回Info: ")
                    .append(testModelServer);
            addInOutTv.setText(stringBuilder.toString());
            Log.e(TAG, "Client--->Server In模式"
                    + "\n客户端本地: " + testModelClient
                    + "\n服务器返回: " + testModelServer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (!isBind) {
            Toast.makeText(this, "请先绑定服务", Toast.LENGTH_LONG).show();
        }
        try {
            switch (v.getId()) {
                case R.id.btnAddIn:
                    addInTestModel();
                    break;
                case R.id.btnAddOut:
                    addOutTestModel();
                    break;
                case R.id.btnAddInOut:
                    addInOutTestModel();
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
