package sg.edu.nus.iss.day22_lecture.repo;

import sg.edu.nus.iss.day22_lecture.model.Employee;

import java.util.List;

public interface EmployeeRepo {

    List<Employee> retrieveEmployeeList();

    // Create
    int save(Employee employee);

    // Update
    int update(Employee employee);

    // Delete
    int deleteById(Integer id);
}
