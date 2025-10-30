package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // ✅ ambil semua kamar available
    public List<Room> getAvailableRooms() {
        return roomRepository.findByIsAvailableTrue();
    }

    // ✅ ambil 1 kamar pertama berdasarkan tipe yang available
    public Room findFirstAvailableRoomByType(String roomType) {
        return roomRepository.findFirstByRoomTypeAndIsAvailableTrue(roomType);
    }

    // ✅ ubah status availability
    public void updateRoomAvailability(int roomNo, boolean isAvailable) {
        Room room = roomRepository.findById(roomNo).orElse(null);
        if (room != null) {
            room.setAvailable(isAvailable);
            roomRepository.save(room);
        }
    }

    public Room getRoomByRoomNo(int roomNo) {
        return roomRepository.findByRoomNo(roomNo);
    }

}
