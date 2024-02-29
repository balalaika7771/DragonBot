package i_zhendorenko.dragCaveBot.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_record")
public class LogDragonRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "dragon_id")
    private Dragon dragon;

    public LogDragonRecord() {
        this.dateTime = LocalDateTime.now();
    }

    public LogDragonRecord(Person person, Dragon dragon) {
        this.person = person;
        this.dragon = dragon;
        this.dateTime = LocalDateTime.now();
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }
    // Геттеры и сеттеры
}
