package uz.pdp.hr_management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDto {
    private UUID employeeId;
    private double amount;
}
