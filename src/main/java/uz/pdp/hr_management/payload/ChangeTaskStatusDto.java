package uz.pdp.hr_management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTaskStatusDto {
    private Integer taskStatusId;
}
