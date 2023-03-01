package sg.edu.nus.iss.day22_lecture.repo;

import sg.edu.nus.iss.day22_lecture.model.Room;

import java.util.List;

public interface RoomRepo {
    int count();

    // Create
    int save(Room room);

    // Read
    List<Room> findAll();

    // Read one record
    Room findById(Integer id);

    // Update
    int update(Room room);

    // Delete
    int deleteById(Integer id);
}
