package br.com.microservices.statefulauthapi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.microservices.statefulauthapi.core.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
