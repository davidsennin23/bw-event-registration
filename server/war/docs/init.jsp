<%@ include file="/docs/include.jsp" %>

<%@ include file="/docs/head.jsp" %>

  <h3>Register for this event</h3>
  <c:choose>
    <%--
    <c:when test="${sessMan.isRegistered}">
      <div class="box" id="registered">
        <%@ include file="/docs/userNav.jsp" %>
        <p class="registered">
          <span class="checkmark">&#x2713;</span> You are registered for this event.
        </p>
        <p class="unregister">
          <a href="eventreg.do">unregister</a>
        </p>
      </div>
    </c:when>
    --%>
    <c:when test="${sessMan.deadlinePassed}">
      <form action="eventreg.do" method="POST">
        <div class="box" id="deadlinePassed">
          <%@ include file="/docs/userNav.jsp" %>
          Registration is closed.<br/>
          <input type="submit" value="Register for waiting list"/>
        </div>
      </form>
    </c:when>
    <c:when test="${sessMan.registrationFull}">
      <form action="eventreg.do" method="POST">
        <div class="box" id="registrationFull">
          <%@ include file="/docs/userNav.jsp" %>
          Registration is full.<br/>
          <input type="submit" value="Register for waiting list"/>
        </div>
      </form>
    </c:when>
    <c:when test="${sessMan.superUser}">
      <%-- Form for superuser --%>
      <form action="eventreg.do" method="POST">
        <div class="box">
          <%@ include file="/docs/userNav.jsp" %>
          <p class="superuserForm">
            Tickets: <input type="text" name="numtickets" id="numtickets" size="3" value=""/>
            <script type="text/javascript">
              formelement('numtickets');
            </script>
            <select name="regType">
              <option>hold</option>
              <option>normal</option>
            </select><br/>
            Comment: <input type="text" value="" size="10" name="comment" id="comment"/>
            <script type="text/javascript">
              formelement('comment');
            </script><br/>
            <input type="submit" value="Register"/>
          </p>
        </div>
      </form>
    </c:when>
    <c:otherwise>
      <%-- Normal authenticated user --%>
      <div class="box">
        <%@ include file="/docs/userNav.jsp" %>
        <div class="ticketVals">
          <!-- em>Tickets</em><br/-->
          Total: ${sessMan.currEvent.maxTickets}<br/>
          Available: ${sessMan.currEvent.maxTickets - sessMan.ticketCount}
        </div>
        <form action="eventreg.do" id="userRegForm" method="POST">
          <c:choose>
            <c:when test="${sessMan.currEvent.maxTicketsPerUser > 1}">
              Tickets:
              <select id="numtickets" name="numtickets">
                <c:forEach var="i" begin="1" end="${sessMan.currEvent.maxTicketsPerUser}">
                   <option value="${i}">${i}</option>
                </c:forEach>
              </select>
            </c:when>
            <c:otherwise>
              <input type="hidden" name="numtickets" value="1"/>
            </c:otherwise>
          </c:choose>
          <input type="hidden" name="href" value="${sessMan.href}"/>
          <input type="submit" value="Register"/>
        </form>
      </div>
    </c:otherwise>
  </c:choose>

 <%@ include file="foot.jsp" %>