package CCSOP.Livraison.Service;

import CCSOP.Livraison.Entitie.Statut;
import CCSOP.Livraison.Repository.StatutRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatutService {

    private final StatutRepository statutRepository;

    public StatutService(StatutRepository statutRepository) {
        this.statutRepository = statutRepository;
    }

    public List<Statut> getAllStatus() {
        return statutRepository.findAll();
    }

    public Optional<Statut> getStatusById(Long id) {
        return statutRepository.findById(id);
    }

    public Optional<Statut> getStatusByName(String name) {
        return statutRepository.findByName(name);
    }
}
