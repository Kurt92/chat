package com.jm.chat.biz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatDto {


    public static class Request {
        @Getter
        @Setter
        public static class ChatMsg {
            private Long chatRoomId;
            private Long senderId;
//            private String accountId;
            private String userName;
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

            @JsonProperty("lastMessageTime")
            public String getFormattedLastMessageTime() {
                LocalDate today = LocalDate.now();

                if (lastMessageTime == null)
                    return null;
                if (lastMessageTime.toLocalDate().isEqual(today))
                    return lastMessageTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                else
                    return lastMessageTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }
        }


        @Getter
        @Setter
        public static class ChatMsg {
            private Long chatRoomId;
            private Long senderId;
            private String userName;
            private String message;

        }
    }

}
