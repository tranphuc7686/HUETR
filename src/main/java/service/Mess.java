package service;

public class Mess {
    private String content;
    private String cauLenh;

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

    public Mess(String content, String cauLenh) {
        this.content = content;
        this.cauLenh = cauLenh;
    }
    public Mess() {

    }
}
