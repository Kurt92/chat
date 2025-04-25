package com.jm.chat.biz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_msg")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMsg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    @Comment("채팅 PK")
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom ChatRoom;

    @Column(name = "user_id")
    @Comment("유저 ID")
    private Long userId;

    @Column(name = "account_id")
    @Comment("계정 ID")
    private String accountId;

    @Column(name = "content")
    @Comment("내용")
    private String content;

    @Column(name = "is_read")
    @Comment("읽음 여부")
    private Boolean isRead;

    @Column(name = "is_last_msg")
    @Comment("마지막 메시지 여부")
    private Boolean isLastMsg;

    @Column(name = "create_dt")
    @Comment("생성일시")
    private LocalDateTime createDt;

    @PrePersist
    public void onCreate() {
        this.createDt = LocalDateTime.now();
    }
}

