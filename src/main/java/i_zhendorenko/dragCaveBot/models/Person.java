package i_zhendorenko.dragCaveBot.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
    @Column(name = "abandon")
    private Boolean abandon;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<CoolCode> coolCodes;

    @OneToMany(mappedBy = "person")
    private List<CookieAuth> cookieAuths;

    @ManyToMany()
    @JoinTable(

            name = "person_dragon",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "dragon_id"))
    private List<Dragon> dragons;

    @OneToMany(mappedBy = "person")
    private List<LogDragonRecord> logDragonRecords;

    @OneToMany(mappedBy = "person")
    private List<LogCodeRecord> logCodeRecords;

    public Person() {
        dragons = new ArrayList<Dragon>();
    }

    public Person(String username, String password) {
        dragons = new ArrayList<Dragon>();
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<CoolCode> getCodes() {
        return coolCodes;
    }

    public void setCodes(List<CoolCode> coolCodes) {
        this.coolCodes = coolCodes;
    }

    public List<Dragon> getDragons() {
        return dragons;
    }

    public void setDragons(List<Dragon> dragons) {
        this.dragons = dragons;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}