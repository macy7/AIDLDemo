package com.example.macy.aidldemo.aidl;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.util.Objects;

/**
 * @ProjectName: AIDLDemo
 * @Package: com.example.macy.aidldemo.aidl
 * @ClassName: TestModel
 * @Description: java类作用描述
 * @Author: macy
 * @CreateDate: 2019/1/9 16:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/1/9 16:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestModel implements Parcelable {

    public TestModel(){

    }

    private String name;
    private int age;

    protected TestModel(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<TestModel> CREATOR = new Creator<TestModel>() {
        @Override
        public TestModel createFromParcel(Parcel in) {
            return new TestModel(in);
        }

        @Override
        public TestModel[] newArray(int size) {
            return new TestModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public void readFromParcel(Parcel dest){
        name = dest.readString();
        age = dest.readInt();
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestModel testModel = (TestModel) o;
        return age == testModel.age &&
                Objects.equals(name, testModel.name);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(name, age);
    }
}
