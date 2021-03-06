package com.example.macy.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.macy.aidldemo.aidl.TestManager;
import com.example.macy.aidldemo.aidl.TestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: AIDLDemo
 * @Package: com.example.macy.aidldemo
 * @ClassName: ServerService
 * @Description: java类作用描述
 * @Author: macy
 * @CreateDate: 2019/1/9 16:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/1/9 16:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServerService extends Service {
    private final String TAG = "ServerService";
    private List<TestModel> testModels = new ArrayList<>();
    private TestManager.Stub testManager = new TestManager.Stub() {
        @Override
        public TestModel getTestModel() throws RemoteException {
            TestModel testModel = testModels.get(0);
            Log.e(TAG, "AIDL: \n**********************************************************\n");
            Log.e(TAG, "AIDL: server getTestModel()\n " + testModel.toString());
            return testModel;
        }

        @Override
        public List<TestModel> getModels() throws RemoteException {
            Log.e(TAG, "AIDL: server getModels()\n " + testModels.toString());
            return testModels;
        }

        @Override
        public TestModel addInTestModel(TestModel testModel) throws RemoteException {
            Log.e(TAG, "AIDL: server in:\n " + testModel.toString());
            if (testModel == null) {
                Log.e(TAG, "AIDL: server in:\n " + "addIn is null");
                testModel = new TestModel();
            }
//            testModel.setName("Server--->Client");
            testModel.setAge(-111);
            if (!testModels.contains(testModel)) {
                testModels.add(testModel);
            }
            Log.e(TAG, "AIDL: server in:\n " + testModels.toString());
            return testModel;
        }

        @Override
        public TestModel addOutTestModel(TestModel testModel) throws RemoteException {
            Log.e(TAG, "AIDL: server out:\n " + testModel.toString());
            if (testModel == null) {
                Log.e(TAG, "AIDL: server out:\n " + "addOut is null");
                testModel = new TestModel();
            }
//            testModel.setName("Server--->Client");
            testModel.setAge(-222);
            if (!testModels.contains(testModel)) {
                testModels.add(testModel);
            }
            Log.e(TAG, "AIDL: server out:\n " + testModels.toString());
            return testModel;
        }

        @Override
        public TestModel addInOutTestModel(TestModel testModel) throws RemoteException {
            Log.e(TAG, "AIDL: server inout:\n " + testModel.toString());
            if (testModel == null) {
                Log.e(TAG, "AIDL: server inout:\n " + "addInOut is null");
                testModel = new TestModel();
            }
//            testModel.setName("Server--->Client");
            testModel.setAge(-333);
            if (!testModels.contains(testModel)) {
                testModels.add(testModel);
            }
            Log.e(TAG, "AIDL: server inout:\n " + testModels.toString());
            return testModel;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        TestModel testModel = new TestModel();
        testModel.setName("ServerService");
        testModel.setAge(777);
        testModels.add(testModel);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return testManager.asBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
