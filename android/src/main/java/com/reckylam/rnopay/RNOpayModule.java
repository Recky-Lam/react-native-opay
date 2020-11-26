
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

    private Promise pendingPromise;

    private final ReactApplicationContext reactContext;

    public RNOpayModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void initTransaction(ReadableMap cardData) {

        if (isEmpty(cardData.getString("reference")) || isEmpty(cardData.getString("amount")) || isEmpty(cardData.getString("pubKey")) || isEmpty(cardData.getString("merchantUserId")) || isEmpty(cardData.getString("merchantUserName"))) {
            rejectPromise("E_INVALID_Info", "Invalid card info");
            return;
        }

        double doubleAmount = Double.parseDouble(cardData.getString("amount"));

        OrderInfo info = new OrderInfo();
        info.setAmount(doubleAmount);
        info.setCurrency("NGN");
        info.setMerchantName(cardData.getString("merchantUserName"));
        info.setMerchantUserId(cardData.getString("merchantUserId"));
        info.setReference(cardData.getString("reference"));
        info.setPublicKey(cardData.getString("pubKey"));

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