/*
Date: 04/11,2019, 09:26
*/
package com.fq.controller;

import com.fq.model.EntityType;
import com.fq.model.Question;
import com.fq.model.SessionHolder;
import com.fq.model.ViewObject;
import com.fq.service.CommentService;
import com.fq.service.QuestionService;
import com.fq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private SessionHolder sessionHolder;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        List<Question> latestQuestions = questionService.getLatestQuestions(0, Integer.valueOf(page) * 10, 10);
        List<ViewObject> questions = new ArrayList<>();
        for (Question q : latestQuestions) {
            ViewObject vo = new ViewObject();
            vo.set("question", q);
            vo.set("user", userService.getUserById(q.getUserId()));
            questions.add(vo);
        }
        model.addAttribute("vos", questions);

        return "index";
    }
}
