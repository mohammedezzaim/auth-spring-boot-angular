package org.example.back.security.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ezzaim Mohammed
 **/
@Getter
@Setter
@Builder
@Entity
@Table(name = "role")
@EntityListeners(AuditingEntityListener.class)
public class GrantedAuthorityImpl implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String role;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<UserDetailsImpl> users;

    public GrantedAuthorityImpl() {
    }

    public GrantedAuthorityImpl(Integer id, String role, LocalDateTime createdDate, LocalDateTime lastModifiedDate, List<UserDetailsImpl> users) {
        this.id = id;
        this.role = role;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.users = users;
    }

    public GrantedAuthorityImpl(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof GrantedAuthorityImpl) {
            GrantedAuthorityImpl sga = (GrantedAuthorityImpl)obj;
            return this.role.equals(sga.getAuthority());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}
