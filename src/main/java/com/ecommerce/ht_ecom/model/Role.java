package com.ecommerce.ht_ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "role_name")
    })
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_name")
    private AppRole roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
    public Role(AppRole roleName) {
        this.roleName = roleName;
    }
}
