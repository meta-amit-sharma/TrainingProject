package com.metacube.springapi.vehicle;

import java.sql.Clob;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;

import com.metacube.springapi.vehicle.enums.UseCaseName;

/**
 * Class Vehicle which is an entity
 * a table is created with this class's name and 
 * have it's data member as it's column name
 * @author Amit Sharma
 *
 */
@Entity
public class Vehicle {

	/*primary key*/
	@Id
	private Date date;
	
	/*check constraint*/
	@Size(min=17, max=17)
	private String vin;
	
	@Size(min=0, max=10)
	private String messageId;
	
	@Size(min=0, max=10)
	private String action;
	
	@Size(min=0, max=10)
	private String sourceId;
	
	@Size(min=0, max=10)
	private String trackingId;
	
	@NotNull
	private String useCaseName;
	
	private int maintenanceGapToService;
	private int maintenanceServiceDue;
	private int maintenanceGapType;
	
	/*To store large Xml files*/
	private Clob xml;
	

	/**
	 * Constructor for this class
	 * @param vin
	 * @param messageId
	 * @param action
	 * @param sourceId
	 * @param trackingId
	 * @param useCaseName
	 * @param maintenanceGapToService
	 * @param maintenanceServiceDue
	 * @param maintenanceGapType
	 * @param xml
	 */
	
	
	/*Getters and Setters for all data members of this class*/
	
	public Clob getXml() {
		return xml;
	}

	public Vehicle(@Size(min = 17, max = 17) String vin, @Size(min = 0, max = 10) String messageId,
			@Size(min = 0, max = 10) String action, @Size(min = 0, max = 10) String sourceId,
			@Size(min = 0, max = 10) String trackingId, @NotNull String useCaseName, int maintenanceGapToService,
			int maintenanceServiceDue, int maintenanceGapType, Clob xml) {
		this.date = new Date();
		this.vin = vin;
		this.messageId = messageId;
		this.action = action;
		this.sourceId = sourceId;
		this.trackingId = trackingId;
		this.useCaseName = useCaseName;
		this.maintenanceGapToService = maintenanceGapToService;
		this.maintenanceServiceDue = maintenanceServiceDue;
		this.maintenanceGapType = maintenanceGapType;
		this.xml = xml;
	}

	public void setXml(Clob xml) {
		this.xml = xml;
	}

	public Vehicle() {}

	
	public Vehicle(Date date,@Size(min = 17, max = 17) String vin, @Size(min = 0, max = 10) String messageId,
			@Size(min = 0, max = 10) String action, @Size(min = 0, max = 10) String sourceId,
			@Size(min = 0, max = 10) String trackingId, @NotNull String useCaseName, int maintenanceGapToService,
			int maintenanceServiceDue, int maintenanceGapType, Clob xml) {
		this.date = date;
		this.vin = vin;
		this.messageId = messageId;
		this.action = action;
		this.sourceId = sourceId;
		this.trackingId = trackingId;
		this.useCaseName = useCaseName;
		this.maintenanceGapToService = maintenanceGapToService;
		this.maintenanceServiceDue = maintenanceServiceDue;
		this.maintenanceGapType = maintenanceGapType;
		this.xml = xml;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getMaintenanceGapToService() {
		return maintenanceGapToService;
	}

	public void setMaintenanceGapToService(int maintenanceGapToService) {
		this.maintenanceGapToService = maintenanceGapToService;
	}

	public int getMaintenanceServiceDue() {
		return maintenanceServiceDue;
	}

	public void setMaintenanceServiceDue(int maintenanceServiceDue) {
		this.maintenanceServiceDue = maintenanceServiceDue;
	}

	public int getMaintenanceGapType() {
		return maintenanceGapType;
	}

	public void setMaintenanceGapType(int maintenanceGapType) {
		this.maintenanceGapType = maintenanceGapType;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public @NotNull String getUseCaseName() {
		return useCaseName;
	}

	public void setUseCaseName(@NotNull String useCaseName) {
		this.useCaseName = useCaseName;
	}
	
}
