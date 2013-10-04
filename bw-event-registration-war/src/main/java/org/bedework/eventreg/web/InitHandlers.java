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

import org.bedework.eventreg.bus.Request;
import org.bedework.eventreg.bus.SessionManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author douglm
 *
 */
public class InitHandlers extends HandlerInterceptorAdapter {
  protected final Log logger = LogFactory.getLog(getClass());

  private SessionManager sessMan;

  /**
   * @param sm
   */
  public void setSessionManager(final SessionManager sm) {
    sessMan = sm;
  }

  @Override
  public boolean preHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler) throws Exception {
    logger.debug("init handler Intercepter");

    sessMan.setReq(new Request(request, response));
    try {
      sessMan.openDb();
    } catch (Throwable e) {
      throw new Exception(e);
    }

    return true;
  }
}