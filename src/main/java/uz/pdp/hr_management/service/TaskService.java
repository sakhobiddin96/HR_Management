package uz.pdp.hr_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.hr_management.entity.Employees;
import uz.pdp.hr_management.entity.Task;
import uz.pdp.hr_management.entity.TaskStatus;
import uz.pdp.hr_management.entity.TaskStatusNames;
import uz.pdp.hr_management.payload.ApiResponse;
import uz.pdp.hr_management.payload.TaskDto;
import uz.pdp.hr_management.repository.EmployeesRepository;
import uz.pdp.hr_management.repository.TaskRepository;
import uz.pdp.hr_management.repository.TaskStatusRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    TaskStatusRepository taskStatusRepository;
    @Autowired
    AuthService authService;

    public ApiResponse assignTask(TaskDto taskDto) {
        Optional<Employees> optionalEmployees = employeesRepository.findById(taskDto.getEmployeeId());
        if (optionalEmployees.isPresent()) {
            Employees employee = optionalEmployees.get();
            Task task = new Task();
            task.setName(taskDto.getName());
            task.setTaskDefinition(taskDto.getTaskDefinition());
            task.setDeadline(taskDto.getDeadline());
            task.setEmployee(employee);
            task.setTaskStatus(taskStatusRepository.findByTaskStatus(TaskStatusNames.WAITING_TO_RUN));

//            Who created task?
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Employees whoCreatedTask = (Employees) authentication.getPrincipal();
            UUID id = whoCreatedTask.getId();
            task.setCreatedBy(id);
            taskRepository.save(task);
            authService.sendEmail(employee.getEmail(),taskDto.getDeadline());
            return new ApiResponse("Task has been assigned to the employee",true);
        }
        return new ApiResponse("Employee not found",false);
    }

    public ApiResponse getAssignedTask(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employees employee = (Employees) authentication.getPrincipal();
        Optional<Task> optionalTask = taskRepository.findByEmployeeId(employee.getId());
        if (optionalTask.isPresent()){
            Task task = optionalTask.get();
            return new ApiResponse("You have a task(s)",true,task);
        }
        return new ApiResponse("You do not have any tasks to do",false);
    }
    public ApiResponse changeStatusOfTask(Integer taskStatusId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employees employees = (Employees) authentication.getPrincipal();
        Optional<Task> optionalTask = taskRepository.findByEmployeeId(employees.getId());
        if (optionalTask.isPresent()){
            Task task = optionalTask.get();
            Optional<TaskStatus> byId = taskStatusRepository.findById(taskStatusId);
            if (byId.isPresent()){
                TaskStatus taskStatus = byId.get();
                task.setTaskStatus(taskStatus);
                Optional<Employees> optionalEmployees = employeesRepository.findById(task.getCreatedBy());
                if (optionalEmployees.isPresent()) {
                    Employees employees1 = optionalEmployees.get();
                    authService.sendEmailAboutTaskStatus(employees1.getEmail(),employees.getFirstName(),taskStatus.getTaskStatus().toString());
                }
                return new ApiResponse("Task status changed",true);
            }
            return null;

        }
        return new ApiResponse("Task not found",false);
    }
}
