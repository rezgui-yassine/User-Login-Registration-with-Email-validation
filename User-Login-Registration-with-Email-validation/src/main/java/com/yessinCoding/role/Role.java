package com.yessinCoding.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yessinCoding.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    //JsonIgnore is used to ignore the users field when the role object is returned as a response
    private List<User>users;
    @CreatedDate
    @Column(updatable = false , nullable = false)
    private LocalDateTime createdAtDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;


}
