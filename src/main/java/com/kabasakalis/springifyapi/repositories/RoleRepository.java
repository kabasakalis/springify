
package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Role;
import com.kabasakalis.springifyapi.models.SpringifyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String name);
    Page<Role> findAllBySpringifyUsers(SpringifyUser user, Pageable pageable);
}
