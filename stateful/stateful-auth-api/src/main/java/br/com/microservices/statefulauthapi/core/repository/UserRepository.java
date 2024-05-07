package br.com.microservices.statefulauthapi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.microservices.statefulauthapi.core.model.User2;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User2, Integer> {

    Optional<User2> findByUsername(String username);
}
