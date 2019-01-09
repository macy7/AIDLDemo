// TestManager.aidl
package com.example.macy.aidldemo.aidl;

// Declare any non-default types here with import statements
import com.example.macy.aidldemo.aidl.TestModel;

interface TestManager {
    TestModel getTestModel();
    List<TestModel> getModels();
    TestModel addInTestModel(in TestModel testModel);
    TestModel addOutTestModel(out TestModel testModel);
    TestModel addInOutTestModel(inout TestModel testModel);
}
