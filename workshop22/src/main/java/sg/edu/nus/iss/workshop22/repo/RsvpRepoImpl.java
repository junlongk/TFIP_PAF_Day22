package sg.edu.nus.iss.workshop22.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.nus.iss.workshop22.model.RSVP;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class RsvpRepoImpl {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_ALL = """
            select * from rsvp
    """;

    private final String SQL_FIND_BY_NAME = """
            select * from rsvp where full_name like '%' ? '%'
    """;

    //    private final String insertSQL = "insert into rsvp (full_name, email, phone, confirmation_date, comments)" +
    //            "values (?, ?, ?, ?, ?)";

    private final String SQL_UPSERT = """
            insert ignore into rsvp (full_name, email, phone, confirmation_date, comments)
            values (?, ?, ?, ?, ?)
    """;

    private final String SQL_UPDATE = """
            update rsvp set full_name = ?, phone = ?, confirmation_date = ?,
            comments = ? where email = ?
    """;

    private final String SQL_COUNT = """
            select count(*) as cnt from rsvp
    """;

    public List<RSVP> findAll() {
        List<RSVP> resultList;
        resultList = jdbcTemplate.query(SQL_FIND_ALL, BeanPropertyRowMapper.newInstance(RSVP.class));
        return resultList;
    }

    public Optional<List<RSVP>> findByName(String name) {
        List<RSVP> rsvpList = jdbcTemplate.query(SQL_FIND_BY_NAME, BeanPropertyRowMapper.newInstance(RSVP.class), name);

        System.out.printf("REPO findByName %s >>>\n%s\n", name, rsvpList);

        if (rsvpList.isEmpty())
            return Optional.empty();
        else
            return Optional.of(rsvpList);
    }

    //    public Boolean save(RSVP rsvp) {
    //        Integer result = jdbcTemplate.update(insertSQL, rsvp.getFullName(), rsvp.getEmail(), rsvp.getPhone(),
    //                rsvp.getConfirmationDate(), rsvp.getComments());
    //        return result > 0;
    //    }


    //    insert ignore into rsvp (full_name, email, phone, confirmation_date, comments)
    //    values (?, ?, ?, ?, ?)
    public boolean upsert(RSVP rsvp) {
        int result = jdbcTemplate.update(SQL_UPSERT, rsvp.getFullName(), rsvp.getEmail(), rsvp.getPhone(),
                rsvp.getConfirmationDate(), rsvp.getComments());
        return result > 0;
    }

    //      update rsvp set full_name = ?, phone = ?, confirmation_date = ?,
    //      comments = ? where email = ?
    public boolean update(RSVP rsvp) {
        int result = jdbcTemplate.update(SQL_UPDATE, rsvp.getFullName(), rsvp.getPhone(),
                rsvp.getConfirmationDate(), rsvp.getComments(),rsvp.getEmail());
        return result > 0;
    }

    public int countAll() {
        return jdbcTemplate.queryForObject(SQL_COUNT, Integer.class);
    }

    @Transactional
    public int[] batchInsert(List<RSVP> rsvp) {
        return jdbcTemplate.batchUpdate(SQL_UPSERT, new BatchPreparedStatementSetter() {

            // rsvp.getFullName(), rsvp.getEmail, rsvp.getPhone(),
            //      rsvp.getConfirmationDate(), rsvp.getComments()
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, rsvp.get(i).getFullName());
                ps.setString(2, rsvp.get(i).getEmail());
                ps.setInt(3, rsvp.get(i).getPhone());
                ps.setDate(4, rsvp.get(i).getConfirmationDate());
                ps.setString(5, rsvp.get(i).getComments());
            }

            public int getBatchSize() {
                return rsvp.size();
            }
        });
    }

}
