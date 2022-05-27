package uz.pdp.hr_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.hr_management.entity.RoleNames;
import uz.pdp.hr_management.entity.Roles;

import java.util.Optional;
@RepositoryRestResource(path = "role")
public interface RoleRepository extends JpaRepository<Roles,Integer> {
    Roles findByRoleNames(RoleNames roleNames);
}
