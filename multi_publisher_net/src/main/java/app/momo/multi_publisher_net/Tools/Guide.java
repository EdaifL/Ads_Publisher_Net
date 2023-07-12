package app.momo.multi_publisher_net.Tools;

public class Guide {

private String Title, Description , Imglink;

    public Guide() {
    }

    public Guide(String title, String description,String imglink) {
        Title = title;
        Imglink = imglink;
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImglink() {
        return Imglink;
    }

    public void setImglink(String imglink) {
        Imglink = imglink;
    }

    @Override
    public String toString() {
        return "Guide{" +
                "Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", Imglink='" + Imglink + '\'' +
                '}';
    }
}
