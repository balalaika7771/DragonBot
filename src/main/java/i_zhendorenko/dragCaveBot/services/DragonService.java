package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.models.CookieAuth;
import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.repositories.DragonRepository;
import javassist.NotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DragonService {
    private final DragonRepository dragonRepository;

    public DragonService(DragonRepository dragonRepository) {
        this.dragonRepository = dragonRepository;
    }

    public Optional<Dragon> getDragonById(int dragonId){
       return dragonRepository.findById(dragonId);
    }
    public Optional<List<Dragon>> findByDescription(String description){
        return  dragonRepository.findByDescription(description);

    }


    public Optional<Dragon> findByName(String name) throws NotFoundException,IllegalStateException {
        Optional<List<Dragon>> dragons = dragonRepository.findByName(name);
        if(dragons.isEmpty()){
            return Optional.empty();
        }
        if(dragons.get().isEmpty()){
            return Optional.empty();
        }
        if (dragons.get().size() > 1){
            throw new IllegalStateException("More than one dragon by one name");
        }
        List<Dragon> dragonList = dragons.get();
        return Optional.of(dragonList.get(0));
    }


    public Dragon addDragon(Dragon dragon) {
        return dragonRepository.save(dragon);
    }

    public List<Dragon> getAllDragons() {
        return dragonRepository.findAll();
    }



}
