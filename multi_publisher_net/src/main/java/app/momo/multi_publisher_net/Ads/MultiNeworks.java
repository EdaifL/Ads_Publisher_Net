package app.momo.multi_publisher_net.Ads;

import static app.momo.multi_publisher_net.Tools.Data.*;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.Random;

public class MultiNeworks {
   private static AdsManager adsManager;
   public static MultiNeworks ads;
   public static MultiNeworks getInstance(){
       if (ads == null){
           ads =new MultiNeworks();

       }
       return ads;
   }
    int size = Ads.Networks().length;
    public void init(Context context) {
        for (AdsManager ads:Ads.Networks()) {
            adsManager = ads;
            adsManager.init(context);
                   }
    }

    public void Showbanner(Context context, FrameLayout layout) {
         ;
        if (!BannerType.isEmpty() && Ads.Networks().length> 1){
            adsManager = adsManager(BannerType.toUpperCase());
            adsManager.Showbanner(context, layout);
            Log.e("Network",adsManager.getClass().getName().toString());
        }else {

        adsManager = Ads.Networks()[random()];
        adsManager.Showbanner(context, layout);
            Log.e("Network",adsManager.getClass().getName().toString());
        }

    }


    public void ShowInter(Context context,Interstitial interstitial) {
         ;

        if (!InterType.isEmpty()&&  size> 1){
            adsManager = adsManager(InterType.toUpperCase());
            adsManager.ShowInter(context,interstitial);
            Log.e("Network",adsManager.getClass().getName().toString());
        }else {
        adsManager = Ads.Networks()[random()];
        adsManager.ShowInter(context, interstitial);
            Log.e("Network",adsManager.getClass().getName().toString());
        }



    }

    public void ShowNative(Context context, FrameLayout layout) {
         ;
        if (!NativeType.isEmpty()&& Ads.Networks().length> 1){
            adsManager  = adsManager(NativeType.toUpperCase());
            adsManager.ShowNative(context,layout);
            Log.e("Network",adsManager.getClass().getName().toString());
        }else {
        adsManager = Ads.Networks()[random()];
        while (adsManager.getClass().getName().toString().equals("app.mourad.guide.Ads.UnityAd")){

            adsManager = Ads.Networks()[random()];
        }

        adsManager.ShowNative(context, layout);
            Log.e("Network",adsManager.getClass().getName().toString());
        }
    }

    public void ShowReward(Context context, RewardedVideo rewarded) {
         ;
        if (!RewardType.isEmpty()&& Ads.Networks().length> 1){
            adsManager = adsManager(RewardType.toUpperCase());
            adsManager.ShowReward(context, rewarded);
            Log.e("Network",adsManager.getClass().getName().toString());
        }else {
        adsManager = Ads.Networks()[random()];
        adsManager.ShowReward(context, rewarded);
            Log.e("Network",adsManager.getClass().getName().toString());
        }

    }
    private AdsManager adsManager(String Type){
        switch (Type){
            case "FACEBOOK": return FAN_Facebook.getInstance();
            case "APPLOVIN" : return ApplovinAd.getInstance() ;
            case  "UNITY" : return UnityAd.getInstance() ;
            case "YANDEX" : return Yandex.getInstance() ;
            case "STARTAPP" : return Startap.getInstance();
            default: return NullAds.getInstance();

        }
    }
    private Integer random(){
        int random = new Random().nextInt(Ads.Networks().length);
        return random;
    }






}
