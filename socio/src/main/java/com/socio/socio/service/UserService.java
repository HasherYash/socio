package com.socio.socio.service;

import com.socio.socio.entity.User;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
//    List<User> getAllUsers();
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);

    void uploadUsers(MultipartFile file) throws Exception;

    Page<User> getAllUsers(Pageable pageable);
}