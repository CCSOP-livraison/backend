package CCSOP.Livraison.repository;

import CCSOP.Livraison.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Utilisez Optional<User> au lieu de User tout court
    Optional<User> findByEmail(String email);
}
