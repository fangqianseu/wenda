/*
Date: 04/11,2019, 10:44
*/
package com.fq.interceptor;

import com.fq.model.LoginTicket;
import com.fq.model.SessionHolder;
import com.fq.model.User;
import com.fq.service.TicketService;
import com.fq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceper implements HandlerInterceptor {
    //    @Autowired
//    private LoginTicketCacheService loginTicketCacheService;
    @Autowired
    private TicketService ticketService;

    @Autowired
    private SessionHolder sessionHolder;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie c = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (cookie.getName().equals("ticket")) {
                c = cookie;
                break;
            }
        }

        int userId;
        if (c != null) {
//            String res = loginTicketCacheService.getLoginTicket(c.getValue());
//            if (res != null) {
//                userId = Integer.parseInt(res);
//            } else {
//                LoginTicket ticket = ticketService.getTicketByTicket(c.getValue());
//
//                if (ticket == null || new Date().after(ticket.getExpired()) || ticket.getStatus() != 0)
//                    return true;
//
//                userId = ticket.getUserId();
//                long expireSeconds = ticket.getExpired().getTime() - new Date().getTime();
//                expireSeconds = expireSeconds >= 0 ? expireSeconds / 1000 : 0;
//                loginTicketCacheService.addLoginCache(ticket.getTicket(), (int) expireSeconds, userId);
//            }


            LoginTicket ticket = ticketService.getTicketByTicket(c.getValue());
            if (ticket == null || ticket.getStatus() != 0) return true;
            if (new Date().after(ticket.getExpired())) {
                ticketService.delectTicket(ticket.getTicket());
                return true;
            }
            userId = ticket.getUserId();
            User user = userService.getUserById(userId);
            sessionHolder.setUser(user);
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && sessionHolder.getUser() != null)
            modelAndView.addObject("user", sessionHolder.getUser());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        sessionHolder.removeUser();
    }
}
