/*
Date: 04/11,2019, 11:03
*/
package com.fq.controller;

import com.fq.model.Question;
import com.fq.model.SessionHolder;
import com.fq.model.User;
import com.fq.model.ViewObject;
import com.fq.service.CommentService;
import com.fq.service.QuestionService;
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

    @Autowired
    private SessionHolder sessionHolder;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = {"/user/{id}"}, method = RequestMethod.GET)
    public String userDetial(Model model, @PathVariable int id, @RequestParam(defaultValue = "0") int page) {
        User user = sessionHolder.getUser();

        ViewObject userviewObject = new ViewObject();
        userviewObject.set("user", user);
        userviewObject.set("commentCount", commentService.getCommentcountByUserId(user.getId()));
        model.addAttribute("profileUser", userviewObject);

        List<Question> latestQuestions = questionService.getLatestQuestions(user.getId(), Integer.valueOf(page) * 10, 10);
        List<ViewObject> questionvos = new ArrayList<>();
        for (Question q : latestQuestions) {
            ViewObject vo = new ViewObject();
            vo.set("question", q);
            vo.set("user", user);
            questionvos.add(vo);
        }
        model.addAttribute("vos", questionvos);

        return "profile";
    }
}
