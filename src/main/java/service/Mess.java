package service;

public class Mess {
    private String content;
    private String cauLenh;
    private int kieuCauLenh;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCauLenh() {
        return cauLenh;
    }

    public void setCauLenh(String cauLenh) {
        this.cauLenh = cauLenh;
    }

    public Mess(String content, String cauLenh,int kieuCauLenh) {
        this.content = content;
        this.cauLenh = cauLenh;
        this.kieuCauLenh = kieuCauLenh;
    }

    public int getKieuCauLenh() {
        return kieuCauLenh;
    }

    public void setKieuCauLenh(int kieuCauLenh) {
        this.kieuCauLenh = kieuCauLenh;
    }

    public Mess() {

    }
}
