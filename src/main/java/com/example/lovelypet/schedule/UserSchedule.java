package com.example.lovelypet.schedule;

import com.example.lovelypet.business.HotelBusiness;
import com.example.lovelypet.business.UserBusiness;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
@Log4j2
public class UserSchedule {

    private final UserBusiness userBusiness;

    private final UserService userService;

    public UserSchedule(UserBusiness userBusiness, UserService userService) {
        this.userBusiness = userBusiness;

        this.userService = userService;
    }

    //1 => second
    //2 => minute
    //3 => hour
    //4 => day
    //5 => year
//
//    /**
//     * Every minute (UTC Time)
//     */
//    @Scheduled(cron = "0 * * * * *")
//    public void testEveryMinute() {
//        log.info("Hello,What's up?");
//    }
//
//    /**
//     * Every day at 00.00 (Thai Time)
//     */

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Bangkok")
    public void deleteUserAccountEveryMidNight() throws BaseException {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        Date dateDelete = calendar.getTime();
        Optional<User> opt = userService.findByDateDelete(dateDelete);
        if (opt.isEmpty()) {
            return;
        }
        userBusiness.deleteAccount(opt.get().getId());
    }
}
