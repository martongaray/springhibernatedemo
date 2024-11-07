package app.dto;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDto {

    private String departmentName;
    private Long departmentId;
    private List<Long> employeeIds = new ArrayList<>();

    public DepartmentDto() {
    }

    public DepartmentDto(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

}
