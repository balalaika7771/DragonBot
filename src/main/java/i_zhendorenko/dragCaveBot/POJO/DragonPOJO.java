package i_zhendorenko.dragCaveBot.POJO;

import i_zhendorenko.dragCaveBot.models.Dragon;

import java.util.Objects;

public class DragonPOJO {
    String url;


    Dragon dragon;

    @Override
    public String toString() {
        return dragon.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DragonPOJO)) return false;
        DragonPOJO that = (DragonPOJO) o;
        return Objects.equals(getUrl(), that.getUrl()) && Objects.equals(getDragon(), that.getDragon());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getDragon());
    }

    public DragonPOJO() {
    }

    public DragonPOJO(String url, Dragon dragon) {
        this.url = url;
        this.dragon = dragon;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Dragon getDragon() {
        return dragon;
    }

    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }
}
