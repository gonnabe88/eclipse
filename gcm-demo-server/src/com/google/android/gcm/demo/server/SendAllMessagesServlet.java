/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gcm.demo.server;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that adds a new message to all registered devices.
 * <p>
 * This servlet is used just by the browser (i.e., not device).
 */
@SuppressWarnings("serial")
public class SendAllMessagesServlet extends BaseServlet {

  private static final int MULTICAST_SIZE = 1000;

  private Sender sender;

  private static final Executor threadPool = Executors.newFixedThreadPool(5);

  private static String   COLLAPSE_KEY = String.valueOf(Math.random() % 100 + 1);
  private static boolean  DELAY_WHILE_IDLE = true;
  private static int   TIME_TO_LIVE = 3;
  private static int    RETRY = 3;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    sender = newSender(config);
  }

  /**
   * Creates the {@link Sender} based on the servlet settings.
   */
  protected Sender newSender(ServletConfig config) {
    String key = (String) config.getServletContext()
        .getAttribute(ApiKeyInitializer.ATTRIBUTE_ACCESS_KEY);
    return new Sender(key);
  }

  /**
   * Processes the request to add a new message.
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    List<String> devices = Datastore.getDevices();
    String status;
    try {
		req.setCharacterEncoding("EUC-KR");
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    if (devices.isEmpty()) {
      status = "Message ignored as there is no device registered!";
    } else {
      // NOTE: check below is for demonstration purposes; a real application
      // could always send a multicast, even for just one recipient
      if (devices.size() == 1) {
        // send a single message using plain post
        String registrationId = devices.get(0);
        //Message message = new Message.Builder().build();
        Message message3 = new Message.Builder()
        .collapseKey(COLLAPSE_KEY)
        .delayWhileIdle(DELAY_WHILE_IDLE)
        .timeToLive(TIME_TO_LIVE)
        .addData("title",  "���δ� �˸� : ��ε� �ȳ��ϼ���?")
        .addData("msg",  "���� : " + req.getParameter("message"))
//        .addData("msg",  "���� : " + new String(req.getParameter("message").getBytes("8859_1"),"utf-8"))
        .build();
        Result result = sender.send(message3, registrationId, 5);
        status = "Sent message to one device: " + result + message3.getData();
      } else {
        // send a multicast message using JSON
        // must split in chunks of 1000 devices (GCM limit)
        int total = devices.size();
        List<String> partialDevices = new ArrayList<String>(total);
        int counter = 0;
        int tasks = 0;
        for (String device : devices) {
          counter++;
          partialDevices.add(device);
          int partialSize = partialDevices.size();
          if (partialSize == MULTICAST_SIZE || counter == total) {
            asyncSend(partialDevices, req);
            partialDevices.clear();
            tasks++;
          }
        }
        status = "Asynchronously sending " + tasks + " multicast messages to " +
            total + " devices";
      }
    }
    req.setAttribute(HomeServlet.ATTRIBUTE_STATUS, status.toString());
    getServletContext().getRequestDispatcher("/home").forward(req, resp);
  }

  private void asyncSend(List<String> partialDevices, final HttpServletRequest req) {
    // make a copy
    final List<String> devices = new ArrayList<String>(partialDevices);
    threadPool.execute(new Runnable() {

      public void run() {
        Message message = new Message.Builder()
        .collapseKey(COLLAPSE_KEY)
        .delayWhileIdle(DELAY_WHILE_IDLE)
        .timeToLive(TIME_TO_LIVE)
        .addData("title",  "���δ� �˸� : ��ε� �ȳ��ϼ���?")
        .addData("msg",  "���� : " + req.getParameter("message"))
        .build();
        MulticastResult multicastResult;
        try {
          multicastResult = sender.send(message, devices, 5);
        } catch (IOException e) {
          logger.log(Level.SEVERE, "Error posting messages", e);
          return;
        }
        List<Result> results = multicastResult.getResults();
        // analyze the results
        for (int i = 0; i < devices.size(); i++) {
          String regId = devices.get(i);
          Result result = results.get(i);
          String messageId = result.getMessageId();
          if (messageId != null) {
            logger.fine("Succesfully sent message to device: " + regId +
                "; messageId = " + messageId);
            String canonicalRegId = result.getCanonicalRegistrationId();
            if (canonicalRegId != null) {
              // same device has more than on registration id: update it
              logger.info("canonicalRegId " + canonicalRegId);
              Datastore.updateRegistration(regId, canonicalRegId);
            }
          } else {
            String error = result.getErrorCodeName();
            if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
              // application has been removed from device - unregister it
              logger.info("Unregistered device: " + regId);
              Datastore.unregister(regId);
            } else {
              logger.severe("Error sending message to " + regId + ": " + error);
            }
          }
        }
      }});
  }

}
