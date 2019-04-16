/*
Date: 04/11,2019, 10:04
*/
package com.fq.service;

import com.fq.dao.LoginTicketDao;
import com.fq.model.LoginTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service

public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
    @Autowired
    private LoginTicketDao loginTicketDao;

    @Transactional(rollbackFor = {Exception.class})
    public String addTicket(int userId) throws Exception {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24 * 7);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-", ""));

        loginTicketDao.addTicket(loginTicket);
        logger.info("Ticket add: " + loginTicket.toString());

        return loginTicket.getTicket();
    }

    public LoginTicket getTicketByTicket(String ticket) {
        logger.info("Ticket query: " + ticket);
        return loginTicketDao.getTicket(ticket);
    }

    public int delectTicket(String ticket) {
        logger.info("Ticket change: " + ticket);
        return loginTicketDao.setStaut(ticket, 1);
    }
}
