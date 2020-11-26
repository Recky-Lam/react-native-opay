
package com.reckylam.rnopay;

import android.content.Context;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.opay.account.core.PayTask;
import com.opay.account.iinterface.PayResultCallback;
import com.opay.account.iinterface.ResultStatus;
import com.opay.account.model.OrderInfo;

public class RNOpayModule extends ReactContextBaseJavaModule {

    private Promise pendingPromise;

    private final ReactApplicationContext reactContext;

    public RNOpayModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void initTransaction(String reference, String amount, String pubKey, String merchantUserId, String merchantUserName) {
        double doubleAmount = Double.parseDouble(amount);

        OrderInfo info = new OrderInfo();
        info.setAmount(doubleAmount);
        info.setCurrency("NGN");
        info.setMerchantName(merchantUserName);
        info.setMerchantUserId(merchantUserId);
        info.setReference(reference);
        info.setPublicKey(pubKey);

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
        if (this.pendingPromise != null) {
            this.pendingPromise.reject(code, message);
            this.pendingPromise = null;
        }
    }

    private void resolvePromise(Object data) {
        if (this.pendingPromise != null) {
            this.pendingPromise.resolve(data);
            this.pendingPromise = null;
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