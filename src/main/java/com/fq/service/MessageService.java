/*
Date: 04/12,2019, 08:45
*/
package com.fq.service;

import com.fq.dao.MessageDao;
import com.fq.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private SensitiveService sensitiveService;

    public int addMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDao.addMessage(message);
    }

    public List<Message> getMessagesbyconversationId(String conversationId, int offset, int limit) {
        return messageDao.getMessagebyconversationId(conversationId, offset, limit);
    }

    public List<Message> getMessagesbyUserId(int userId, int offset, int limit) {
        return messageDao.getConversationListByUserId(userId, offset, limit);
    }

    public int getMessageUnreadCount(int userId, String conversationId) {
        return messageDao.getMessageUnreadCount(userId, conversationId);
    }

    public void setCommentRead(int messageId, int status) {
        messageDao.updateUnread(messageId, status);
    }
}
