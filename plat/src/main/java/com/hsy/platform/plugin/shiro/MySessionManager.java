package com.hsy.platform.plugin.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;

import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

public class MySessionManager extends DefaultWebSessionManager {

    /**
     * 重写session获取方法，如果第一次request有存有session信息的话，从request取，否则从redis取
     * @param sessionKey
     * @return
     * @throws UnknownSessionException
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
       Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if(sessionKey instanceof  WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }
        if (request != null && sessionId != null) {//从request中获取session
                Session session = (Session) request.getAttribute(sessionId.toString());
                if (session != null) {
                    return session;
                }
        }
        Session session = super.retrieveSession(sessionKey);
        if(request != null && sessionId != null){

                request.setAttribute(sessionId.toString(), session);
            }
        return session;
    }
}
