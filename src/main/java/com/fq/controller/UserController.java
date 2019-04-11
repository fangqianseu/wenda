/*
Date: 04/11,2019, 11:03
*/
package com.fq.controller;

import com.fq.model.Question;
import com.fq.model.SessionHolder;
import com.fq.model.User;
import com.fq.model.ViewObject;
import com.fq.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private SessionHolder sessionHolder;
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = {"/user/{id}"}, method = RequestMethod.GET)
    public String userDetial(Model model, @PathVariable int id) {
        List<Question> questions = questionService.getLatestQuestions(id, 0, 10);
        User user = sessionHolder.getUser();

        ViewObject viewObject = new ViewObject();
        viewObject.set("user", user);

        model.addAttribute("profileUser", viewObject);
        return "profile";
    }
}
