/*
Date: 04/11,2019, 15:00
*/
package com.fq.controller;

import com.fq.model.*;
import com.fq.service.CommentService;
import com.fq.service.QuestionService;
import com.fq.service.UserService;
import com.fq.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private SessionHolder sessionHolder;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    public String questionAdd(@RequestParam String title, @RequestParam String content) {
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setCreatedDate(new Date());
        question.setCommentCount(0);
        int userid = 0;
        if (sessionHolder.getUser() == null) {
            userid = WendaUtil.ANONYMOUS_USERID;
        }
        userid = sessionHolder.getUser().getId();
        question.setUserId(userid);

        if (questionService.addQuestion(question) > 0)
            return WendaUtil.getJSONString(0);
        else
            return WendaUtil.getJSONString(1, "失败");
    }

    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getQuestionById(qid);
        model.addAttribute("question", question);

        // 显示评论详情
        List<Comment> commentList = commentService.selectCommentByEntry(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<ViewObject>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);

            vo.set("liked", 11);
            vo.set("likeCount",12);

            vo.set("user", userService.getUserById(comment.getUserId()));
            comments.add(vo);
        }
        model.addAttribute("comments", comments);

        return "detail";
    }
}
