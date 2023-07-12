package app.momo.multi_publisher_net.Ads;


import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;

public interface AdsManager {
     void init(Context context);
     void Showbanner(Context context,FrameLayout layout);
     void ShowInter(Context context,Intent intent);
     void ShowNative(Context context,FrameLayout layout);
     void ShowReward(Context context , RewardedVideo rewarded);
}
