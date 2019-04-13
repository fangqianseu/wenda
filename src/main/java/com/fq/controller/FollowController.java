/*
Date: 04/12,2019, 15:07
*/
package com.fq.controller;

import com.fq.model.*;
import com.fq.service.AgreementService;
import com.fq.service.CommentService;
import com.fq.service.FollowService;
import com.fq.service.UserService;
import com.fq.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class FollowController {
    @Autowired
    private FollowService followService;
    @Autowired
    private SessionHolder sessionHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private AgreementService agreementService;
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/followQuestion", method = RequestMethod.POST)
    public String followQuestion(@RequestParam int questionId) {
        if (sessionHolder.getUser() == null)
            return WendaUtil.getJSONString(999);

        followService.follow(buildFollow(questionId, EntityType.ENTITY_QUESTION, 0));
        return WendaUtil.getJSONString(0, buildInfo(questionId, EntityType.ENTITY_QUESTION));
    }

    @ResponseBody
    @RequestMapping(value = "/unfollowQuestion", method = RequestMethod.POST)
    public String unfollowQuestion(@RequestParam int questionId) {
        if (sessionHolder.getUser() == null)
            return WendaUtil.getJSONString(999);

        followService.follow(buildFollow(questionId, EntityType.ENTITY_QUESTION, 1));

        return WendaUtil.getJSONString(0, buildInfo(questionId, EntityType.ENTITY_QUESTION));
    }

    @ResponseBody
    @RequestMapping(value = "/followUser", method = RequestMethod.POST)
    public String followUser(@RequestParam int userId) {
        if (sessionHolder.getUser() == null)
            return WendaUtil.getJSONString(999);

        followService.follow(buildFollow(userId, EntityType.ENTITY_USER, 0));
        return WendaUtil.getJSONString(0, buildInfo(userId, EntityType.ENTITY_USER));
    }

    @ResponseBody
    @RequestMapping(value = "/unfollowUser", method = RequestMethod.POST)
    public String unfollowUser(@RequestParam int userId) {
        if (sessionHolder.getUser() == null)
            return WendaUtil.getJSONString(999);

        followService.follow(buildFollow(userId, EntityType.ENTITY_USER, 1));
        return WendaUtil.getJSONString(0, buildInfo(userId, EntityType.ENTITY_USER));
    }

    @RequestMapping(path = {"/user/{uid}/followees"}, method = {RequestMethod.GET})
    public String followees(Model model, @PathVariable("uid") int userId) {
        model.addAttribute("curUser", userService.getUserById(userId));
        model.addAttribute("followeeCount", followService.getFolloweesCount(userId, EntityType.ENTITY_USER));

        List<Follow> followList = followService.getFollowees(userId, EntityType.ENTITY_USER);
        model.addAttribute("followees", buildFollowViewObject(followList, false));

        return "followees";
    }

    @RequestMapping(path = {"/user/{uid}/followers"}, method = {RequestMethod.GET})
    public String followers(Model model, @PathVariable("uid") int userId) {

        model.addAttribute("curUser", userService.getUserById(userId));
        model.addAttribute("followerCount", followService.getFollowersCount(userId, EntityType.ENTITY_USER));

        List<Follow> followList = followService.getFollowers(userId, EntityType.ENTITY_USER);

        model.addAttribute("followers", buildFollowViewObject(followList, true));

        return "followers";
    }

    private Follow buildFollow(int entityId, int entityType, int status) {
        Follow follow = new Follow();
        follow.setUserId(sessionHolder.getUser().getId());
        follow.setEntityId(entityId);
        follow.setEntityType(entityType);
        follow.setStatus(status);
        follow.setCreatedDate(new Date());
        return follow;
    }

    private Map<String, Object> buildInfo(int entityId, int entityType) {
        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", sessionHolder.getUser().getHeadUrl());
        info.put("name", sessionHolder.getUser().getName());
        info.put("id", sessionHolder.getUser().getId());
        info.put("count", followService.getFollowersCount(entityId, entityType));
        return info;
    }

    private List<ViewObject> buildFollowViewObject(List<Follow> followList, boolean isFollower) {
        List<ViewObject> viewObjectList = new ArrayList<>();
        for (Follow follow : followList) {
            ViewObject vo = new ViewObject();
            User user = null;
            if (isFollower)
                user = userService.getUserById(follow.getUserId());
            else
                user = userService.getUserById(follow.getEntityId());

            vo.set("user", user);
            vo.set("followed", followService.isFollow(sessionHolder.getUser().getId(), user.getId(), EntityType.ENTITY_USER));
            vo.set("followerCount", followService.getFollowersCount(user.getId(), EntityType.ENTITY_USER));
            vo.set("followeeCount", followService.getFolloweesCount(user.getId(), EntityType.ENTITY_USER));
            vo.set("commentCount", commentService.getUsersCommentcount(user.getId(), EntityType.ENTITY_QUESTION));
            vo.set("agreements", agreementService.getAgreementCount(user.getId(), EntityType.ENTITY_USER));

            viewObjectList.add(vo);
        }
        return viewObjectList;
    }
}
