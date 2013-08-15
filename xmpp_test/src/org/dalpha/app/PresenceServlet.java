package org.dalpha.app;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.Presence;
import com.google.appengine.api.xmpp.PresenceShow;
import com.google.appengine.api.xmpp.PresenceType;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;


@SuppressWarnings("serial")
public class PresenceServlet extends HttpServlet {
   
    XMPPService xmppService = XMPPServiceFactory.getXMPPService();

    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Presence presence = xmppService.parsePresence(request);
      
        
        String from = presence.getFromJid().getId();
        String to = presence.getToJid().getId();
       
        System.out.println("From : " + from);
        System.out.println("To : " + to);
        System.out.println(presence.getPresenceType());
       
        xmppService.sendPresence(presence.getFromJid(), PresenceType.AVAILABLE, PresenceShow.CHAT, "XMPP");
    }
}