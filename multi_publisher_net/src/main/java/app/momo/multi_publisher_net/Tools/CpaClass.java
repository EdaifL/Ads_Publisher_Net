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


public class CpaClass extends Dialog {
    private Activity activity;
    TextView Title,Description;
    ImageView Close,OfferImg;
    Button OfferBtn;
    ProgressBar progressBar;

    private static int idShowed = 0;
    public CpaClass(Activity activity) {
        super(activity);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setContentView(R.layout.cpamodel);
        this.setCancelable(false);
        Title = this.findViewById(R.id.Title);
        Description = this.findViewById(R.id.Description);
        Close = this.findViewById(R.id.CloseBtn);
        OfferImg = this.findViewById(R.id.AdImg);
        OfferBtn = this.findViewById(R.id.CpaBtn);
        progressBar = this.findViewById(R.id.progressBar2);
        CpaModel cpaModel = CpaPopUp.get(idShowed);
        if (idShowed +1 == CpaPopUp.size()){
            idShowed = 0 ;
        }else {
            idShowed +=1;
        }

        if (!cpaModel.getCpaTitle().isEmpty()){
            Title.setText(cpaModel.getCpaTitle());
        }else {
            Title.setVisibility(View.INVISIBLE);
        }
        if (!cpaModel.getCpaDescription().isEmpty()){
            Description.setText(cpaModel.getCpaDescription());
        }else{
            Description.setVisibility(View.GONE);
        }
        if (!cpaModel.getCpaBtnText().isEmpty()){
            OfferBtn.setText(cpaModel.getCpaBtnText());
        }
        if (!cpaModel.getCpaImgLink().isEmpty()){
            Glide.with(activity).load(cpaModel.getCpaImgLink()).addListener(new RequestListener<Drawable>() {
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
            openUrlInBrowser(activity, cpaModel.getCpaLink());
        });
        Close.setOnClickListener(v -> {
            this.dismiss();
        });
        this.show();
    }
    public void showOffer(){

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
