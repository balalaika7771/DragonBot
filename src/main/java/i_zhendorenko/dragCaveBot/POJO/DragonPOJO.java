package i_zhendorenko.dragCaveBot.POJO;

import i_zhendorenko.dragCaveBot.models.Dragon;

import java.util.Objects;

public class DragonPOJO {
    String url;
    String name;


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DragonPOJO)) return false;
        DragonPOJO that = (DragonPOJO) o;
        return Objects.equals(getUrl(), that.getUrl()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getName());
    }

    public DragonPOJO() {
    }

    public DragonPOJO(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
