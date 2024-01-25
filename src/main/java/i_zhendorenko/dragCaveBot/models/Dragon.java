package i_zhendorenko.dragCaveBot.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "dragon")
public class Dragon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Column(name = "name")
    private String name;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dragon_description", joinColumns = @JoinColumn(name = "dragon_id"))
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Column(name = "description")
    private List<String> description;

    @ManyToMany(mappedBy = "dragons")
    private List<Person> persons;


    public Dragon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public Dragon() {
    }
}
