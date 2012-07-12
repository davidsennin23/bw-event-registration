<%@ include file="/docs/include.jsp" %>

<%@ include file="/docs/head.jsp" %>

    <c:if test="${sessMan.adminUser}">
      <div class="fullpage">
        <div class="rightLinks">
          Welcome <c:out value="${sessMan.currentUser}"/> (admin)<br/>
          <a href="javascript:print();">print</a> |
          <a href="javascript:self.close();">close this window</a> |
          <a href="download.do?href=${sessMan.href}">download registrations</a>
        </div>
        <h1><c:out value="${sessMan.currEvent.summary}"/></h1>
        <div class="eventDateTime">
          <c:out value="${sessMan.currEvent.dateTime}"/>
          <%--<fmt:formatDate value="${sessMan.currEvent.date}" type="both" timeStyle="long" dateStyle="long" />--%>
        </div>
        <p class="left">
          Registrations: <c:out value="${sessMan.registrantCount}"/><br/>
          Total active tickets:  <c:out value="${sessMan.regTicketCount}"/><br/>
          Total waiting list tickets: <c:out value="${sessMan.waitingTicketCount}"/>
        </p>
        <p class="left">
          Max tickets allowed for event: <c:out value="${sessMan.currEvent.maxTickets}"/><br/>
          Max tickets allowed per user: <c:out value="${sessMan.currEvent.maxTicketsPerUser}"/>
        </p>
        <p>
          Registration starts: <c:out value="${sessMan.currEvent.registrationStart}"/><br/>
          Registration ends: <c:out value="${sessMan.currEvent.registrationEnd}"/>
        </p>
        <form name="adminagenda" action="">
          <c:if test="${sessMan.message != null and sessMan.message != ''}">
            <div id="message">
              <c:out value="${sessMan.message}"/>
            </div>
          </c:if>
          <table id="agenda" cellspacing="2">
            <tr>
              <th>
                Ticket ID
              </th>
              <th>
                UserID
              </th>
              <th>
                Tickets
              </th>
              <th>
                Type
              </th>
              <th>
                Comment
              </th>
              <th>
                Created
              </th>
              <th>
              </th>
            </tr>
            <c:forEach var="reg" items="${regs}" varStatus="loopStatus">
              <c:choose>
                <c:when test="${reg.type eq 'waiting'}">
                  <tr class="adminWaitList">
                </c:when>
                <c:when test='${(loopStatus.index)%2 eq 0}'>
                  <tr class="b">
                </c:when>
                <c:otherwise>
                  <tr class="a">
                </c:otherwise>
              </c:choose>
                <td class="regTicketId">
                  <c:out value="${reg.ticketid}"/>
                </td>
                <td class="regEmail">
                  <c:out value="${reg.authid}"/>
                </td>
                <td class="regNumTickets">
                  <input name='numtickets<c:out value="${reg.ticketid}"/>' id='numtickets<c:out value="${reg.ticketid}"/>' type="text" value='<c:out value="${reg.numTickets}"/>' size="3"/>
                </td>
                <td class="regType">
                  <select name='type<c:out value="${reg.ticketid}"/>' id='type<c:out value="${reg.ticketid}"/>'>
                    <option value="registered" ${reg.type == 'registered' ? 'selected="selected"' : ''}>registered</option>
                    <option value="hold" ${reg.type == 'hold' ? 'selected="selected"' : ''}>hold</option>
                    <option value="waiting" ${reg.type == 'waiting' ? 'selected="selected"' : ''}>waiting</option>
                  </select>
                </td>
                <td class="regComment">
                  <input name='comment<c:out value="${reg.ticketid}"/>' id='comment<c:out value="${reg.ticketid}"/>' type="text" value='<c:out value="${reg.comment}"/>'/>
                </td>
                <td class="regCreated">
                  <c:out value="${reg.created}"/>
                </td>
                <td class="regControls">
                  <a href="javascript:doUpdateSuTicket('<c:out value="${reg.ticketid}"/>')" onclick="return confirmUpdateAdminTicket()">update</a> |
                  <a href='removeAgendaTicket.do?id=<c:out value="${reg.ticketid}"/>' onclick="return confirmRemoveAdminTicket('<c:out value="${reg.authid}"/>')">remove</a>
                </td>
              </tr>
            </c:forEach>
          </table>
        </form>
      </div>
    </c:if>

 <%@ include file="foot.jsp" %>