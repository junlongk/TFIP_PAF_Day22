package sg.edu.nus.iss.day22_lecture.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import sg.edu.nus.iss.day22_lecture.model.Room;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoomRepoImpl implements RoomRepo{

    @Autowired
    JdbcTemplate jdbcTemplate;

    String SQL_COUNT = "select count(*) from room";
    String SQL_SELECT = "select * from room";
    String SQL_SELECT_BY_ID = "select * from room where id = ?";
    String SQL_INSERT = "insert into room (room_type, price) values (?, ?)";
    String SQL_UPDATE = "update room set room_type = ?, price = ? where id = ?";
    String SQL_DELETE = "delete from room where id = ?";

    @Override
    public int count() {
        Integer result = jdbcTemplate.queryForObject(SQL_COUNT, Integer.class);
        return result == null ? 0 : result;
    }

    @Override
    public int save(Room room) {
        int saved = jdbcTemplate.update(SQL_INSERT, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, room.getRoomType());
                ps.setInt(2, room.getPrice());
            }
        });
        return saved;
    }

    @Override
    public List<Room> findAll() {
        return jdbcTemplate.query(SQL_SELECT, BeanPropertyRowMapper.newInstance(Room.class));
    }

    @Override
    public Room findById(Integer id) {
        // "select * from room where id = ?"
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Room.class), id);
    }

    @Override
    public int update(Room room) {
        int updated = 0;

        updated = jdbcTemplate.update(SQL_UPDATE, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, room.getRoomType());
                ps.setInt(2, room.getPrice());
                ps.setInt(3, room.getId());
            }
        });
        return updated;
    }

    @Override
    public int deleteById(Integer id) {
        // "delete from room where id = ?"
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
