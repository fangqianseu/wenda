/*
Date: 04/11,2019, 14:52
*/
package com.fq.service;

import com.fq.dao.QuestionDao;
import com.fq.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private SensitiveService sensitiveService;

    @Transactional(rollbackFor = Exception.class)
    public int addQuestion(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));

        return questionDao.addQuestion(question);
    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }

    public Question getQuestionById(int qid) {
        return questionDao.selectQuestionById(qid);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateComment(int questionId, int commentcount) {
        return questionDao.updateCommentCount(questionId, commentcount);
    }

}
