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
import javax.swing.text.EditorKit;

/**
 * Servlet that adds a new message to all registered devices.
 * <p>
 * This servlet is used just by the browser (i.e., not device).
 */
@SuppressWarnings("serial")
public class SendMessagesServlet extends BaseServlet {

	private static final int MULTICAST_SIZE = 1000;

	private Sender sender;

	private static final Executor threadPool = Executors.newFixedThreadPool(5);

	private static String   COLLAPSE_KEY = String.valueOf(Math.random() % 100 + 1);
	private static boolean  DELAY_WHILE_IDLE = true;
	private static int   TIME_TO_LIVE = 3;
	private static int    RETRY = 3;
	private static final String PARAMETER_DEPT1 = "dept1";
	private static final String PARAMETER_DEPT2 = "dept2";
	private static final String PARAMETER_DEPT3 = "dept3";
	private static final String PARAMETER_DEPT4 = "dept4";
	private static final String PARAMETER_DEPT5 = "dept5";
	private static final String PARAMETER_DEPT6 = "dept6";
	private static final String PARAMETER_DEPT7 = "dept7";
	private static final String PARAMETER_DEPT8 = "dept8";
	private static final String PARAMETER_DEPT9 = "dept9";
	private static final String PARAMETER_DEPT10 = "dept10";
	private static final String PARAMETER_DEPT11 = "dept11";
	private static final String PARAMETER_DEPT12 = "dept12";
	private static final String PARAMETER_DEPT13 = "dept13";
	private static final String PARAMETER_DEPT14 = "dept14";
	private static final String PARAMETER_DEPT15 = "dept15";
	private static final String PARAMETER_DEPT16 = "dept16";

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
		try {
			req.setCharacterEncoding("EUC-KR");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String dept1 = getParameter(req, PARAMETER_DEPT1);
		String dept2 = getParameter(req, PARAMETER_DEPT2);
		String dept3 = getParameter(req, PARAMETER_DEPT3);
		String dept4 = getParameter(req, PARAMETER_DEPT4);
		String dept5 = getParameter(req, PARAMETER_DEPT5);
		String dept6 = getParameter(req, PARAMETER_DEPT6);
		String dept7 = getParameter(req, PARAMETER_DEPT7);
		String dept8 = getParameter(req, PARAMETER_DEPT8);
		String dept9 = getParameter(req, PARAMETER_DEPT9);
		String dept10 = getParameter(req, PARAMETER_DEPT10);
		String dept11 = getParameter(req, PARAMETER_DEPT11);
		String dept12 = getParameter(req, PARAMETER_DEPT12);
		String dept13 = getParameter(req, PARAMETER_DEPT13);
		String dept14 = getParameter(req, PARAMETER_DEPT14);
		String dept15 = getParameter(req, PARAMETER_DEPT15);
		String dept16 = getParameter(req, PARAMETER_DEPT16);

		List<String> devices = Datastore.getDevices(dept1, dept2, dept3, dept4, dept5, dept6, dept7, dept8, dept9, dept10, dept11, dept12, dept13, dept14, dept15, dept16);
		String status;

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
				.addData("title",  "국민대 알림 : 모두들 안녕하세요?")
				.addData("msg",  "본문 : " + req.getParameter("message"))
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
						asyncSend(partialDevices,req);
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

				//Message message = new Message.Builder().build();
				Message message = new Message.Builder()
				.collapseKey(COLLAPSE_KEY)
				.delayWhileIdle(DELAY_WHILE_IDLE)
				.timeToLive(TIME_TO_LIVE)
				.addData("title",  "국민대 알림 : 모두들 안녕하세요?")
				.addData("msg",  "본문 : " + req.getParameter("message"))
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
