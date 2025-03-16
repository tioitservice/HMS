package com.hms.user_service.controller;

import com.hms.user_service.model.Room;
import com.hms.user_service.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/room")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        return ResponseEntity.ok(roomService.addRoom(room));
    }

    @GetMapping("/room/all")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/room/{roomNo}")
    public ResponseEntity<Room> getRoomByNo(@PathVariable int roomNo) {
        Optional<Room> room = roomService.getRoomByNo(roomNo);
        return room.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/room/update/{roomNo}")
    public ResponseEntity<Room> updateRoomStatus(@PathVariable int roomNo, @RequestParam boolean booked) {
        Optional<Room> updatedRoom = roomService.updateRoomStatus(roomNo, booked);
        return updatedRoom.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
