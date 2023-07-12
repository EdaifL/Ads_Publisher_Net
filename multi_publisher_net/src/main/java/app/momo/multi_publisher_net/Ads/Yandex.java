package app.momo.multi_publisher_net.Ads;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdEventListener;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.common.MobileAds;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.nativeads.NativeAd;
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener;
import com.yandex.mobile.ads.nativeads.NativeAdLoader;
import com.yandex.mobile.ads.nativeads.NativeAdMedia;
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration;
import com.yandex.mobile.ads.nativeads.NativeAdView;
import com.yandex.mobile.ads.nativeads.template.NativeBannerView;
import com.yandex.mobile.ads.rewarded.Reward;
import com.yandex.mobile.ads.rewarded.RewardedAd;
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener;

import app.momo.multi_publisher_net.Tools.DialogLoad;


public class Yandex implements AdsManager {
    private String TAG = "Yandex";
    private InterstitialAd interstitialAd;
    private RewardedAd mRewardedAd;
    private NativeAdView mNativeAdView;
    private RewardedAd rewardedAd;
    private Dialog dialog;
    public static String BannerUnitId;
    public static String NativeUnitId;
    public static String InterUnitId;
    public static Yandex yandex;
    public static String rewardedUnitId;
    public static Yandex getInstance() {
        if (yandex == null){
            yandex = new Yandex();
        }

        return yandex;
    }







    private NativeAdLoader mNativeAdLoader;





    @Override
    public void init(Context context) {
        MobileAds.initialize(context, () -> Log.d(TAG, "SDK initialized"));
        MobileAds.enableDebugErrorIndicator(true);
    }

    @Override
    public void Showbanner(Context activity, FrameLayout layout) {
        BannerAdView mBannerAdView = new BannerAdView(activity);
        mBannerAdView.setAdUnitId(BannerUnitId);
        mBannerAdView.setAdSize(AdSize.stickySize(AdSize.FULL_SCREEN.getWidth()));
        final AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAdView.setBannerAdEventListener(new BannerAdEventListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(AdRequestError adRequestError) {
                Log.d(TAG, "onAdFailedToLoad: " + adRequestError.getDescription());
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {
            }

            @Override
            public void onReturnedToApplication() {
            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });
        // Loading the ad.
        layout.addView(mBannerAdView);
        mBannerAdView.loadAd(adRequest);
    }

    @Override
    public void ShowInter(Context activity, Interstitial interstitial) {
        dialog = new DialogLoad(activity);
        dialog.show();
        interstitialAd = new InterstitialAd(activity);
        interstitialAd.setAdUnitId(InterUnitId);
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
        interstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {


                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                interstitial.isField();
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                interstitial.isShowed();
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });
    }

    @Override
    public void ShowNative(Context activity, FrameLayout layout) {
        mNativeAdLoader = new NativeAdLoader(activity);
        mNativeAdLoader.setNativeAdLoadListener(new NativeAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull final NativeAd nativeAd) {
                layout.removeAllViews();
                bind_NativeAd(nativeAd, activity,layout);
            }

            @Override
            public void onAdFailedToLoad(@NonNull final AdRequestError adRequestError) {
                Log.d("SAMPLE_TAG", adRequestError.getDescription());
            }
        });

        final NativeAdRequestConfiguration nativeAdRequestConfiguration =
                new NativeAdRequestConfiguration.Builder(NativeUnitId)
                        .setShouldLoadImagesAutomatically(true)
                        .build();
        mNativeAdLoader.loadAd(nativeAdRequestConfiguration);


    }

    @Override
    public void ShowReward(Context context, RewardedVideo rewarded) {
        dialog = new DialogLoad(context);
        dialog.show();
        rewardedAd = new RewardedAd(context);
        rewardedAd.setAdUnitId(rewardedUnitId);
        rewardedAd.loadAd(new AdRequest.Builder().build());
        rewardedAd.setRewardedAdEventListener(new RewardedAdEventListener() {
            @Override
            public void onAdLoaded() {
                rewardedAd.show();
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                rewarded.ErrorToLoad();
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onRewarded(@NonNull Reward reward) {
                rewarded.Completed();
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });
    }

    private void bind_NativeAd(@NonNull final NativeAd nativeAd, Context activity, FrameLayout layout) {
        NativeBannerView mNativeBannerView = new NativeBannerView(activity);
        mNativeBannerView.setAd(nativeAd);
        layout.removeAllViews();
        layout.addView(mNativeBannerView);
    }


    private void configureMediaView(@NonNull final NativeAd nativeAd) {
        final NativeAdMedia nativeAdMedia = nativeAd.getAdAssets().getMedia();
        if (nativeAdMedia != null) {
            //you can use the aspect ratio if you need it to determine the size of media view.
            final float aspectRatio = nativeAdMedia.getAspectRatio();
        }
    }


    public void destroy() {
        if (mRewardedAd != null) {
            mRewardedAd.setRewardedAdEventListener(null);
            mRewardedAd.destroy();
        }
    }
}
