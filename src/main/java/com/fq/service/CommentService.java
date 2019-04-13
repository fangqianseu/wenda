/*
Date: 04/11,2019, 15:37
*/
package com.fq.service;

import com.fq.dao.CommentDao;
import com.fq.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private SensitiveService sensitiveService;

    public int commitAdd(Comment comment) {
        // js和 敏感词 过滤
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));

        return commentDao.addComment(comment) > 0 ? comment.getId() : 0;
    }

    public List<Comment> selectCommentByEntry(int entityId, int entityType) {
        return commentDao.selectCommentByEntity(entityId, entityType);
    }

    public int getCommentcount(int entryId, int entryType) {
        return commentDao.getCommentCountofEntityType(entryId, entryType);
    }

    public int getUsersCommentcount(int userId, int entryType) {
        return commentDao.getCommentCounByUserId(userId, entryType);
    }

    public int getCommentcountByUserId(int UserId) {
        return commentDao.getCommentCountbyUserId(UserId);
    }

    public Comment selectCommentById(int commentId) {
        return commentDao.selectCommentById(commentId);
    }
}
