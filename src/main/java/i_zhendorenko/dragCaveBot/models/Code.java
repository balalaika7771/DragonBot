package i_zhendorenko.dragCaveBot.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Code")
public class Code {
    public Code(){}
    public Code(String code, Person person){
        this.code = code;
        this.person = person;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "code не должно быть пустым")
    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}
