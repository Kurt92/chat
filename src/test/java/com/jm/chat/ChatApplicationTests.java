package com.jm.chat;

import com.jm.chat.biz.entity.ChatMsg;
import com.jm.chat.biz.entity.ChatMsgRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ChatApplicationTests {

    private final ChatMsgRepository chatMsgRepository;


    @Test
    @Transactional
    //N + 1 유도
    void testFindAllAndTriggerNPlusOne() {
        List<ChatMsg> messages = chatMsgRepository.findAll();


        for (ChatMsg msg : messages) {
            System.out.println("msg.id = " + msg.getChatId() +
                    ", room.id = " + msg.getChatRoomId().getChatRoomId());
        }
    }



}
