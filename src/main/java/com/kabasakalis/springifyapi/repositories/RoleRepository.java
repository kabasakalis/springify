
package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
