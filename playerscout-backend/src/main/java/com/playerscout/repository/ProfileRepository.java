package com.playerscout.repository;

import com.playerscout.model.Profile;
import com.playerscout.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * ProfileRepository provides CRUD operations for Profile entities.
 * Spring Boot generates the implementation automatically.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Custom query: find a profile by the user who owns it
    Optional<Profile> findByUser(User user);
    // You can add more custom queries as needed
}
