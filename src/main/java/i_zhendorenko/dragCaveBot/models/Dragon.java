package i_zhendorenko.dragCaveBot.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
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
    @CollectionTable(name = "dragon_habitat", joinColumns = @JoinColumn(name = "dragon_id"))
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Column(name = "habitat")
    private List<String> habitat;

    @NotEmpty(message = "Описание не должно быть пустым")
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "dragons")
    private List<Person> persons;

    @OneToMany(mappedBy = "dragon")
    private List<LogDragonRecord> logDragonRecords;

    public Dragon() {
        habitat = new ArrayList<String>();
    }
    public Dragon(String name) {
        habitat = new ArrayList<String>();
        this.name = name;
    }

    public void addHabitat(String _habitat){
        habitat.add(_habitat);
    }
    public void addHabitat(List<String> _habitat){
        habitat.addAll(_habitat);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<String> getHabitat() {
        return habitat;
    }

    public void setHabitat(List<String> habitat) {
        this.habitat = habitat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
