package uz.pdp.hr_management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hr_management.entity.Employees;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @NotNull
    private String name;

    @NotNull
    private String taskDefinition;

    @NotNull
    private Date deadline;

    @NotNull
    private UUID employeeId;
}
