package i_zhendorenko.dragCaveBot.repositories;

import i_zhendorenko.dragCaveBot.models.CoolCode;
import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DragonRepository extends JpaRepository<Dragon, Integer> {

    Optional<List<Dragon>> findByDescription(String description);
    Optional<List<Dragon>> findByName(String name);


}
