/*
Date: 04/13,2019, 11:00
*/
package com.fq.async.handler;

import com.fq.async.EventHandler;
import com.fq.async.EventModel;
import com.fq.async.EventType;
import com.fq.model.Message;
import com.fq.service.MessageService;
import com.fq.service.UserService;
import com.fq.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AgreementHandler implements EventHandler {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();

        message.setToId(Integer.valueOf(model.getEventDetial("toUser")));
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setContent(String.format("用户 %s 赞同了你关于问题 (%s) 的回答, 快去看一看吧",
                model.getEventDetial("userName"),
                "http:/127.0.0.1:8080/question/" + model.getEventDetial("qusetionId")));
        message.setHasRead(0);
        message.setCreatedDate(model.getDate());

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.AGREEMENT);
    }
}
