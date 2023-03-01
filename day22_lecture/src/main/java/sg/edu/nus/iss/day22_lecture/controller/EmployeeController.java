package sg.edu.nus.iss.day22_lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.day22_lecture.model.Employee;
import sg.edu.nus.iss.day22_lecture.repo.EmployeeRepoImpl;

import java.util.List;

@RequestMapping("/api/employees")
@RestController
public class EmployeeController {
    @Autowired
    EmployeeRepoImpl empRepo;

    @GetMapping("/")
    public List<Employee> retrieveEmployees() {
        List<Employee> employees = empRepo.retrieveEmployeeList();

        if (employees.isEmpty())
            return null;
        else
            return employees;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Integer> createEmployee(@RequestBody Employee employee) {
        int created = empRepo.save(employee);

        if (created >= 1)
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/")
    public ResponseEntity<Integer> updateEmployee(@RequestBody Employee employee) {
        int updated = empRepo.update(employee);

        if (updated >= 1)
            return new ResponseEntity<>(updated, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteEmployee(@PathVariable("id") Integer id) {
        int deleted = empRepo.deleteById(id);

        if (deleted >= 1)
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
