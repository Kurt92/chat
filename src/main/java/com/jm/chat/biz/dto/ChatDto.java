package com.jm.chat.biz.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ChatDto {


    public static class Request {
        @Getter
        @Setter
        public class ChatMsg {
            private String chatRoomId;
            private String sender;
            private String message;
        }

    }


    public static class Response {
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class ChatRoomList {
            private Long chatRoomId;
            private String roomNm;
            private String lastMessage;
            private LocalDateTime lastMessageTime;
            private Long unreadCount;
        }
    }
}
