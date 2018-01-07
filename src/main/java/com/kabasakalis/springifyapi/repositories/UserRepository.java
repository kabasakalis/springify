
package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
