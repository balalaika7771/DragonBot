package i_zhendorenko.dragCaveBot.DTO;

import i_zhendorenko.dragCaveBot.models.Dragon;

import java.util.Objects;

public class DragonDTO {


    private String name;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DragonDTO)) return false;
        DragonDTO dragonDTO = (DragonDTO) o;
        return Objects.equals(name, dragonDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public DragonDTO(String name) {
        this.name = name;
    }
    public DragonDTO(Dragon dragon) {
        this.name = dragon.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
