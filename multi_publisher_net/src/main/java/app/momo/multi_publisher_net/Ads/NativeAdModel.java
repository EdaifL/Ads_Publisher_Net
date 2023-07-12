package app.momo.multi_publisher_net.Ads;

import android.graphics.Bitmap;

public class NativeAdModel {
    private String PackegeName;
    private String title;
    private String Link;
    private String description;
    private Bitmap iconBitmap;
    private Bitmap imageBitmap;
    private String callToActionText;

    // Constructor
    public NativeAdModel() {
    }

    // Getter and Setter methods

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.iconBitmap = iconBitmap;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getCallToActionText() {
        return callToActionText;
    }

    public void setCallToActionText(String callToActionText) {
        this.callToActionText = callToActionText;
    }

    public String getPackegeName() {
        return PackegeName;
    }

    public void setPackegeName(String setPackegeName) {
        this.PackegeName = setPackegeName;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
