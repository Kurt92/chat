package com.jm.chat.biz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    @Comment("채팅방 PK")
    private Long ChatRoomId;

    @Column(name = "account_id")
    @Comment("계정아이디")
    private String accountId;

    @Column(name = "room_nm")
    @Comment("방이름")
    private String roomNm;

    @Column(name = "create_dt")
    @Comment("생성일시")
    private LocalDateTime createDt;

    @PrePersist
    public void onCreate() {
        this.createDt = LocalDateTime.now();
    }
}

