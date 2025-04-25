package com.socio.socio.dto;

import com.socio.socio.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {
    private String name;
    private String description;
    private User createdBy;
}