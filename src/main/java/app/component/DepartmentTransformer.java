package app.component;

import app.dto.DepartmentDto;
import app.entity.Department;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentTransformer {

    public DepartmentDto transform(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName(department.getName());
        departmentDto.setDepartmentId(department.getId());
        department.getEmployees().forEach(employee -> departmentDto.getEmployeeIds().add(employee.getId()));
        return departmentDto;
    }
    public List<DepartmentDto> transform(List<Department> departments) {
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        departments.forEach(department -> departmentDtos.add(transform(department)));
        return departmentDtos;
    }

}
