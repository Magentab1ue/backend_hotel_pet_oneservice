package com.example.lovelypet.business;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.Room;
import com.example.lovelypet.entity.RoomType;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.HotelException;
import com.example.lovelypet.exception.RoomException;
import com.example.lovelypet.model.RoomRequest;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.RoomService;
import com.example.lovelypet.service.RoomTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomBusiness {

    private final RoomService roomService;

    private final RoomTypeService roomTypeService;

    private final HotelService hotelService;

    public RoomBusiness(RoomService roomService, RoomTypeService roomTypeService, HotelService hotelService) {
        this.roomService = roomService;
        this.roomTypeService = roomTypeService;
        this.hotelService = hotelService;
    }

    public String addRoom(RoomRequest request) throws BaseException {
        int total = request.getTotal();
        String type = request.getType();

        Hotel hotelId = findHotelById(request.getHotelId());

        Optional<RoomType> optType = roomTypeService.findByName(type);
        if (optType.isEmpty()) {
            roomTypeService.create(type);
            optType = roomTypeService.findByName(type);
            if (optType.isEmpty()) {
                throw RoomException.notFoundRoomType();
            }
        }
        RoomType roomType = optType.get();

        for (int i = 1; i <= total; i++) {
            List<Room> opt = roomService.findByHotelId(hotelId);
            int roomNumber = opt.size() + 1;
            Room room = roomService.create(
                    hotelId,
                    roomType,
                    request.getPrice(),
                    request.getDetails(),
                    roomNumber
            );
        }

        return "successfully added  list room of" + total + " rooms has been.";
    }

    public String updateRoom(RoomRequest request) throws BaseException {

        String type = request.getType();

        Optional<RoomType> optType = roomTypeService.findByName(type);
        if (optType.isEmpty()) {
            roomTypeService.create(type);
            optType = roomTypeService.findByName(type);
            if (optType.isEmpty()) {
                throw RoomException.notFoundRoomType();
            }
        }
        RoomType roomType = optType.get();
        Room room = roomService.update(
                request.getId(),
                request.getDetails(),
                request.getPrice(),
                request.getStatus(),
                roomType
        );
        return "Successfully updated room NO." + room.getRoomNumber() + " information.";
    }

    public String deleteU(int id) throws BaseException {
        Optional<Room> opt = roomService.findById(id);
        if (opt.isEmpty()) {
            throw RoomException.notFound();
        }
        Room room = opt.get();
        roomService.deleteByIdU(String.valueOf(room.getId()));

        Optional<Room> confirm = roomService.findById(room.getId());
        if (confirm.isEmpty()) {
            return "Delete room number" + room.getRoomNumber() + "success";
        } else {
            return "Delete room number" + room.getRoomNumber() + "fail";
        }
    }

    public Hotel findHotelById(int id) throws BaseException {
        Optional<Hotel> opt = hotelService.findById(id);
        if (opt.isEmpty()) {
            throw HotelException.notFound();
        }
        return opt.get();
    }

    public Optional<Room> findById(int id) {
        return roomService.findById(id);
    }
}


//    public User findUserById(int id) throws BaseException {
//
//        Optional<User> opt = userService.findById(id);
//        if (opt.isEmpty()) {
//            throw UserException.notFound();
//        }
//        User response = opt.get();
//        return response;
//    }


