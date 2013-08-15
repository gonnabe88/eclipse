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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that adds display number of devices and button to send a message.
 * <p>
 * This servlet is used just by the browser (i.e., not device) and contains the
 * main page of the demo app.
 */
@SuppressWarnings("serial")
public class HomeServlet extends BaseServlet {

  static final String ATTRIBUTE_STATUS = "status";

  /**
   * Displays the existing messages and offer the option to send a new one.
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();

    out.print("<html><body>");
    out.print("<head>");
    out.print("  <title>GCM Demo</title>");
    out.print("  <link rel='icon' href='favicon.png'/>");
    out.print("</head>");
    String status = (String) req.getAttribute(ATTRIBUTE_STATUS);
    if (status != null) {
      out.print(status);
    }
    List<String> devices = Datastore.getDevices();
    
    out.print("<h1>kookmin univ administrator page4</h1>");
    
    if (devices.isEmpty()) {
      out.print("<h2>No devices registered!</h2>");
      out.print("<h2>" + devices.size() + " device(s) registered!</h2>");

      out.print("<form name='form' method='POST' action='send'>");
      out.print("<input type='text' size='20' name='message' />");
      out.print("<br><input type='checkbox' name='dept' value='1' />COMPUTER<br>");
      out.print("<input type='checkbox' name='dept' value='2' />MANAGEMENT<br>");
      out.print("<input type='checkbox' name='dept' value='3' />DESIGN<br>");
      out.print("<input type='checkbox' name='dept' value='4' />MATHMATICS<br>");
      out.print("<input type='checkbox' name='dept' value='5' />PHYSICS<br>");
      out.print("<input type='submit' value='Send Message(send)' />");
      out.print("</form>");
      
      
      out.print("<form name='form' method='POST' action='sendAll'>");
      out.print("<input type='text' size='20' name='message' />");
      out.print("<input type='submit' value='Send Message(sendAll)' />");
      out.print("</form>");
      
      out.print("<form name='form' method='POST' action='unregister'>");
      out.print("<input type='text' size='20' name='regId' />");
      out.print("<input type='submit' value='unregister' />");
      out.print("</form>");
      
      out.print("<form name='form' method='POST' action='home'>");
      out.print("<input type='submit' value='home' />");
      out.print("</form>");
    } else {
      out.print("<h2>" + devices.size() + " device(s) registered!</h2>");

      out.print("<form name='form' method='POST' action='send'>");
      out.print("<input type='text' size='20' name='message' />");
      
      out.print("<br><input type='checkbox' name='dept1' value='N' checked/>");
      out.print("<input type='checkbox' name='dept1' value='T' />COMPUTER<br>");
      
      out.print("<input type='checkbox' name='dept2' value='N' checked/>");
      out.print("<input type='checkbox' name='dept2' value='T' />MANAGEMENT<br>");
      
      out.print("<input type='checkbox' name='dept3' value='F' checked/>");
      out.print("<input type='checkbox' name='dept3' value='T' />DESIGN<br>");
      
      out.print("<input type='checkbox' name='dept4' value='N' checked/>");
      out.print("<input type='checkbox' name='dept4' value='T' />MATHMATICS<br>");
      
      out.print("<input type='checkbox' name='dept5' value='N' checked/>");
      out.print("<input type='checkbox' name='dept5' value='T' />PHYSICS<br>");
      
      out.print("<input type='checkbox' name='dept6' value='N' checked/>");
      out.print("<input type='checkbox' name='dept6' value='T' />COMPUTER<br>");
      
      out.print("<input type='checkbox' name='dept7' value='N' checked/>");
      out.print("<input type='checkbox' name='dept7' value='T' />MANAGEMENT<br>");
      
      out.print("<input type='checkbox' name='dept8' value='N' checked/>");
      out.print("<input type='checkbox' name='dept8' value='T' />DESIGN<br>");
      
      out.print("<input type='checkbox' name='dept9' value='N' checked/>");
      out.print("<input type='checkbox' name='dept9' value='T' />MATHMATICS<br>");
      
      out.print("<input type='checkbox' name='dept10' value='N' checked/>");
      out.print("<input type='checkbox' name='dept10' value='T' />PHYSICS<br>");
      
      out.print("<input type='checkbox' name='dept11' value='N' checked/>");
      out.print("<input type='checkbox' name='dept11' value='T' />COMPUTER<br>");
      
      
      out.print("<input type='checkbox' name='dept12' value='N' checked/>");
      out.print("<input type='checkbox' name='dept12' value='T' />MANAGEMENT<br>");
      
      out.print("<input type='checkbox' name='dept13' value='N' checked/>");
      out.print("<input type='checkbox' name='dept13' value='T' />DESIGN<br>");
      
      out.print("<input type='checkbox' name='dept14' value='N' checked/>");
      out.print("<input type='checkbox' name='dept14' value='T' />MATHMATICS<br>");
      
      out.print("<input type='checkbox' name='dept15' value='N' checked/>");
      out.print("<input type='checkbox' name='dept15' value='T' />PHYSICS<br>");
      
      out.print("<input type='checkbox' name='dept16' value='N' checked/>");
      out.print("<input type='checkbox' name='dept16' value='T' />PHYSICS<br>");
      out.print("<input type='submit' value='Send Message(send)' />");
      out.print("</form>");
      
      
      out.print("<form name='form' method='POST' action='sendAll'>");
      out.print("<input type='text' size='20' name='message' />");
      out.print("<input type='submit' value='Send Message(sendAll)' />");
      out.print("</form>");
      
      out.print("<form name='form' method='POST' action='unregister'>");
      out.print("<input type='text' size='20' name='regId' />");
      out.print("<input type='submit' value='unregister' />");
      out.print("</form>");
      
      out.print("<form name='form' method='POST' action='home'>");
      out.print("<input type='submit' value='home' />");
      out.print("</form>");
      
      
      out.print("<form name='form' method='POST' action='dept'>");
      out.print("<input type='text' size='20' name='regId' />");
      out.print("<br><input type='checkbox' name='dept1' value='F' checked/>");
      out.print("<input type='checkbox' name='dept1' value='T' />COMPUTER<br>");
      
      out.print("<input type='checkbox' name='dept2' value='F' checked/>");
      out.print("<input type='checkbox' name='dept2' value='T' />MANAGEMENT<br>");
      
      out.print("<input type='checkbox' name='dept3' value='F' checked/>");
      out.print("<input type='checkbox' name='dept3' value='T' />DESIGN<br>");
      
      out.print("<input type='checkbox' name='dept4' value='F' checked/>");
      out.print("<input type='checkbox' name='dept4' value='T' />MATHMATICS<br>");
      
      out.print("<input type='checkbox' name='dept5' value='F' checked/>");
      out.print("<input type='checkbox' name='dept5' value='T' />PHYSICS<br>");
      
      out.print("<input type='checkbox' name='dept6' value='F' checked/>");
      out.print("<input type='checkbox' name='dept6' value='T' />COMPUTER<br>");
      
      out.print("<input type='checkbox' name='dept7' value='F' checked/>");
      out.print("<input type='checkbox' name='dept7' value='T' />MANAGEMENT<br>");
      
      out.print("<input type='checkbox' name='dept8' value='F' checked/>");
      out.print("<input type='checkbox' name='dept8' value='T' />DESIGN<br>");
      
      out.print("<input type='checkbox' name='dept9' value='F' checked/>");
      out.print("<input type='checkbox' name='dept9' value='T' />MATHMATICS<br>");
      
      out.print("<input type='checkbox' name='dept10' value='F' checked/>");
      out.print("<input type='checkbox' name='dept10' value='T' />PHYSICS<br>");
      
      out.print("<input type='checkbox' name='dept11' value='F' checked/>");
      out.print("<input type='checkbox' name='dept11' value='T' />COMPUTER<br>");
      
      
      out.print("<input type='checkbox' name='dept12' value='F' checked/>");
      out.print("<input type='checkbox' name='dept12' value='T' />MANAGEMENT<br>");
      
      out.print("<input type='checkbox' name='dept13' value='F' checked/>");
      out.print("<input type='checkbox' name='dept13' value='T' />DESIGN<br>");
      
      out.print("<input type='checkbox' name='dept14' value='F' checked/>");
      out.print("<input type='checkbox' name='dept14' value='T' />MATHMATICS<br>");
      
      out.print("<input type='checkbox' name='dept15' value='F' checked/>");
      out.print("<input type='checkbox' name='dept15' value='T' />PHYSICS<br>");
      
      out.print("<input type='checkbox' name='dept16' value='F' checked/>");
      out.print("<input type='checkbox' name='dept16' value='T' />PHYSICS<br>");
      out.print("<input type='submit' value='update(dept)' />");
      out.print("</form>");
      
    }
    out.print("</body></html>");
    resp.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    doGet(req, resp);
  }

}
