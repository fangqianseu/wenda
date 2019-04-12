/*
Date: 04/12,2019, 08:51
*/
package com.fq.controller;

import com.fq.model.Message;
import com.fq.model.SessionHolder;
import com.fq.model.User;
import com.fq.model.ViewObject;
import com.fq.service.MessageService;
import com.fq.service.UserService;
import com.fq.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class MessageController {
    private static Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private SessionHolder sessionHolder;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList(Model model, @RequestParam(defaultValue = "0") String page) {
        List<ViewObject> vos = new ArrayList<>();
        List<Message> messages = messageService.getMessagesbyUserId(sessionHolder.getUser().getId(),
                Integer.valueOf(page) * 10, 10);

        for (Message message : messages) {
            ViewObject vo = new ViewObject();
            vo.set("message", message);
            vo.set("user", userService.getUserById(message.getFromId()));
            vo.set("unread", messageService.getMessageUnreadCount(sessionHolder.getUser().getId(), message.getConversationId()));
            vos.add(vo);
        }
        model.addAttribute("conversations", vos);
        return "letter";
    }

    @ResponseBody
    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    public String getConversationList(@RequestParam String toName, @RequestParam String content,
                                      HttpServletRequest httpServletRequest) {
        if (StringUtils.isBlank(toName) || StringUtils.isBlank(content)) {
            return WendaUtil.getJSONString(1, "用户名或者私信内容不能为空");
        }
        if (sessionHolder.getUser() == null) {
            return WendaUtil.getJSONString(999, "未登录");
        }

        User toUser = userService.getUserByName(toName);
        if (toUser == null) {
            return WendaUtil.getJSONString(1, "用户不存在");
        }

        Message message = new Message();
        message.setContent(content);
        message.setFromId(sessionHolder.getUser().getId());
        message.setToId(toUser.getId());
        message.setCreatedDate(new Date());
        message.setHasRead(0);
        messageService.addMessage(message);

        return WendaUtil.getJSONString(0);
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String msgDetail(Model model, @RequestParam(defaultValue = "0") String page,
                            @RequestParam String conversationId) {
        List<ViewObject> vos = new ArrayList<>();
        List<Message> messageList = messageService.getMessagesbyconversationId(conversationId, 10 * Integer.valueOf(page), 10);

        for (Message message : messageList) {
            if (sessionHolder.getUser().getId() == message.getToId())
                messageService.setCommentRead(message.getId(), 1);

            ViewObject vo = new ViewObject();
            vo.set("user", userService.getUserById(message.getFromId()));
            vo.set("message", message);
            // 区分 对话框的 左右
            if (message.getFromId() == sessionHolder.getUser().getId())
                vo.set("from", "1");
            vos.add(vo);
        }
        model.addAttribute("messages", vos);
        return "letterDetail";
    }
}
