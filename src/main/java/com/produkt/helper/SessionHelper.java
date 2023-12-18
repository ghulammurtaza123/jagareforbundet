package com.produkt.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public void removeSessionMessage() {

        try {

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                HttpSession session = request.getSession(false); // passing false to getSession to avoid creating a new session

                if (session != null) {
                    session.removeAttribute("message");
                }
            } else {
                System.out.println("Request Attributes are null");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
