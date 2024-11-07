package app.component;

import app.dto.EmployeeDto;
import app.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeTransformer {

    public EmployeeDto transform(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeName(employee.getName());
        employeeDto.setEmployeeId(employee.getId());
        employeeDto.setDepartmentName(employee.getDepartment().getName());
        employeeDto.setDepartmentId(employee.getDepartment().getId());
        return employeeDto;
    }

    public List<EmployeeDto> transform(List<Employee> employees) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        employees.forEach(employee -> employeeDtos.add(transform(employee)));
        return employeeDtos;
    }

}
