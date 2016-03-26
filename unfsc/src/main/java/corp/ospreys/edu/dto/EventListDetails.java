package corp.ospreys.edu.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "eventList")
public class EventListDetails {
	
	private List<EventDetails> eventList;

	/**
	 * @return the eventList
	 */
	public List<EventDetails> getEventList() {
		return eventList;
	}

	/**
	 * @param eventList the eventList to set
	 */
	public void setEventList(List<EventDetails> eventList) {
		this.eventList = eventList;
	}
	
	
	
}


