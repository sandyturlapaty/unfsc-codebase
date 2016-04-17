/**
 * 
 */
package corp.ospreys.edu.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dao.EventDetailsDao;
import corp.ospreys.edu.dto.EventDetails;
import corp.ospreys.edu.dto.EventListDetails;
import corp.ospreys.edu.service.EventDetailsService;
import corp.ospreys.edu.util.Emailer;

/**
 * @author sandyturlapaty
 *
 */
@Service("eventDetailsService")
public class EventDetailsServiceImpl implements EventDetailsService {
	
	@Autowired
	private EventDetailsDao eventDetailsDao;

	@Override
	public EventListDetails retrieveEventById(String idValue, String idType) {
		EventListDetails details = new EventListDetails();
		List<EventDetails> eventList = eventDetailsDao.retrieveEventById(idValue, idType);
		details.setEventList(eventList);
		return details;
	}

	@Override
	public String createEvent(EventDetails event) {
		try {
			eventDetailsDao.createEvent(event);
			if(StringUtils.isNotEmpty(event.getPublicInd()) && "yes".equalsIgnoreCase(event.getPublicInd())){
				Emailer.triggerEmail("New Event Request", "Name : "+event.getEventName()+ "  Event Location : "+event.getEventLocation());
			}
			return "201";
		} catch (Exception e){
			return "400";
		}
	}
	
	@Override
	public String subscribeEvent(EventDetails event) {
		try {
			eventDetailsDao.subscribeEvent(event);
			return "201";
		} catch (Exception e){
			return "400";
		}
	}

	@Override
	public String approveEvent(String eventId) {
	String x;
	try {
		x = eventDetailsDao.approveEvent(eventId);
	} catch (SQLException e) {
		e.printStackTrace();
		x="failure";
	}
		return x;
	}
	
	
}
