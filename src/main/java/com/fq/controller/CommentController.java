/*
Date: 04/11,2019, 15:42
*/
package com.fq.controller;

import com.fq.model.Comment;
import com.fq.model.EntityType;
import com.fq.model.SessionHolder;
import com.fq.service.CommentService;
import com.fq.service.QuestionService;
import com.fq.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    private SessionHolder sessionHolder;
    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String commentAdd(@RequestParam int questionId, @RequestParam String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setEntityType(EntityType.ENTITY_QUESTION);
        comment.setEntityId(questionId);
        int userId = 0;
        if (sessionHolder.getUser() == null)
            userId = WendaUtil.ANONYMOUS_USERID;
        else
            userId = sessionHolder.getUser().getId();
        comment.setUserId(userId);
        comment.setStatus(0);

        commentService.commitAdd(comment);
        questionService.updateComment(questionId, commentService.getCommentcount(questionId, EntityType.ENTITY_QUESTION));

        return "redirect:/question/" + questionId;
    }
}
