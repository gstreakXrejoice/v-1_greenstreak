package co.gstreak.app;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;

import com.vungle.warren.Vungle;
import com.vungle.warren.AdConfig; // Custom ad configurations
import com.vungle.warren.InitCallback; // Initialization callback
import com.vungle.warren.LoadAdCallback; // Load ad callback
import com.vungle.warren.PlayAdCallback; // Play ad callback
import com.vungle.warren.VungleSettings; // Minimum disk space
import com.vungle.warren.error.VungleException; // onError message

public class RNVungle extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNVungle(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    VungleSettings vungleSettings = new VungleSettings.Builder().setAndroidIdOptOut(true).build();

    private void sendEvent(ReactApplicationContext context, String eventName, final WritableMap params) {
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    private void initVungle(final String appId) {
        final WritableMap params1 = Arguments.createMap();
        final WritableMap params2 = Arguments.createMap();
        Vungle.init(appId, reactContext.getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {
                params1.putString("name", "sdkInitialize");
                sendEvent(reactContext, "EventSDKInitialize", params1);
            }

            @Override
            public void onError(VungleException e) {
                Log.d("VUNGLE_ERROR:", e.getMessage());
                params2.putString("name", "sdkFailed");
                sendEvent(reactContext, "EventSDKFailed", params2);
            }

            @Override
            public void onAutoCacheAdAvailable(String placementId) {
                // Callback to notify when an ad becomes available for the cache optimized
                // placement
                // NOTE: This callback works only for the cache optimized placement. Otherwise,
                // please use
                // LoadAdCallback with loadAd API for loading placements.
            }
        }, vungleSettings);
    }

    private void innerLoadAds(final String placementId) {
        final WritableMap params1 = Arguments.createMap();
        final WritableMap params2 = Arguments.createMap();
        // Load Ad Implementation
        if (Vungle.isInitialized()) {
            Vungle.loadAd(placementId, new LoadAdCallback() {
                @Override
                public void onAdLoad(String placementReferenceId) {
                    params1.putString("name", "adPlayabilityUpdate");
                    sendEvent(reactContext, "EventAdPlayabilityUpdate", params1);
                }

                @Override
                public void onError(String placementReferenceId, VungleException e) {
                    Log.d("VUNGLE_ERROR:", e.getLocalizedMessage());
                    params2.putString("name", "sdkFailed");
                    sendEvent(reactContext, "EventSDKFailed", params2);
                }
            });
        }
    }

    private void innerShowAds(final String placementId, final String userId) {
        AdConfig adConfig = new AdConfig();
        adConfig.setAdOrientation(AdConfig.AUTO_ROTATE);
        adConfig.setMuted(true);
        Vungle.setIncentivizedFields(userId, null, null, null, null);
        if (Vungle.canPlayAd(placementId)) {
            Vungle.playAd(placementId, adConfig, new PlayAdCallback() {
                @Override
                public void creativeId(String creativeId) {
                    //
                }

                @Override
                public void onAdStart(String placementId) {
                    final WritableMap params = Arguments.createMap();
                    params.putString("name", "didShowAd");
                    params.putString("placementId", placementId);
                    sendEvent(reactContext, "DidShowAd", params);
                }

                @Override
                public void onAdEnd(String placementId, boolean completed, boolean isCTAClicked) {
                    //
                }

                @Override
                public void onAdEnd(String placementId) {
                    //
                }

                @Override
                public void onAdClick(String placementId) {
                    //
                }

                @Override
                public void onAdRewarded(String placementId) {
                    //
                }

                @Override
                public void onAdLeftApplication(String placementId) {
                    //
                }

                @Override
                public void onError(String placementId, VungleException exception) {
                    //
                }

                @Override
                public void onAdViewed(String placementId) {
                    final WritableMap params = Arguments.createMap();
                    params.putString("name", "didCloseAd");
                    params.putString("placementId", placementId);
                    sendEvent(reactContext, "DidCloseAd", params);
                }
            });
        } else {
            final WritableMap params = Arguments.createMap();
            params.putString("name", "sdkFailed");
            sendEvent(reactContext, "EventSDKFailed", params);
        }
    }

    @ReactMethod
    public void loadPlacement(final String placementId) {
        innerLoadAds(placementId);
    }

    @ReactMethod
    public void playAd(final String placementId, final String userId) {
        innerShowAds(placementId, userId);
    }

    @ReactMethod
    public void isSDKInitialized(Promise promise) {
        try {
            if (Vungle.isInitialized() == true) {
                promise.resolve("YES");
            } else {
                promise.resolve("NO");
            }
        } catch (Exception e) {
            promise.reject("Error", e);
        }
    }

    @ReactMethod
    public void startVungle(final String appid) {
        initVungle(appid);
    }

    @Override
    public String getName() {
        return "RNVungle";
    }

    @ReactMethod
    public void isAddAvailable(String placemendId, Promise promise) {
        try {
            if(Vungle.canPlayAd(placemendId)) {
                promise.resolve("YES");
            } else {
                promise.resolve("NO");
            }
        } catch (Exception e) {
            promise.reject("Error", e);
        }
    }
}