package app.momo.multi_publisher_net.Tools;

public class CpaModel {
    private String cpaLink;
    private String cpaTitle;
    private String cpaDescription;
    private String cpaBtnText;
    private String cpaImgLink;

    public CpaModel( String cpaLink, String cpaTitle, String cpaDescription, String cpaBtnText, String cpaImgLink) {

        this.cpaLink = cpaLink;
        this.cpaTitle = cpaTitle;
        this.cpaDescription = cpaDescription;
        this.cpaBtnText = cpaBtnText;
        this.cpaImgLink = cpaImgLink;
    }


    public String getCpaLink() {
        return cpaLink;
    }

    public void setCpaLink(String cpaLink) {
        this.cpaLink = cpaLink;
    }

    public String getCpaTitle() {
        return cpaTitle;
    }

    public void setCpaTitle(String cpaTitle) {
        this.cpaTitle = cpaTitle;
    }

    public String getCpaDescription() {
        return cpaDescription;
    }

    public void setCpaDescription(String cpaDescription) {
        this.cpaDescription = cpaDescription;
    }

    public String getCpaBtnText() {
        return cpaBtnText;
    }

    public void setCpaBtnText(String cpaBtnText) {
        this.cpaBtnText = cpaBtnText;
    }

    public String getCpaImgLink() {
        return cpaImgLink;
    }

    public void setCpaImgLink(String cpaImgLink) {
        this.cpaImgLink = cpaImgLink;
    }
}
