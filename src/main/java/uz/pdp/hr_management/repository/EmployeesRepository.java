package uz.pdp.hr_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.hr_management.entity.Employees;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;
@RepositoryRestResource(path = "employee")
public interface EmployeesRepository extends JpaRepository<Employees, UUID> {
    Optional<Employees> findByEmailAndEmailCode(String email, String emailCode);
    boolean existsByEmail(String email);
    Optional<Employees> findByEmail(String email);
}
