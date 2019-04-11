/*
Date: 04/11,2019, 10:04
*/
package com.fq.service;

import com.fq.dao.LoginTicketDao;
import com.fq.model.LoginTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    private LoginTicketDao loginTicketDao;

    public String addTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24 * 7);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-", ""));

        loginTicketDao.addTicket(loginTicket);

        return loginTicket.getTicket();
    }

    public LoginTicket getTicketByTicket(String ticket) {
        return loginTicketDao.getTicket(ticket);
    }

    public int delectTicket(String ticket) {
        return loginTicketDao.setStaut(ticket, 1);
    }
}
