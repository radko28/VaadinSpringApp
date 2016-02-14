package cz.morosystems.education.vaadinexampleapp.util;

import org.joda.time.DateTime;

import cz.morosystems.education.vaadinexampleapp.entities.ComputerType;

/**
 * 
 * Device views in the web page. Format attribute created date and sorting devices.
 * 
 * @author radoslav.kuzma
 * 
 */

public class DeviceView implements Comparable<DeviceView> {


    private String notes;
    private ComputerType computerType;
    private String deviceId;
    private String name;
    private DateTime created;
    private String deviceType;


    public ComputerType getComputerType() {
        return computerType;
    }

    public void setComputerType(ComputerType computerType) {
        this.computerType = computerType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }


    public String getDeviceType() {
        return deviceType;
    }


    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }


    public String getNotes() {
        return notes;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getCreatedString() {
        String createdString = "";
        if (created != null)
            createdString = org.joda.time.format.DateTimeFormat.forPattern(AppHelper.DATE_FORMAT).print(created);
        return createdString;
    }

    @Override
    public int compareTo(DeviceView arg0) {
        int result = -1;
        if (this.created.getMillis() < arg0.created.getMillis()) {
            result = 1;
        } else if (this.created.getMillis() == arg0.created.getMillis()) {
            result = 0;
        }
        return result;
    }

}
