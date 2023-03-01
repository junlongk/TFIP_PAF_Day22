package sg.edu.nus.iss.day22_lecture.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;

    private String firstName;

    private String lastName;

    private Integer salary;

    private List<Dependant> dependants;
}
