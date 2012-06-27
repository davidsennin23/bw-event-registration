package org.bedework.eventreg.web;

import org.bedework.eventreg.db.Event;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author douglm
 */
public class UpdateTicketController extends AbstractController {
  @Override
  public ModelAndView doRequest(final HttpServletRequest request,
                                final HttpServletResponse response) throws Throwable {
    String ticketId = sessMan.getTicketId();
    int numTickets = sessMan.getTicketsRequested();
    String comment = sessMan.getComment();
    String type = sessMan.getType();

    if (type == null) {
      type = "normal";
    }

    Event currEvent = sessMan.getCurrEvent();

    logger.debug("update ticket - super user type: " + sessMan.getSuperUser());

    if (((numTickets + sessMan.getTicketCount()) <= currEvent.getMaxTickets()) ||
        (numTickets < sessMan.getUserTicketCount()) ||
        sessMan.getSuperUser()) {
      sessMan.updateTicketById(ticketId,
                               numTickets,
                               comment,
                               type);
      sessMan.setMessage("Ticket number " + ticketId + " updated.");
    } else {
      logger.info("registration is full");
      return errorReturn("Registration is full: you may only decrease or remove tickets.");
    }

    if (sessMan.getSuperUser()) {
      return sessModel("forward:suagenda.do");
    }

    return sessModel("forward:agenda.do");
  }
}