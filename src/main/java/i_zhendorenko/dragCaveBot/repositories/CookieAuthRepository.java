package i_zhendorenko.dragCaveBot.repositories;

import i_zhendorenko.dragCaveBot.models.CookieAuth;
import i_zhendorenko.dragCaveBot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CookieAuthRepository extends JpaRepository<CookieAuth, Long> {
    Optional<CookieAuth> findTopByPersonOrderByIdDesc(Person person);
}
