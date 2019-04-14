/*
Date: 04/11,2019, 15:00
*/
package com.fq.controller;

import com.fq.async.EventType;
import com.fq.model.*;
import com.fq.service.*;
import com.fq.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    @Autowired
    private AgreementService agreementService;
    @Autowired
    private FollowService followService;
    @Autowired
    private EventProducerService eventProducerService;

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

        if (questionService.addQuestion(question) > 0) {
            eventProducerService.creatEventModel(EventType.QUESTION_ADD, userid, EntityType.ENTITY_QUESTION, new HashMap<String, String>());
            return WendaUtil.getJSONString(0);
        } else
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

            if (sessionHolder.getUser() == null)
                vo.set("liked", 0);
            else
                vo.set("liked", agreementService.getAgreeStatus(sessionHolder.getUser().getId(), comment.getId(), EntityType.ENTITY_COMMENT));
            vo.set("likeCount", agreementService.getAgreementCount(comment.getId(), EntityType.ENTITY_COMMENT));

            vo.set("user", userService.getUserById(comment.getUserId()));
            comments.add(vo);
        }
        model.addAttribute("comments", comments);

        // 获取所有关注了该问题的用户
        List<ViewObject> followUsers = new ArrayList<>();
        List<Follow> followsList = followService.getFollowers(qid, EntityType.ENTITY_QUESTION);
        for (Follow follow : followsList) {
            ViewObject vo = new ViewObject();
            User user = userService.getUserById(follow.getUserId());
            if (user == null) continue;
            vo.set("name", user.getName());
            vo.set("headUrl", user.getHeadUrl());
            vo.set("id", user.getId());
            followUsers.add(vo);
        }
        model.addAttribute("followUsers", followUsers);

        // 判断当前用户是否关注
        if (sessionHolder.getUser() == null) {
            model.addAttribute("followed", false);
        } else {
            boolean status = followService.isFollow(sessionHolder.getUser().getId(), qid, EntityType.ENTITY_QUESTION);
            model.addAttribute("followed", status);
        }

        return "detail";
    }
}
