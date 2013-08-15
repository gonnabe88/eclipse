package org.dalpha.app;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.MessageType;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

@SuppressWarnings("serial")
public class MessageServlet extends HttpServlet {
   
    XMPPService xmppService = XMPPServiceFactory.getXMPPService();
   

    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
    
    

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 메세지 수신
        Message message = xmppService.parseMessage(request);
        String from = message.getFromJid().getId();
       
        System.out.println("From : " + from);
        System.out.println("MessageType : " + message.getMessageType());
        System.out.println("Body : " + message.getBody());
       
        // 메세지 발신
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.withRecipientJids(new JID("to@gmail.com"));
        messageBuilder.withMessageType(MessageType.NORMAL);
        messageBuilder.withBody("Hi!");
       
        Message reply = messageBuilder.build();
        xmppService.sendMessage(reply);
    }
}