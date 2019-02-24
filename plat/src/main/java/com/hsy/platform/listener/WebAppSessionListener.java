package com.hsy.platform.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * session监听
 */
public class WebAppSessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        Object lineCount = arg0.getSession().getServletContext().getAttribute("lineCount");
        Integer count = 0;
        if (lineCount == null) {
            lineCount = "0";
        }
        count = Integer.valueOf(lineCount.toString());
        count++;
        arg0.getSession().getServletContext().setAttribute("lineCount", count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        Object lineCount = arg0.getSession().getServletContext().getAttribute("lineCount");
        Integer count = Integer.valueOf(lineCount.toString());
        count--;
        arg0.getSession().getServletContext().setAttribute("lineCount", count);
    }
}
