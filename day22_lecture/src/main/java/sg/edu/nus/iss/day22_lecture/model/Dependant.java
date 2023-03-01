package sg.edu.nus.iss.day22_lecture.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependant {
    private Integer id;

    private String dependantName;

    private String relationship;

    private Date birthDate;

    // private Employee employee;
}
