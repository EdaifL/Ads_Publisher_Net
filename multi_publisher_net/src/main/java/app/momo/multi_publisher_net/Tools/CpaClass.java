package app.momo.multi_publisher_net.Tools;

import static app.momo.multi_publisher_net.Tools.Data.*;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import app.momo.multi_publisher_net.R;


public class CpaClass {
    private Activity activity;
    TextView Title,Description;
    ImageView Close,OfferImg;
    Button OfferBtn;
    ProgressBar progressBar;
    private Dialog dialog;

    public CpaClass(Activity activity) {
        this.activity = activity;
    }
    public void showOffer(){
        dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.cpamodel);
        dialog.setCancelable(false);
        Title = dialog.findViewById(R.id.Title);
        Description = dialog.findViewById(R.id.Description);
        Close = dialog.findViewById(R.id.CloseBtn);
        OfferImg = dialog.findViewById(R.id.AdImg);
        OfferBtn = dialog.findViewById(R.id.CpaBtn);
        progressBar = dialog.findViewById(R.id.progressBar2);
        if (!cpaTitle.isEmpty()){
            Title.setText(cpaTitle);
        }else {
            Title.setVisibility(View.INVISIBLE);
        }
        if (!cpaDescription.isEmpty()){
            Description.setText(cpaDescription);
        }else{
            Description.setVisibility(View.GONE);
        }
        if (!cpaBtnText.isEmpty()){
            OfferBtn.setText(cpaBtnText);
        }
        if (!cpaImgLink.isEmpty()){
            Glide.with(activity).load(cpaImgLink).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    OfferImg.setVisibility(View.VISIBLE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    OfferImg.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(OfferImg);

        }
        OfferBtn.setOnClickListener(v -> {
            openUrlInBrowser(activity,cpaLink);
        });
        Close.setOnClickListener(v -> {
            dialog.dismiss();
        });
    dialog.show();
    }
    private void openUrlInBrowser(Context context, String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");

        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);

        if (resolveInfos.size() > 0) {
            context.startActivity(intent);
        } else {
            intent.setPackage(null);
            context.startActivity(intent);
        }
    }
}