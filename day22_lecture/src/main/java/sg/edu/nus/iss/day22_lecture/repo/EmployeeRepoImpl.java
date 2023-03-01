package sg.edu.nus.iss.day22_lecture.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import sg.edu.nus.iss.day22_lecture.model.Dependant;
import sg.edu.nus.iss.day22_lecture.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepoImpl implements EmployeeRepo{

    @Autowired
    JdbcTemplate jdbcTemplate;

    String SQL_SELECT =  "select * from employee";
    String SQL_SELECT_EMPLOYEE_BY_ID = "select * from employee where id = ?";

    String SQL_SELECT_EMPLOYEE_WITH_DEPENDANTS = """
            select e.id emp_id, e.first_name, e.last_name, e.salary,
            d.id dep_id, d.dependant_name, d.relationship, d.birth_date
            from employee e inner join dependant d
            on e.id = d.employee_id
            """;
    String SQL_INSERT = "insert into employee (first_name, last_name, salary) values (?, ?, ?)";
    String SQL_UPDATE = "update employee set first_name = ?, last_name = ?, salary = ? where id = ?";
    String SQL_DELETE = "delete from employee where id = ?";

    @Override
    public List<Employee> retrieveEmployeeList() {

        return jdbcTemplate.query(SQL_SELECT_EMPLOYEE_WITH_DEPENDANTS, new ResultSetExtractor<List<Employee>>() {
            @Override
            public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Employee> employees = new ArrayList<Employee>();

                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("emp_id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setSalary(rs.getInt("salary"));
                    emp.setDependants(new ArrayList<Dependant>());

                    Dependant dep = new Dependant();
                    dep.setId(rs.getInt("dep_id"));
                    dep.setDependantName(rs.getString("dependant_name"));
                    dep.setRelationship(rs.getString("relationship"));
                    dep.setBirthDate(rs.getDate("birth_date"));

                    if (employees.size() == 0) {
                        emp.getDependants().add(dep);
                        employees.add(emp);
                    } else {
                        List<Employee> eList = employees.stream()
                                .filter(e -> e.getId() == emp.getId())
                                .collect(Collectors.toList());

                        if (eList.size() == 0) {
                            emp.getDependants().add(dep);
                            employees.add(emp);
                        } else {
                            int idx = employees.indexOf(eList.get(0));

                            if (idx >= 0) {
                                employees.get(idx).getDependants().add(dep);
                            }
                        }
                    }
                }
                return employees;
            }
        });
    }

    @Override
    public int save(Employee employee) {
        int saved = 0;

        saved = jdbcTemplate.update(SQL_INSERT, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setInt(3, employee.getSalary());
            }
        });

        return saved;
    }

    @Override
    public int update(Employee employee) {
        int updated = 0;

        updated = jdbcTemplate.update(SQL_UPDATE, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setInt(3, employee.getSalary());
                ps.setInt(4, employee.getId());
            }
        });

        return updated;
    }

    @Override
    public int deleteById(Integer id) {
        int deleted = 0;

        deleted = jdbcTemplate.update(SQL_DELETE, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
        });

        return deleted;
    }
}
