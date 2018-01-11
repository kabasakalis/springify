
package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.domain.Role;
import com.kabasakalis.springifyapi.domain.SpringifyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SpringifyUser, Long> {
    SpringifyUser findByUsername(String username);

    Page<SpringifyUser> findAllByRoles(Role role, Pageable pageable);
}
