package com.hms.user_service.service;

import com.hms.user_service.model.Room;
import com.hms.user_service.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomByNo(int roomNo) {
        return roomRepository.findByRoomNo(roomNo);
    }

    public Optional<Room> updateRoomStatus(int roomNo, boolean booked) {
        Optional<Room> room = roomRepository.findByRoomNo(roomNo);
        if (room.isPresent()) {
            Room updatedRoom = room.get();
            updatedRoom.setBooked(booked);
            return Optional.of(roomRepository.save(updatedRoom));
        }
        return Optional.empty();
    }
}
