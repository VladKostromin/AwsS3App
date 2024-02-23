package com.vladkostromin.awss3app.entity;

import com.vladkostromin.awss3app.enums.Role;
import com.vladkostromin.awss3app.enums.UserStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table("users")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserEntity extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private UserStatus status;
    private Role role;
    @Transient
    private List<EventEntity> events;

    @ToString.Include(name = "password")
    private String maskPassword() {
        return "********";
    }
}
