package sg.edu.nus.iss.day22_lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.day22_lecture.model.Room;
import sg.edu.nus.iss.day22_lecture.service.RoomService;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/rooms")
@RestController
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping("/count")
    public Integer getRoomCount() {
        Integer roomCount = roomService.count();
        return roomCount;
    }

    @GetMapping("/")
    public ResponseEntity<List<Room>>  retrieveAllRooms() {
        List<Room> rooms = new ArrayList<Room>();
        rooms = roomService.findAll();

        if (rooms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> retrieveRoomById(@PathVariable("id") int id) {
        Room room = roomService.findById(id);

        if (room == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Integer> createRoom(@RequestBody Room room) {
        int result = roomService.save(room);

        if (result >= 1)
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/")
    public ResponseEntity<Integer> updateRoom(@RequestBody Room room) {
        int updated = roomService.update(room);

        if (updated >= 1)
            return new ResponseEntity<>(updated, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteRoom(@PathVariable("id") Integer id) {
        int deleted = roomService.deleteById(id);

        if (deleted >= 1)
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        else
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
    }
}
