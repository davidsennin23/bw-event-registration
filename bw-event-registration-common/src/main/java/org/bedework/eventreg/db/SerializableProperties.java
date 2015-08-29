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
package org.bedework.eventreg.db;

import org.bedework.eventreg.common.EventregException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Serializable form of information.
 *
 */
public class SerializableProperties {
  private static final ObjectMapper om = new ObjectMapper();

  protected Map vals;

  protected SerializableProperties() {
  }

  public void init(final Map vals) throws EventregException {
    this.vals = vals;
  }

  public void setProperties(final String val) throws EventregException {
    if (val == null) {
      vals = new HashMap();
    } else {
      vals = asMap(val);
    }
  }

  /** This will be called to serialize the values for the db.
   *
   * @return json serialized value
   * @throws EventregException
   */
  @JsonIgnore
  public String getProperties() throws EventregException {
    return asString();
  }

  /**
   *
   * @return Map representing extra properties.
   */
  @JsonIgnore
  public Map getVals() {
    if (vals == null) {
      vals = new HashMap();
    }

    return vals;
  }

  /* ==============================================================
   *                   Json methods
   * ============================================================== */

  protected Map<?, ?> asMap(final String val) throws EventregException {
    try {
      init((Map)om.readValue(val, Object.class));
      return vals;
    } catch (final Throwable t) {
      throw new EventregException(t);
    }
  }

  protected String asString() throws EventregException {
    final StringWriter sw = new StringWriter();

    try {
      om.writeValue(sw, getVals());
      return sw.toString();
    } catch (final Throwable t) {
      throw new EventregException(t);
    }
  }

  @JsonIgnore
  protected Map<?, ?> getMap(final String name) throws EventregException {
    final Object val = getVals().get(name);

    if (val == null) {
      throw new EventregException("missing value: " + name);
    }
    try {
      return (Map)val;
    } catch (final Throwable t) {
      throw new EventregException(t);
    }
  }

  /* ==============================================================
   *                   set methods
   * ============================================================== */

  public void setBoolean(final String name, final Boolean val) {
    getVals().put(name, val);
  }

  public void setInt(final String name, final Integer val) {
    getVals().put(name, val);
  }

  public void setString(final String name, final String val) {
    if (val == null) {
      getVals().remove(name);
    } else {
      getVals().put(name, val);
    }
  }

  public void setObject(final String name, final Object val) {
    if (val == null) {
      getVals().remove(name);
    } else {
      getVals().put(name, val);
    }
  }

  /* ==============================================================
   *                   get methods
   * ============================================================== */

  public String must(final String name) throws EventregException {
    return JsonUtil.must(name, getVals());
  }

  public List<String> mustList(final String name) throws EventregException {
    //noinspection unchecked
    return JsonUtil.mustList(name, getVals());
  }

  public String may(final String name) throws EventregException {
    return JsonUtil.may(name, getVals());
  }

  public List mayList(final String name) throws EventregException {
    return JsonUtil.mayList(name, getVals());
  }

  public List mayList(final String name,
                      final Map theVals) throws EventregException {
    return JsonUtil.mayList(name, theVals);
  }

  protected int mayInt(final String name) throws EventregException {
    final Object val = getVals().get(name);

    if (val == null) {
      return 0;
    }
    try {
      return (Integer)val;
    } catch (final Throwable t) {
      throw new EventregException(t);
    }
  }

  protected boolean mayBool(final String name) throws EventregException {
    final Object val = getVals().get(name);

    if (val == null) {
      return false;
    }
    try {
      return (Boolean)val;
    } catch (final Throwable t) {
      throw new EventregException(t);
    }
  }
}
