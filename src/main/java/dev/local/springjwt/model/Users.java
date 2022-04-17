package dev.local.springjwt.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false,unique = true)
    String username;
    @Column(nullable = false,unique = true)
    String email;
    @Column(nullable = false,unique = true)
    String phone;
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    @Column(nullable = false)
    String password;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
    },fetch=FetchType.EAGER)
    @JoinTable(name = "user_profil",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profil_id")
    )
    Collection<Profil> profils ;
}
