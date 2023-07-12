package app.momo.multi_publisher_net.Ads;

import static   app.momo.multi_publisher_net.Tools.Data.*;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.momo.multi_publisher_net.Tools.Data;
import app.momo.multi_publisher_net.Tools.Guide;


public class Ads {
   public static MultiNeworks adsManager;

    public Ads(Context context,String url , onLoadData loadData) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,response -> {
            /////////////////////////AdsType////////////////////////////////
            JSONObject Type = response.optJSONObject("UnitType");
            if (Type != null){
            BannerType = Type.optString("BannerType");
            InterType = Type.optString("InterType");
            NativeType  = Type.optString("NativeType");
            RewardType = Type.optString("rewardType");}
            ////////////////////Facebook////////////////////////////////////
            JSONObject facebook = response.optJSONObject("Facebook");
            IsFanOn = facebook.optBoolean("IsOn");
            FAN_Facebook.BannerUnitId = facebook.optString("Banner");
            FAN_Facebook.InterUnitId = facebook.optString("Inter");
            FAN_Facebook.NativeUnitId = facebook.optString("Native");
            FAN_Facebook.rewardedUnitId = facebook.optString("Reward");

            ////////////////////////Applovin///////////////////////////////
            JSONObject Applovin = response.optJSONObject("Applovin");
            IsApplovinOn = Applovin.optBoolean("IsOn");
            ApplovinAd.OpenAppUnitId = Applovin.optString("OpenApp");
            ApplovinAd.BannerUnitId = Applovin.optString("Banner");
            ApplovinAd.InterUnitId = Applovin.optString("Inter");
            ApplovinAd.NativeUnitId = Applovin.optString("Native");
            ApplovinAd.rewardedUnitId = Applovin.optString("Reward");

            //////////////////////////Yandex////////////////////////////
            JSONObject yandex = response.optJSONObject("yandex");
            IsYandexOn = yandex.optBoolean("IsOn");
            Yandex.BannerUnitId = yandex.optString("Banner");
            Yandex.InterUnitId = yandex.optString("Inter");
            Yandex.NativeUnitId = yandex.optString("Native");
            Yandex.rewardedUnitId = yandex.optString("Reward");

            //////////////////////////Unity////////////////////////////
            JSONObject unity  =  response.optJSONObject("Unity");
            IsUnityOn =unity.optBoolean("IsOn");
            UnityAd.AppId = unity.optString("AppId");
            UnityAd.BannerUnitId = unity.optString("Banner");
            UnityAd.InterUnitId =unity.optString("Inter");
            UnityAd.rewardedUnitId = unity.optString("Reward");

            /////////////////////StartApp////////////////
            JSONObject startApp = response.optJSONObject("StartApp");
            IsStartAppOn = startApp.optBoolean("IsOn");
            Startap.AppId = startApp.optString("AppId");

            ////////////////////////////////////////////////////

            OfficialAppLink = response.optString("NewAppLink");
            isAppOn = response.optBoolean("IsAppOn");
            isUnderMant = response.optBoolean("IsUnderMaintenance");

            ///////////////////////////////////////////////////////

            JSONObject Cpa = response.optJSONObject("Cpa");
            IsCpaOn = Cpa.optBoolean("IsOn");
            cpaLink = Cpa.optString("Link");
            cpaTitle = Cpa.optString("Title");
            cpaDescription = Cpa.optString("ShortDescription");
            cpaImgLink = Cpa.optString("ImgLink");
            cpaBtnText = Cpa.optString("BtnText");

            ///////////////////////////////////////////////////////
            JSONArray tips = response.optJSONArray("Guides");
            for (int i = 0; i < tips.length() ; i++) {
                Guide guide = new Guide(tips.optJSONObject(i).optString("Title"),tips.optJSONObject(i).optString("Description"),tips.optJSONObject(i).optString("Img"));
                guides.add(guide);
            }


            adsManager = MultiNeworks.getInstance();
            if (!isAppOn){
               loadData.isAppGone();
            }else {

            if (isUnderMant){
                loadData.isUnderReview();
            }else {
                loadData.isLoaded();
               adsManager.init(context);

            }}
        }
        ,error -> {
            String s =error.getMessage();
            loadData.fieldToLoad(s);

        });
        Volley.newRequestQueue(context).add(request);
    }
    private static AdsManager Swither() {

        if (Data.IsFanOn){return  FAN_Facebook.getInstance();}
        else if(Data.IsYandexOn){return  Yandex.getInstance() ;}
        else if (Data.IsApplovinOn){return ApplovinAd.getInstance();}
        else if (Data.IsStartAppOn){return Startap.getInstance();}
        else if (Data.IsUnityOn){return UnityAd.getInstance();}
        else {return NullAds.getInstance();}

    }
    public static AdsManager[] Networks() {
        List<AdsManager> networks = new ArrayList<>();

        if (IsFanOn) {
            networks.add(FAN_Facebook.getInstance());
        }
        if (IsYandexOn) {
            networks.add(Yandex.getInstance());
        }
        if (IsApplovinOn) {
            networks.add(ApplovinAd.getInstance());
        }
        if (IsStartAppOn) {
            networks.add(Startap.getInstance());
        }
        if (IsUnityOn) {
            networks.add(UnityAd.getInstance());
        }

        if (networks.isEmpty()) {
            networks.add(NullAds.getInstance());
        }

        return networks.toArray(new AdsManager[0]);
    }
    public interface onLoadData{
        void isLoaded();
        void isUnderReview();
        void isAppGone();
        void  fieldToLoad(String ErrorMessage);
    }
}