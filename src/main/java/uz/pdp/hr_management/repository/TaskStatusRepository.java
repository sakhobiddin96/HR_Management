package uz.pdp.hr_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hr_management.entity.TaskStatus;
import uz.pdp.hr_management.entity.TaskStatusNames;

import java.util.UUID;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
    TaskStatus findByTaskStatus(TaskStatusNames taskStatus);
}
