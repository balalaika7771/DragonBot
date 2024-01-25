package i_zhendorenko.dragCaveBot.POJO;

import java.util.Objects;

public class Code {
    String url;
    String code;
    public Code(){
    }

    public Code(String url, String code) {
        this.url = url;
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Code)) return false;
        Code code1 = (Code) o;
        return Objects.equals(getUrl(), code1.getUrl()) && Objects.equals(code, code1.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), code);
    }

    public String getCode() {
        return code;
    }
    public String getSampleCode() {
        String res = new String(code);
        res = res.toLowerCase();
        res = res.replaceAll("9","g");
        res = res.replaceAll("0","o");
        res = res.replaceAll("6","b");
        res = res.replaceAll("1","i");
        return res;
    }
}
