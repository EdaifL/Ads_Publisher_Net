package app.momo.multi_publisher_net.Ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import app.momo.multi_publisher_net.Tools.DialogLoad;



public class UnityAd implements AdsManager {
    static final String TAG = "UnityAds";
    public static String ReweardVideo;
    private BannerView bottomBanner;
    private final Boolean testMode = false;
    private Dialog dialog;
    public static String BannerUnitId;
    public static String InterUnitId;
    public static String rewardedUnitId;
    public static String AppId;
    public static UnityAd unityAd;


    public static UnityAd getInstance() {
        if (unityAd == null) {
            unityAd =  new UnityAd();



        }
        return unityAd;
    }



    public void LoadInter() {
        UnityAds.load(InterUnitId, loadListener);
    }


    @Override
    public void init(Context context) {
        UnityAds.initialize(context, AppId,testMode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                Log.e("UnityAdsExample", "Unity Ads initialization complete ");
                LoadInter();
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                Log.e("UnityAdsExample", "Unity Ads initialization failed with error: [" + error + "] " + message);

            }
        });
    }


    @Override
    public void Showbanner(Context activity, FrameLayout layout) {
        Log.d(TAG, "ShowBanner: ");
        bottomBanner = new BannerView((Activity) activity, BannerUnitId, new UnityBannerSize(320, 50));
        // Set the listener for banner lifecycle events:
        bottomBanner.setListener(new BannerView.Listener() {
            @Override
            public void onBannerLoaded(BannerView bannerAdView) {
                super.onBannerLoaded(bannerAdView);
                Log.d(TAG, "onBannerLoaded: " + bannerAdView.getPlacementId());
                layout.removeAllViews();
            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                super.onBannerFailedToLoad(bannerAdView, errorInfo);
                Log.d(TAG, "onBannerFailedToLoad: " + errorInfo.errorMessage);
            }

            @Override
            public void onBannerClick(BannerView bannerAdView) {
                super.onBannerClick(bannerAdView);
            }

            @Override
            public void onBannerLeftApplication(BannerView bannerAdView) {
                super.onBannerLeftApplication(bannerAdView);
            }
        });
        bottomBanner.load();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        bottomBanner.setLayoutParams(params);
        layout.addView(bottomBanner);
    }

    @Override
    public void ShowInter(Context activity,Interstitial interstitial) {
        dialog = new DialogLoad(activity);
        dialog.show();
        UnityAds.load(InterUnitId, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String s) {
                UnityAds.show((Activity) activity, "Rewarded_Android", new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                        if (dialog.isShowing()){dialog.dismiss(); }
                        interstitial.isField();

                    }

                    @Override
                    public void onUnityAdsShowStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowClick(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {

                        if (dialog.isShowing()){dialog.dismiss(); }
                        interstitial.isShowed();

                    }
                });
            }

            @Override
            public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {

                if (dialog.isShowing()){dialog.dismiss(); }
                interstitial.isField();
            }
        });


    }

    @Override
    public void ShowNative(Context context, FrameLayout layout) {

    }

    @Override
    public void ShowReward(Context context, RewardedVideo rewarded) {
        dialog = new DialogLoad(context);
        dialog.show();
        UnityAds.load(rewardedUnitId, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                dialog.dismiss();
                UnityAds.show((Activity) context, rewardedUnitId , new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                        rewarded.ErrorToLoad();
                    }

                    @Override
                    public void onUnityAdsShowStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowClick(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                        rewarded.Completed();
                    }
                });
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                rewarded.ErrorToLoad();
            }
        });

    }

    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
        @Override
        public void onUnityAdsAdLoaded(String placementId) {

        }

        @Override
        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {

            Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
            //  LoadInter();

        }
    };
}
