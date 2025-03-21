package com.yessinCoding.entity;

    import com.yessinCoding.role.Role;
    import jakarta.persistence.*;
    import lombok.*;
    import org.springframework.data.annotation.CreatedDate;
    import org.springframework.data.annotation.LastModifiedDate;
    import org.springframework.data.jpa.domain.support.AuditingEntityListener;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.security.Principal;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.Collection;
    import java.util.List;
    import java.util.stream.Collectors;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Entity
    @Table(name = "_user")
    @EntityListeners(AuditingEntityListener.class)
    public class User implements UserDetails, Principal {

        @Id
        @GeneratedValue
        private Integer id;

        @Column(nullable = false) // Ensure firstName is not null
        private String firstName;

        @Column(nullable = false) // Ensure lastName is not null
        private String lastName;

        private LocalDate dateOfBirth;

        @Column(unique = true, nullable = false) // Ensure email is unique and not null
        private String email;

        @Column(nullable = false) // Ensure password is not null
        private String password;

        @Column(nullable = false) // Ensure accountLocked is not null
        private boolean accountLocked;

        @Column(nullable = false) // Ensure enabled is not null
        private boolean enabled;

        @CreatedDate
        @Column(updatable = false, nullable = false) // Ensure createdAt is not null and not updatable
        private LocalDateTime createdAt;

        @LastModifiedDate
        @Column(insertable = false) // Ensure lastModified is not inserted manually
        private LocalDateTime lastModified;

        @ManyToMany(fetch = FetchType.EAGER) // Eagerly fetch roles
        @JoinTable(
                name = "user_roles", // Name of the join table
                joinColumns = @JoinColumn(name = "user_id"), // Foreign key for User
                inverseJoinColumns = @JoinColumn(name = "role_id") // Foreign key for Role
        )
        private List<Role> roles;

        @Override
        public String getName() {
            return email; // Return email as the name for Principal
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            // Convert roles to GrantedAuthority objects
            return this.roles
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
        }

        @Override
        public String getPassword() {
            return password; // Return the password for authentication
        }

        @Override
        public String getUsername() {
            return email; // Return the email as the username
        }

        @Override
        public boolean isAccountNonExpired() {
            return true; // Account never expires
        }

        @Override
        public boolean isAccountNonLocked() {
            return !accountLocked; // Account is locked if accountLocked is true
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true; // Credentials never expire
        }

        @Override
        public boolean isEnabled() {
            return enabled; // Account is enabled if enabled is true
        }

        public String fullName() {
            return firstName + " " + lastName; // Return full name
        }
    }