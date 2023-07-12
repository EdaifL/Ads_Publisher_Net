package app.momo.multi_publisher_net.Tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.momo.multi_publisher_net.R;

public class rateApp  {

    private Activity activity;
    public SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;
    private Dialog alertDialog;

    public rateApp(Activity myAct) {
        activity = myAct;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void rate() {
        showRateDialog(activity);
    }
    Button rate;
    public void showRateDialog(final Activity mcontext) {
        alertDialog = new Dialog(mcontext);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setContentView(R.layout.rate_app);
        alertDialog.setCancelable(false);

        rate = alertDialog.findViewById(R.id.rateNow);

        TextView later = alertDialog.findViewById(R.id.later);
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                activity.finishAffinity();
            }
        });
        rate.setOnClickListener(v -> {
            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName())));
                activity.finishAffinity();
            } catch (ActivityNotFoundException e) {
                activity.startActivity(new Intent(Intent.ACTION_APP_ERROR));
                alertDialog.dismiss();
            }
        });



        alertDialog.show();

    }
}