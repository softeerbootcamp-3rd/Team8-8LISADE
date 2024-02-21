package com.lisade.togeduck.entity;

import com.lisade.togeduck.entity.enums.AuthorityType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "authority", nullable = false)
    private AuthorityType authority;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserChatRoom> UserChatRooms;

    @Builder
    public User(Long id, String userId, String password, String nickname, String email,
        AuthorityType authority) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.authority = authority;
    }
}
