package app.momo.multi_publisher_net.Ads;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.nativead.NativeAdDetails;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;

import java.util.List;

import app.momo.multi_publisher_net.R;
import app.momo.multi_publisher_net.Tools.DialogLoad;

public class Startap implements AdsManager {
    public static String AppId;
    public static Startap startap;
    private StartAppAd startAppAd;
    public static Startap getInstance(){
        if (startap == null){
            startap = new Startap();
        }
        return startap;
    }
    @Override
    public void init(Context context) {
        StartAppSDK.init(context,AppId );
        startAppAd = new StartAppAd(context);

    }


    @Override
    public void Showbanner(Context context, FrameLayout layout) {
        Banner banner = new Banner(context);
        RelativeLayout.LayoutParams bannerParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
// Add to main Layout
        layout.addView(banner, bannerParameters);
    }
    Dialog dialog;
    @Override
    public void ShowInter(Context context,Interstitial interstitial) {
        dialog = new DialogLoad(context);
        dialog.show();
        StartAppAd startAppAd = new StartAppAd(context);
        startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC);
        startAppAd.showAd(new AdDisplayListener() {
            @Override
            public void adHidden(Ad ad) {
                if (dialog.isShowing()){dialog.dismiss();}
                interstitial.isShowed();
            }

            @Override
            public void adDisplayed(Ad ad) {

            }

            @Override
            public void adClicked(Ad ad) {

            }

            @Override
            public void adNotDisplayed(Ad ad) {
                if (dialog.isShowing()){dialog.dismiss();}
               interstitial.isField();
            }
        });
    }
    @Override
    public void ShowNative(Context context, FrameLayout layout) {


        NativeAdPreferences nativePrefs = new NativeAdPreferences()
                .setAutoBitmapDownload(true)
                .setPrimaryImageSize(2) .setSecondaryImageSize(2);
        StartAppNativeAd startAppNativeAd = new StartAppNativeAd(context);
        startAppNativeAd.loadAd(nativePrefs,new AdEventListener() {
            @Override
            public void onReceiveAd(@NonNull Ad ad) {
                layout.removeAllViews();
                NativeAdDetails nativeAdDetails = startAppNativeAd.getNativeAds().get(0);
                StartAppNativeAd.CampaignAction campaignAction =  startAppNativeAd.getNativeAds().get(0).getCampaignAction();

                NativeAdModel nativeAdModel = createNativeAdModel(nativeAdDetails);
                View nativeAdView = LayoutInflater.from(context).inflate(R.layout.startapp_native, layout, false);
                bindNativeAdModelToViews(context,nativeAdModel, campaignAction,nativeAdView);
                layout.addView(nativeAdView);
                nativeAdView.setOnClickListener(v -> {
                    if (campaignAction == StartAppNativeAd.CampaignAction.OPEN_MARKET) {

                    } else if (campaignAction == StartAppNativeAd.CampaignAction.LAUNCH_APP) {

                    } else {

                    }
                });
            }

            @Override
            public void onFailedToReceiveAd(@Nullable Ad ad) {
                // Handle the failed ad loading here
            }
        });
    }




    @Override
    public void ShowReward(Context context,RewardedVideo rewarded) {
        dialog = new DialogLoad(context);
        dialog.show();
        startAppAd = new StartAppAd(context);
        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
            @Override
            public void onReceiveAd(@NonNull Ad ad) {
                dialog.dismiss();
                startAppAd.showAd(new AdDisplayListener() {
                    @Override
                    public void adHidden(Ad ad) {

                    }

                    @Override
                    public void adDisplayed(Ad ad) {

                    }

                    @Override
                    public void adClicked(Ad ad) {

                    }

                    @Override
                    public void adNotDisplayed(Ad ad) {

                    }
                });
                startAppAd.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoCompleted() {
                        rewarded.Completed();
                    }
                });
            }

            @Override
            public void onFailedToReceiveAd(@Nullable Ad ad) {

            }
        });

    }

    private void bindNativeAdModelToViews(Context context,NativeAdModel nativeAdModel,StartAppNativeAd.CampaignAction campaignAction, View nativeAdView) {
        ImageView iconImageView = nativeAdView.findViewById(R.id.native_ad_icon);
        TextView titleTextView = nativeAdView.findViewById(R.id.native_ad_title);
        TextView descriptionTextView = nativeAdView.findViewById(R.id.native_ad_description);
        Button callToActionButton = nativeAdView.findViewById(R.id.native_ad_button);
        ImageView imageView = nativeAdView.findViewById(R.id.native_ad_image);

        iconImageView.setImageBitmap(nativeAdModel.getIconBitmap());
        titleTextView.setText(nativeAdModel.getTitle());
        descriptionTextView.setText(nativeAdModel.getDescription());
        callToActionButton.setText(nativeAdModel.getCallToActionText());
        Glide.with(context).load(nativeAdModel.getImageBitmap()).into(imageView);
        callToActionButton.setOnClickListener(v -> {
            if (campaignAction == StartAppNativeAd.CampaignAction.OPEN_MARKET) {
                openUrlInBrowser(context, nativeAdModel.getLink());
            } else if (campaignAction == StartAppNativeAd.CampaignAction.LAUNCH_APP) {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(nativeAdModel.getPackegeName());
                if (intent != null) {
                    context.startActivity(intent);
                }
            }
        });

    }
    private void openUrlInBrowser(Context context,String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome"); // Set the package name of the default browser

        // Check if the default browser is installed
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);

        if (resolveInfos.size() > 0) {
            // Default browser is installed, open the URL directly
            context.startActivity(intent);
        } else {
            // Default browser is not installed, fallback to any available browser
            intent.setPackage(null);
            context.startActivity(intent);
        }
    }
    private NativeAdModel createNativeAdModel(NativeAdDetails nativeAdDetails) {
        NativeAdModel nativeAdModel = new NativeAdModel();
        nativeAdModel.setTitle(nativeAdDetails.getTitle());
        nativeAdModel.setDescription(nativeAdDetails.getDescription());
        nativeAdModel.setIconBitmap(nativeAdDetails.getSecondaryImageBitmap());
        nativeAdModel.setCallToActionText(nativeAdDetails.getCallToAction());
        nativeAdModel.setImageBitmap(nativeAdDetails.getImageBitmap());
        nativeAdModel.setPackegeName(nativeAdDetails.getPackageName());
        nativeAdModel.setLink(nativeAdDetails.a.g());
        return nativeAdModel;
    }
}
