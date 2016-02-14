package cz.morosystems.education.vaadinexampleapp.managers.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cz.morosystems.education.vaadinexampleapp.dao.DeviceDao;
import cz.morosystems.education.vaadinexampleapp.dao.UserDao;
import cz.morosystems.education.vaadinexampleapp.entities.CellPhone;
import cz.morosystems.education.vaadinexampleapp.entities.Computer;
import cz.morosystems.education.vaadinexampleapp.entities.Device;
import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.managers.DeviceManager;
import cz.morosystems.education.vaadinexampleapp.util.DeviceType;
import cz.morosystems.education.vaadinexampleapp.util.DeviceView;

/**
 * DeviceManager interface implementation.
 * 
 * @author radoslav.kuzma
 * 
 */
@Component
@Scope("prototype")
public class DeviceManagerImpl implements DeviceManager, Serializable {

    /** Spring component scan injects DeviceDao bean */
    @Autowired
    DeviceDao deviceDao;
    /** Spring component scan injects UserDao bean */
    @Autowired
    UserDao userDao;

    /**
     * Calls DeviceDao findDeviceById to execute an HQL query and return device, specified by ID.
     * 
     * @param id
     * 
     * @return Device entity
     */
    @Override
    public Device findDeviceById(String id) {
        return deviceDao.findDeviceById(id);
    }
    /**
     * Finds user by UserDao, specified by username to set device user. Persists Device entity.
     * 
     * @param device
     *            new Device entity
     * @param username
     *            the string, user's username
     */
    @Override
    public void save(Device device, String username) {
        User user = userDao.findUserByUserName(username);
        device.setUsers(user);
        device.setCreated(new org.joda.time.DateTime(new Date()));
        deviceDao.save(device);
    }
    /**
     * Calls DeviceDao to remove device record.
     * 
     * @param device
     *            Computer or CellPhone entity to be removed
     */
    @Override
    public void remove(Device device) {
        deviceDao.remove(device);

    }
    /**
     * Finds user, specified by username by UserDao. Calls findDevicesByUser to get and return user's device list.
     * 
     * @param username
     *            the string, user's username
     * @return list of user's devices
     */
    @Override
    public List<DeviceView> findDevicesByUserName(String username) {
        User user = userDao.findUserByUserName(username);
        List<DeviceView> result = findDevicesByUser(user);
        return result;
    }
    /**
     * Gets list of devices by DeviceDao findDevicesByUser. Creates DeviceView list and fills it. Returns sorted DeviceView list.
     * 
     * @param user
     *            User entity
     * @return DeviceView list
     */
    @Override
    public List<DeviceView> findDevicesByUser(User user) {
        List<DeviceView> result = new ArrayList<DeviceView>();
        List<Device> devicelist = deviceDao.findDevicesByUser(user);

        DeviceView dv = null;
        for (Device item : devicelist) {

            dv = new DeviceView();
            dv.setCreated(item.getCreated());
            dv.setDeviceId(item.getDeviceId());
            dv.setName(item.getName());
            if (item instanceof CellPhone) {
                dv.setNotes(((CellPhone)item).getPhoneNumber());
                dv.setDeviceType(DeviceType.CELL_PHONE.toString());

            } else {
                dv.setNotes("");
                dv.setDeviceType(((Computer)item).getComputerType().toString());
            }
            result.add(dv);
        }

        Collections.sort(result);
        return result;

    }
    /**
     * Uses DeviceDao findDeviceById to get device and it's user. Calls remove method to delete device. Calls findDevicesByUser to return
     * user's devices.
     */
    @Override
    public void deleteDevice(String id) {
        Device device = deviceDao.findDeviceById(id);
        remove(device);
    }
    /**
     * Uses userDao to find user, specified by username, sets this user in device. Updates device by DeviceDao update
     * 
     * @param device
     *            Device entity to be updated
     * @param username
     *            the string
     */
    @Override
    public void update(Device device, String username) {
        User user = userDao.findUserByUserName(username);
        device.setUsers(user);
        Device record = deviceDao.findDeviceById(device.getDeviceId());
        device.setCreated(record.getCreated());
        deviceDao.update(device);
    }


}
