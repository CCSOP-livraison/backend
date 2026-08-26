package CCSOP.Livraison.repository;

import CCSOP.Livraison.entities.User;


public interface UserRepository{
    User findByemail(String email);

    void save(User user);
}
