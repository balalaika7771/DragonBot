package i_zhendorenko.dragCaveBot.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "cookieauth")
public class CookieAuth {
    public CookieAuth(){

    }
    public CookieAuth(Person person, List<String> cookieAuths){
        this.person = person;
        this.cookies = cookieAuths;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cookie_auth_cookies", joinColumns = @JoinColumn(name = "cookie_auth_id"))
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Column(name = "cookies")
    private List<String> cookies;
    @Column(name = "created_at")
    private Date createdAt;
    public Long getId() {
        return id;
    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<String> getCookies() {
        return cookies;
    }

    public void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }
}
