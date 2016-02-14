package cz.morosystems.education.vaadinexampleapp.views.device.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

import cz.morosystems.education.vaadinexampleapp.managers.DeviceManager;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * 
 * Delete device and navigate to list of devices.
 * 
 * @author radoslav.kuzma
 * 
 */
public class DeleteActionListener implements Button.ClickListener {


    private static final long serialVersionUID = 2485270555146172114L;

    private DeviceManager deviceManager;
    private String deviceId;
    private Navigator navigator;

    public DeleteActionListener(DeviceManager deviceManager, Navigator navigator, String deviceId) {
        this.deviceId = deviceId;
        this.navigator = navigator;
        this.deviceManager = deviceManager;
    }

    @Override
    public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {

        deviceManager.deleteDevice(deviceId);
        navigator.navigateTo(MainNavigator.USER_VIEW + "/");

    }
}
