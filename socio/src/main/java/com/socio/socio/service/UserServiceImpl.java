package com.socio.socio.service;

import com.socio.socio.entity.Role;
import com.socio.socio.entity.User;
import com.socio.socio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User updatedUser) {
        User existing = getUserById(id);
        existing.setEmail(updatedUser.getEmail());
        existing.setPassword(updatedUser.getPassword());
        existing.setPrivateProfile(updatedUser.isPrivateProfile());
        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void uploadUsers(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        List<User> users = new ArrayList<>();

        if (filename != null && filename.endsWith(".csv")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                String[] fields = line.split(",");
                User user = new User();
                user.setEmail(fields[1].trim());
                user.setPassword(fields[2].trim());
                user.setRole(Role.USER);
                users.add(user);
            }
        } else {
            InputStream is = file.getInputStream();
            Workbook workbook = filename != null && filename.endsWith(".xlsx")
                    ? new XSSFWorkbook(is)
                    : new HSSFWorkbook(is);

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                User user = new User();
                user.setEmail(row.getCell(1).getStringCellValue());
                user.setPassword(row.getCell(2).getStringCellValue());
                user.setRole(Role.USER);
                users.add(user);
            }
        }

        userRepository.saveAll(users);
    }
}