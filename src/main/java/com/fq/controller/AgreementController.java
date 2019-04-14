/*
Date: 04/12,2019, 15:07
*/
package com.fq.controller;

import com.fq.async.producer.CommmentAgreementProducer;
import com.fq.model.Agreement;
import com.fq.model.EntityType;
import com.fq.model.SessionHolder;
import com.fq.service.AgreementService;
import com.fq.service.CommentService;
import com.fq.service.UserService;
import com.fq.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class AgreementController {
    @Autowired
    private AgreementService agreementService;
    @Autowired
    private SessionHolder sessionHolder;
    @Autowired
    private CommmentAgreementProducer COMMENTAGREEMENTProducer;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public String like(@RequestParam int commentId) {
        if (sessionHolder.getUser() == null)
            return WendaUtil.getJSONString(999);

        agreementService.agree(buildAgreement(commentId, 0));
        int agreementCount = agreementService.getAgreementCount(commentId, EntityType.ENTITY_COMMENT);

        // 引入异步事件 -- agreementEvent
        int toUserId = commentService.selectCommentById(commentId).getUserId();
        COMMENTAGREEMENTProducer.buildEventModel(sessionHolder.getUser().getId(),
                toUserId, userService.getUserById(toUserId).getName(), commentService.selectCommentById(commentId).getEntityId());

        return WendaUtil.getJSONString(0, String.valueOf(agreementCount));
    }


    @ResponseBody
    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    public String dislike(@RequestParam int commentId) {
        if (sessionHolder.getUser() == null)
            return WendaUtil.getJSONString(999);

        agreementService.agree(buildAgreement(commentId, 1));
        int agreementCount = agreementService.getAgreementCount(commentId, EntityType.ENTITY_COMMENT);

        return WendaUtil.getJSONString(0, String.valueOf(agreementCount));
    }

    public Agreement buildAgreement(int commentId, int status) {
        Agreement agreement = new Agreement();
        agreement.setUserId(sessionHolder.getUser().getId());
        agreement.setEntityId(commentId);
        agreement.setEntityType(EntityType.ENTITY_COMMENT);
        agreement.setStatus(status);
        agreement.setCreatedDate(new Date());
        return agreement;
    }
}
