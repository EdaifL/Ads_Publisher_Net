package app.momo.multi_publisher_net.Ads;

import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;

public class NullAds implements AdsManager {
    public static NullAds nullAds;
    public static NullAds getInstance(){
        if (nullAds == null){
            nullAds = new NullAds();
        }
        return nullAds;
    }

    @Override
    public void init(Context context) {

    }


    @Override
    public void Showbanner(Context context, FrameLayout layout) {

    }

    @Override
    public void ShowInter(Context context, Intent intent) {
        context.startActivity(intent);
    }

    @Override
    public void ShowNative(Context context, FrameLayout layout) {

    }

    @Override
    public void ShowReward(Context context, RewardedVideo rewarded) {

    }
}
