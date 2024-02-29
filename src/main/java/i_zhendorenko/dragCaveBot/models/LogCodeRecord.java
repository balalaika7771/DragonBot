package i_zhendorenko.dragCaveBot.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
//@Table(name = "log_code_record")
public class LogCodeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "code")
    private String code;

    public LogCodeRecord() {
        this.dateTime = LocalDateTime.now();
    }

    public LogCodeRecord(Person person, String code) {
        this.person = person;
        this.code = code;
        this.dateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogCodeRecord)) return false;
        LogCodeRecord that = (LogCodeRecord) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDateTime(), that.getDateTime()) && Objects.equals(getPerson(), that.getPerson()) && Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateTime(), getPerson(), getCode());
    }

}
