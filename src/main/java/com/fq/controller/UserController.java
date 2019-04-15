/*
Date: 04/11,2019, 11:03
*/
package com.fq.controller;

import com.fq.model.*;
import com.fq.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private FollowService followService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionHolder sessionHolder;
    @Autowired
    private AgreementService agreementService;

    @RequestMapping(value = {"/user/{id}"}, method = RequestMethod.GET)
    public String userDetial(Model model, @PathVariable int id, @RequestParam(defaultValue = "0") int page) {
        User user = userService.getUserById(id);
        if (user == null)
            return "redirect:/";

        ViewObject userviewObject = new ViewObject();
        userviewObject.set("user", user);
        userviewObject.set("commentCount", commentService.getCommentcountByUserId(user.getId()));
        userviewObject.set("followerCount", followService.getFollowersCount(id, EntityType.ENTITY_USER));
        userviewObject.set("followeeCount", followService.getFolloweesCount(id, EntityType.ENTITY_USER));
        List<Integer> commentIds = commentService.selectCommentIdsByUserId(user.getId());

        int agreementCount = 0;
        if (commentIds.size() != 0) {
            agreementCount = agreementService.getAgreementCountByUserIds(commentIds, EntityType.ENTITY_COMMENT);
        }
        userviewObject.set("agreementCount", agreementCount);
        userviewObject.set("followed", followService.isFollow(sessionHolder.getUser().getId(), id, EntityType.ENTITY_USER));

        model.addAttribute("profileUser", userviewObject);

        List<Question> latestQuestions = questionService.getLatestQuestions(user.getId(), Integer.valueOf(page) * 10, 10);
        List<ViewObject> questionvos = new ArrayList<>();
        for (Question q : latestQuestions) {
            ViewObject vo = new ViewObject();
            vo.set("question", q);
            vo.set("user", user);
            vo.set("followCount", followService.getFollowersCount(q.getId(), EntityType.ENTITY_QUESTION));
            questionvos.add(vo);
        }
        model.addAttribute("vos", questionvos);

        return "profile";
    }
}
