/* ********************************************************************
Licensed to Jasig under one or more contributor license
agreements. See the NOTICE file distributed with this work
for additional information regarding copyright ownership.
Jasig licenses this file to you under the Apache License,
Version 2.0 (the "License"); you may not use this file
except in compliance with the License. You may obtain a
copy of the License at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied. See the License for the
specific language governing permissions and limitations
under the License.
 */
package org.bedework.eventreg.web;

import org.bedework.eventreg.bus.SessionManager;
import org.bedework.eventreg.db.Event;
import org.bedework.eventreg.db.Registration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author douglm
 *
 */
public class SuperUserAgendaController implements Controller {

  protected final Log logger = LogFactory.getLog(getClass());

  private SessionManager sessMan;

  @Override
  public ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    logger.debug("SuperUserAgendaController entry");

    if (!sessMan.getSuperUser()) {
      logger.warn("Non superuser attempted to access SuperUserAgenda.");
      return new ModelAndView("error");
    }

    try {
      Event ev = sessMan.getCurrEvent();

      TreeSet<Registration> regs = new TreeSet<Registration>();

      for (Registration reg: sessMan.getRegistrationsByHref(ev.getHref())) {
        reg.setEvent(ev);

        regs.add(reg);
      }

      Map myModel = new HashMap();
      myModel.put("suserAgenda", regs);
      myModel.put("sessMan", sessMan);

      return new ModelAndView("suagenda", myModel);
    } catch (Exception e) {
      logger.error(this, e);
      throw e;
    } catch (Throwable t) {
      logger.error(this, t);
      throw new Exception(t);
    } finally {
      sessMan.closeDb();
    }
  }

  /**
   * @param sm
   */
  public void setSessionManager(final SessionManager sm) {
    sessMan = sm;
  }
}
