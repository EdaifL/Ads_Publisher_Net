package app.momo.multi_publisher_net.Ads;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdView;
import com.facebook.ads.NativeAdViewAttributes;
import com.facebook.ads.NativeBannerAd;
import com.facebook.ads.NativeBannerAdView;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;

import app.momo.multi_publisher_net.Tools.DialogLoad;

public class FAN_Facebook implements AdsManager {
    private AdView adView = null;
    public static FAN_Facebook facebook;
    private AdListener adListener;
    private InterstitialAd interstitialAd;
    private NativeAd nativeAd;
    private Dialog dialog;
    public static String BannerUnitId;
    public static String NativeUnitId;
    public static String InterUnitId;
    public static String rewardedUnitId;
    private RewardedVideoAd rewardedVideoAd;
    public static FAN_Facebook getInstance() {

      if (facebook == null){
          facebook  = new FAN_Facebook();
      }
      return facebook;

    }

    @Override
    public void init(Context context) {
        AudienceNetworkAds.initialize(context);
    }


    @Override
    public void Showbanner(Context activity, FrameLayout layout) {
        NativeBannerAd nativeBannerAd = new NativeBannerAd(activity, BannerUnitId);
        NativeAdListener nativeAdListener = new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                layout.removeAllViews();
                NativeAdViewAttributes viewAttributes = new NativeAdViewAttributes(activity)
                        .setBackgroundColor(Color.WHITE)
                        .setTitleTextColor(Color.BLACK)
                        .setDescriptionTextColor(Color.DKGRAY)
                        .setButtonColor(Color.BLACK)
                        .setButtonBorderColor(Color.WHITE)
                        .setButtonTextColor(Color.WHITE);
                View adView = NativeBannerAdView.render(activity, nativeBannerAd, NativeBannerAdView.Type.HEIGHT_100, viewAttributes);
                layout.addView(adView);

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        nativeBannerAd.loadAd(
                nativeBannerAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());



    }

    @Override
    public void ShowInter(Context activity,Interstitial interstitial) {
        dialog = new DialogLoad(activity);
        dialog.show();
        interstitialAd = new InterstitialAd(activity, InterUnitId);
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig()
                .withAdListener(new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {

                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                        interstitial.isShowed();
                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                       interstitial.isField();
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        interstitialAd.show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                })
                .build());
    }

    @Override
    public void ShowNative(Context activity, FrameLayout layout) {
        nativeAd = new NativeAd(activity, NativeUnitId);


        nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                layout.removeAllViews();
                NativeAdViewAttributes viewAttributes = new NativeAdViewAttributes(activity)
                        .setBackgroundColor(Color.WHITE)
                        .setTitleTextColor(Color.BLACK)
                        .setDescriptionTextColor(Color.DKGRAY)
                        .setButtonColor(Color.BLACK)
                        .setButtonBorderColor(Color.WHITE)
                        .setButtonTextColor(Color.WHITE);
                View nativeAdView = NativeAdView.render(activity, nativeAd, viewAttributes);
                layout.addView(nativeAdView, new LinearLayout.LayoutParams(MATCH_PARENT, 800));
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

        });
        nativeAd.loadAd();

    }

    @Override
    public void ShowReward(Context context, RewardedVideo rewarded) {
        dialog = new DialogLoad(context);
        dialog.show();
        rewardedVideoAd = new RewardedVideoAd(context,rewardedUnitId);

        rewardedVideoAd.loadAd(rewardedVideoAd.buildLoadAdConfig().withAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoCompleted() {
                if (dialog.isShowing()){dialog.dismiss();}
                rewarded.Completed();
            }

            @Override
            public void onRewardedVideoClosed() {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                if (dialog.isShowing()){dialog.dismiss();}
                rewarded.ErrorToLoad();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                rewardedVideoAd.show();

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
    }
}
