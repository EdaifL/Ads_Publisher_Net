package app.momo.multi_publisher_net.Tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import app.momo.multi_publisher_net.R;

public class CpaAsNative {


    public void Load(Context context, LoadData load){
        CpaNative cpaNative = new CpaNative(context);
        Glide.with(context)
                .load(cpaNative.imageView)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        load.load(cpaNative);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        cpaNative.titleTextView.setText(Data.cpaTitle);
        cpaNative.descriptionTextView.setText(Data.cpaDescription);
        cpaNative.button.setText(Data.cpaBtnText);
        cpaNative.button.setOnClickListener(v -> {
            openUrlInBrowser(context,Data.cpaLink);
        });
        cpaNative.imageView.setOnClickListener(v -> {
            openUrlInBrowser(context,Data.cpaLink);
        });


    }

    public class CpaNative extends CardView {
            private TextView titleTextView;
            private TextView descriptionTextView;
            private ImageView imageView;
            private Button button;
    public CpaNative(@NonNull Context context) {
                super(context);

                initialize();
            }

    public CpaNative(@NonNull Context context, @Nullable AttributeSet attrs) {
                super(context, attrs);

                initialize();
            }

    public CpaNative(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
                initialize();
            }
            private void initialize() {
                inflate(getContext(), R.layout.cpamodel, this);
                titleTextView = findViewById(R.id.Title);
                descriptionTextView = findViewById(R.id.Description);
                imageView = findViewById(R.id.AdImg);
                button = findViewById(R.id.CpaBtn);
            }

            public void setTitle(String title) {
                titleTextView.setText(title);
            }

            public void setDescription(String description) {
                descriptionTextView.setText(description);
            }

            public void setImage(int resourceId) {
                imageView.setImageResource(resourceId);
                imageView.setOnClickListener(v -> {

                });
            }

            public void setButtonText(String text) {
                button.setText(text);

            }
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
    public interface LoadData{
        void load(CpaNative cpaOffer);
    }
}

