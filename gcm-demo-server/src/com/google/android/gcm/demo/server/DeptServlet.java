
package com.google.android.gcm.demo.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that registers a device, whose registration id is identified by
 * {@link #PARAMETER_REG_ID}.
 *
 * <p>
 * The client app should call this servlet everytime it receives a
 * {@code com.google.android.c2dm.intent.REGISTRATION C2DM} intent without an
 * error or {@code unregistered} extra.
 */
@SuppressWarnings("serial")
public class DeptServlet extends BaseServlet {

  private static final String PARAMETER_REG_ID = "regId";
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
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException {
    String regId = getParameter(req, PARAMETER_REG_ID);
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
    
    System.out.println(dept1+dept2+dept3+dept4+dept5+dept6+dept7+dept8+dept9+dept10+dept11+dept12+dept13+dept14+dept15+dept16);
    Datastore.update_dept(regId, dept1, dept2, dept3, dept4, dept5, dept6, dept7, dept8, dept9, dept10, dept11, dept12, dept13, dept14, dept15, dept16); 	
    
    setSuccess(resp);
  }

}
