package com.example.lovelypet.service;

import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public User create(
            String userName,
            String passWord,
            String name,
            String email,
            String phoneNumber,
            String token,
            Date tokenExpireDate
    ) throws BaseException {

        //validate
        if (Objects.isNull(email)) {
            //throw error email null
            throw UserException.createEmailNull();
        }

        if (Objects.isNull(passWord)) {
            //throw error password null
            throw UserException.createPasswordNull();
        }

        if (Objects.isNull(name)) {
            //throw error name null
            throw UserException.createNameNull();
        }

        if (Objects.isNull(userName)) {
            throw UserException.createUserNameNull();
        }

        if (Objects.isNull(phoneNumber)) {
            throw UserException.createPhoneNumberNull();
        }

        //verify
        if (repository.existsByEmail(email)) {
            //throw error email duplicated
            throw UserException.createEmailDuplicated();
        }
        if (repository.existsByUserName(userName)) {
            //throw error email duplicated
            throw UserException.createUserNameDuplicated();
        }

        //save
        else {
            User entity = new User();

            entity.setUserName(userName);
            entity.setPassWord(passwordEncoder.encode(passWord));
            entity.setName(name);
            entity.setEmail(email);
            entity.setPhoneNumber(phoneNumber);
            entity.setToken(token);
            entity.setTokenExpire(tokenExpireDate);
            return repository.save(entity);
        }
    }


    public Optional<User> findById(int idU) throws BaseException {
        return repository.findById(idU);

    }

    public Optional<User> findByEmail(String email) throws BaseException {
        return repository.findByEmail(email);

    }

    public Optional<User> findByToken(String token) throws BaseException {
        return repository.findByToken(token);

    }


    public Optional<User> findLog(String userName) throws BaseException {
        return repository.findByUserName(userName);

    }


    public Optional<User> findByDateDelete(Date date) {
        return repository.findByDateDeleteAccount(date);
    }


    public User update(User user) throws BaseException {
        if (Objects.isNull(user)) {
            throw UserException.objectIsNull();
        }
        return repository.save(user);
    }

    public User resetPassword(User user, String newPassword) throws BaseException {
        if (Objects.isNull(user)) {
            throw UserException.objectIsNull();
        }
        if (Objects.isNull(newPassword)) {
            throw UserException.resetPasswordIsNullNewPassword();
        }
        user.setPassWord(passwordEncoder.encode(newPassword));
        return repository.save(user);
    }

    public void deleteByIdU(int idU) {
        repository.deleteById(idU);
    }

    public boolean matchPassword(String requestPass, String dataPass) {
        return passwordEncoder.matches(requestPass, dataPass);
    }
}
