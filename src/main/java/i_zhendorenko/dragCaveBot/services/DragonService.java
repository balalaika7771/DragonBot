package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.models.CookieAuth;
import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.repositories.DragonRepository;
import javassist.NotFoundException;
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
    public Dragon findByDescription(String description) throws NotFoundException,IllegalStateException {
        Optional<List<Dragon>> dragons = dragonRepository.findByDescription(description);
        if(dragons.isEmpty()){
            throw new NotFoundException("Not found dragon");
        }
        if (dragons.get().size() > 1){
            throw new IllegalStateException("More than one dragon by one description");
        }
        List<Dragon> dragonList = dragons.get();
        return dragonList.get(0);
    }

    public Dragon addDragon(String name) {
        Dragon dragon = new Dragon(name);
        return dragonRepository.save(dragon);
    }
    public List<Dragon> getAllDragons() {
        return dragonRepository.findAll();
    }
    public void addDescriptionToDragon(int dragonId, String description) {
        Dragon dragon = dragonRepository.findById(dragonId)
                .orElseThrow(() -> new RuntimeException("Dragon not found"));
        dragon.getDescription().add(description);
        dragonRepository.save(dragon);
    }

}
