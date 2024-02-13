package i_zhendorenko.dragCaveBot.models;

import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.Person;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_record")
public class LogRecord {

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

    public LogRecord() {
        this.dateTime = LocalDateTime.now();
    }

    public LogRecord(Person person, Dragon dragon) {
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
