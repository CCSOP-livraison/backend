package CCSOP.Livraison.repository;

import CCSOP.Livraison.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByemail(String email);
}
