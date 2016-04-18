package corp.ospreys.edu.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "event")
public class EventDetails {
	
	private String eventName;
	private String eventLocation;
	private String eventTime;
	private String eventOwner;
	private String eventDescription;
	private String ownerContact ;
	private String userId;
	private String eventDuration;
	private String publicInd;
	private String eventId;
	private String subscribeInd;
	
	/**
	 * @return the subscribeInd
	 */
	public String getSubscribeInd() {
		return subscribeInd;
	}
	/**
	 * @param subscribeInd the subscribeInd to set
	 */
	public void setSubscribeInd(String subscribeInd) {
		this.subscribeInd = subscribeInd;
	}
	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}
	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	/**
	 * @return the eventLocation
	 */
	public String getEventLocation() {
		return eventLocation;
	}
	/**
	 * @param eventLocation the eventLocation to set
	 */
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	/**
	 * @return the eventTime
	 */
	public String getEventTime() {
		return eventTime;
	}
	/**
	 * @param eventTime the eventTime to set
	 */
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	/**
	 * @return the eventOwner
	 */
	public String getEventOwner() {
		return eventOwner;
	}
	/**
	 * @param eventOwner the eventOwner to set
	 */
	public void setEventOwner(String eventOwner) {
		this.eventOwner = eventOwner;
	}
	/**
	 * @return the eventDescription
	 */
	public String getEventDescription() {
		return eventDescription;
	}
	/**
	 * @param eventDescription the eventDescription to set
	 */
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	/**
	 * @return the ownerContact
	 */
	public String getOwnerContact() {
		return ownerContact;
	}
	/**
	 * @param ownerContact the ownerContact to set
	 */
	public void setOwnerContact(String ownerContact) {
		this.ownerContact = ownerContact;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the eventDuration
	 */
	public String getEventDuration() {
		return eventDuration;
	}
	/**
	 * @param eventDuration the eventDuration to set
	 */
	public void setEventDuration(String eventDuration) {
		this.eventDuration = eventDuration;
	}
	
	/**
	 * @return
	 */
	public String getPublicInd() {
		return publicInd;
	}
	
	/**
	 * @param publicInd
	 */
	public void setPublicInd(String publicInd) {
		this.publicInd = publicInd;
	}
	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}
	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
}


