package app.momo.multi_publisher_net.Ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;

import app.momo.multi_publisher_net.R;
import app.momo.multi_publisher_net.Tools.DialogLoad;


public class ApplovinAd implements AdsManager {
    public static ApplovinAd applovinAd;
    private MaxAppOpenAd appOpenAd;
    private static boolean IsOpenshowed = false;
    private MaxAdView adView;
    MaxInterstitialAd interstitialAd;
    private MaxRewardedAd rewardedAd;
    Dialog dialog;
    public static String BannerUnitId;
    public static String NativeUnitId;
    public static String InterUnitId;
    public static String OpenAppUnitId;
    public static String rewardedUnitId;
    private String LogTag = "Applovin";
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd             nativeAd;

    public static ApplovinAd getInstance() {
        if (applovinAd ==null){
            applovinAd = new ApplovinAd();
        }
        return applovinAd;
    }
    @Override
    public void init(Context context){
        AppLovinSdk.getInstance( context ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( context, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {

            }
        } );

    }




    public void ShowOpenApp(Context context, Intent intent) {
        dialog = new DialogLoad(context);
        if (!IsOpenshowed && OpenAppUnitId.isEmpty()){
            dialog.show();
            appOpenAd = new MaxAppOpenAd( OpenAppUnitId, context);
            appOpenAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    if ( appOpenAd == null || !AppLovinSdk.getInstance( context ).isInitialized() ) return;

                    if ( appOpenAd.isReady() )
                    {
                        appOpenAd.showAd( OpenAppUnitId );
                    }
                    else
                    {
                        appOpenAd.loadAd();
                    }
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {
                    if (dialog.isShowing()){dialog.dismiss();}
                    IsOpenshowed =true;
                }

                @Override
                public void onAdHidden(MaxAd ad) {

                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    appOpenAd.loadAd();
                    if (dialog.isShowing()){dialog.dismiss();}
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    appOpenAd.loadAd();
                    if (dialog.isShowing()){dialog.dismiss();}
                }
            });
            appOpenAd.loadAd();
        }
    }

    @Override
    public void Showbanner(Context context,FrameLayout layout) {
        if (!BannerUnitId.isEmpty()){
            adView = new MaxAdView( BannerUnitId, context );
            adView.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(MaxAd ad) {

                }

                @Override
                public void onAdCollapsed(MaxAd ad) {

                }

                @Override
                public void onAdLoaded(MaxAd ad) {


                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {

                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    Log.i(LogTag,error.getMessage());
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    Log.i(LogTag,error.getMessage());
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            adView.loadAd();
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int heightPx = context.getResources().getDimensionPixelSize( R.dimen.banner_height );
            adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );
            adView.setBackgroundColor(Color.WHITE);
            adView.startAutoRefresh();
            layout.addView(adView);
        }
    }


    @Override
    public void ShowInter(Context context,Interstitial interstitial) {

        if (!InterUnitId.isEmpty()){

            interstitialAd =new MaxInterstitialAd(InterUnitId, (Activity) context);
            interstitialAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    interstitialAd.showAd(InterUnitId);
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {

                    if (dialog.isShowing()){dialog.dismiss();}
                  interstitial.isShowed();
                    if (adView != null){adView.destroy();}
                    if (interstitialAd !=null){interstitialAd.destroy();}

                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    if (dialog.isShowing()){dialog.dismiss();}
                    interstitial.isField();
                    if (adView != null){adView = null;}
                    if (interstitialAd !=null){interstitialAd.destroy();}
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    if (dialog.isShowing()){dialog.dismiss();}
                    interstitial.isShowed();
                    if (adView != null){adView = null;}
                    if (interstitialAd !=null){interstitialAd.destroy();}
                }
            });
            interstitialAd.loadAd();
        }else{
            interstitial.isShowed();
        }

    }

    @Override
    public void ShowNative(Context context,FrameLayout layout) {
        if (!NativeUnitId.isEmpty()) {
            nativeAdLoader = new MaxNativeAdLoader(NativeUnitId, context);
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                    // Clean up any pre-existing native ad to prevent memory leaks.
                    nativeAd = ad;

                    layout.removeAllViews();
                    layout.addView(nativeAdView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,800));
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    Log.i(LogTag,error.getMessage());
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {
                    // Optional click callback
                }
            });

            nativeAdLoader.loadAd();

        }
    }

    @Override
    public void ShowReward(Context context, RewardedVideo rewarded) {
        dialog = new DialogLoad(context);
        dialog.show();
        rewardedAd = MaxRewardedAd.getInstance(rewardedUnitId, (Activity) context);
        rewardedAd.setListener(new MaxRewardedAdListener() {
            @Override
            public void onUserRewarded(MaxAd maxAd, MaxReward maxReward) {

            }

            @Override
            public void onRewardedVideoStarted(MaxAd maxAd) {

            }

            @Override
            public void onRewardedVideoCompleted(MaxAd maxAd) {
                if (dialog.isShowing()){dialog.dismiss();}
                rewarded.Completed();

            }

            @Override
            public void onAdLoaded(MaxAd maxAd) {
                rewardedAd.showAd();

            }

            @Override
            public void onAdDisplayed(MaxAd maxAd) {

            }

            @Override
            public void onAdHidden(MaxAd maxAd) {
                if (dialog.isShowing()){dialog.dismiss();}
            }

            @Override
            public void onAdClicked(MaxAd maxAd) {

            }

            @Override
            public void onAdLoadFailed(String s, MaxError maxError) {
                if (dialog.isShowing()){dialog.dismiss();}
                rewarded.ErrorToLoad();
            }

            @Override
            public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                if (dialog.isShowing()){dialog.dismiss();}
                rewarded.ErrorToLoad();
            }
        });
        rewardedAd.loadAd();
    }


}
