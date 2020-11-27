
package com.reckylam.rnopay;

import android.content.Context;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableMap;

import com.opay.account.core.PayTask;
import com.opay.account.iinterface.PayResultCallback;
import com.opay.account.iinterface.ResultStatus;
import com.opay.account.model.OrderInfo;

import static android.text.TextUtils.isEmpty;

public class RNOpayModule extends ReactContextBaseJavaModule {

    private Promise promise;

    private final ReactApplicationContext reactContext;

    public RNOpayModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void initTransaction(ReadableMap data, final Promise promise) {

        if (isEmpty(data.getString("reference")) || isEmpty(data.getString("amount")) || isEmpty(data.getString("pubKey")) || isEmpty(data.getString("merchantUserId")) || isEmpty(data.getString("merchantUserName"))) {
            rejectPromise("E_INVALID_Info", "Invalid card info");
            return;
        }

        this.promise = promise;

        double doubleAmount = Double.parseDouble(data.getString("amount"));

        OrderInfo info = new OrderInfo();
        info.setAmount(doubleAmount);
        info.setCurrency("NGN");
        info.setMerchantName(data.getString("merchantUserName"));
        info.setMerchantUserId(data.getString("merchantUserId"));
        info.setReference(data.getString("reference"));
        info.setPublicKey(data.getString("pubKey"));

        try {
            createTransaction(info);
        } catch (Exception error) {
            rejectPromise("E_CHARGE_ERROR", error.getMessage());
        }
    }

    private void createTransaction(final OrderInfo info) {
        new PayTask(info).pay(
            reactContext,
            new PayResultCallback() {
                @Override
                public void onResult(ResultStatus status) {
                    WritableMap map = Arguments.createMap();
                    map.putString("code", String.valueOf(status.getCode()));
                    map.putString("message", status.getMsg());
                    map.putString("reference", info.getReference());
                    map.putString("amount", String.valueOf(info.getAmount()));
                    resolvePromise(map);
                }
            }
        );
    }

    private void rejectPromise(String code, String message) {
        if (this.promise != null) {
            this.promise.reject(code, message);
            this.promise = null;
        }
    }

    private void resolvePromise(Object data) {
        if (this.promise != null) {
            this.promise.resolve(data);
            this.promise = null;
        }
    }

    @ReactMethod
    public String version() {
        String version = "Opay Version 1.0.4";
        return version;
    }

    @Override
    public String getName() {
        return "RNOpay";
    }
}