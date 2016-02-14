package cz.morosystems.education.vaadinexampleapp.dao;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.Device;
import cz.morosystems.education.vaadinexampleapp.entities.User;

/**
 * 
 * Interface for manipulating device data.
 * 
 * @author radoslav.kuzma
 * 
 */
public interface DeviceDao {

    /**
     * 
     * Get device entity by primary key.
     * 
     * @param id
     *            primary the device entity
     * @return device entity
     */
    public Device findDeviceById(String id);

    /**
     * 
     * Insert device entity.
     * 
     * @param device
     *            entity
     */
    public void save(Device device);

    /**
     * 
     * Remove device entity by primary key.
     * 
     * @param device
     *            entity device
     */
    public void remove(Device device);
    /**
     * 
     * List of devices for user entity.
     * 
     * @param user
     *            entity user
     * @return list of devices
     */
    public List<Device> findDevicesByUser(User user);
    /**
     * 
     * Update device entity for user entity.
     * 
     * @param device
     *            entity device
     */
    public void update(Device device);
}
