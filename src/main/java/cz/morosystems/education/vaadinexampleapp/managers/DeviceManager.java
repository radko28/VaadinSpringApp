package cz.morosystems.education.vaadinexampleapp.managers;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.Device;
import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.util.DeviceView;

/**
 * Interface used to interact with device business manager
 * 
 * @author radoslav.kuzma
 * 
 */
public interface DeviceManager {


    /**
     * Calls DeviceDao findDeviceById to execute an HQL query and return device, specified by ID.
     * 
     * @param id
     *            the string, ID of device record
     * @return Device entity
     */
    public Device findDeviceById(String id);
    /**
     * Finds user by UserDao, specified by username to set device user. Persists Device entity.
     * 
     * @param device
     *            new Device entity
     * @param username
     *            the string, user's username
     */
    public void save(Device device, String username);
    /**
     * Calls DeviceDao to remove device record.
     * 
     * @param device
     *            Computer or CellPhone entity to be removed
     */
    public void remove(Device device);
    /**
     * Finds user, specified by username by UserDao. Calls findDevicesByUser to get and return user's device list.
     * 
     * @param username
     *            the string, user's username
     * @return list of user's devices
     */
    public List<DeviceView> findDevicesByUserName(String username);
    /**
     * Gets list of devices by DeviceDao findDevicesByUser
     * 
     * @param user
     *            User entity
     * @return user's device list
     */
    public List<DeviceView> findDevicesByUser(User user);
    /**
     * Uses DeviceDao findDeviceById to get device and it's user. Calls remove method to delete device.
     */
    public void deleteDevice(String id);
    /**
     * Uses userDao to find user, specified by username, sets this user in device. Updates device by DeviceDao update
     * 
     * @param device
     *            Device entity to be updated
     * @param username
     *            the string
     */
    public void update(Device device, String username);

}
