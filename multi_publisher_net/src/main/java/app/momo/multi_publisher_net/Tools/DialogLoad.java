package app.momo.multi_publisher_net.Tools;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import app.momo.multi_publisher_net.R;

public class DialogLoad extends Dialog {
    public DialogLoad(@NonNull Context context) {
        super(context);
        this.setContentView(R.layout.loading);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
