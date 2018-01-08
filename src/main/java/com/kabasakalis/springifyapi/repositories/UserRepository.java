
package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.SpringifyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SpringifyUser, Long> {
    SpringifyUser findByUsername(String username);
}
