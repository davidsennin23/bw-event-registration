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

import org.bedework.eventreg.db.SysInfo;

import org.springframework.web.servlet.ModelAndView;

/**
 * Ensure we get a valid admin token.
 *
 */
public abstract class AdminAuthAbstractController extends AuthAbstractController {
  @Override
  protected ModelAndView setup() throws Throwable {
    ModelAndView mv = super.setup();

    if (mv != null) {
      return mv;
    }

    SysInfo sysi = sessMan.getSysInfo();

    if (sysi == null) {
      return errorReturn("No system properties");
    }

    String adminToken = sysi.getEventregAdminToken();

    if (adminToken == null) {
      return errorReturn("No admin token set in system properties");
    }

    if (!adminToken.equals(req.getAdminToken())) {
      return errorReturn("Invalid admin token");
    }

    admin = true;

    return null;
  }
}
