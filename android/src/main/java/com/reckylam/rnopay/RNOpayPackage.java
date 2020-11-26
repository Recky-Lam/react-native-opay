
package com.reckylam.rnopay;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;
public class RNOpayPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
      return Arrays.<NativeModule>asList(new RNOpayModule(reactContext));
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
      return Collections.emptyList();
    }

//    @Override
//    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
//      return Collections.emptyList();
//    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new RNOpayModule(reactContext));
        return modules;
    }

    @ReactMethod
    public void initTransaction(String reference, String key) {

        OrderInfo info = new OrderInfo();
        info.setAmount(10);
        info.setCurrency("NG");
        info.setMerchantName("merchantName");
        info.setMerchantUserId("123456");
        info.setReference(reference);
        info.setPublicKey(key);
        new PayTask(info).pay(context: MainActivity.this,
            new PayResultCallback() {
                @Override
                public void onResult(ResultStatus status) {

                }
            })
    }
}