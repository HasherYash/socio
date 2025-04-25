package com.socio.socio.service;

import com.socio.socio.dto.UserFollowerStatsDto;
import com.socio.socio.entity.User;
import com.socio.socio.entity.Role;
import com.socio.socio.repository.PostRepository;
import com.socio.socio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;

    public User createOrUpdateAdmin(User user) {
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<UserFollowerStatsDto> getUserStats() {
        return userRepository.getUsersGroupedByDateAndSortedByFollowers();
    }

    public List<Map<String, Object>> getGroupedReportedPosts() {
        List<Object[]> results = postRepository.getGroupedReportedPosts();
        List<Map<String, Object>> groupedReports = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> report = new HashMap<>();
            report.put("reportedBy", row[0]);
            report.put("createdAt", row[1]);
            report.put("fileType", row[2]);
            report.put("count", row[3]);
            groupedReports.add(report);
        }
        return groupedReports;
    }
}