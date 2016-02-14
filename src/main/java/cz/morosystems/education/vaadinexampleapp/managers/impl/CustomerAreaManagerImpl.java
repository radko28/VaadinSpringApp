package cz.morosystems.education.vaadinexampleapp.managers.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cz.morosystems.education.vaadinexampleapp.dao.CustomerAreaDao;
import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;
import cz.morosystems.education.vaadinexampleapp.managers.CustomerAreaManager;

@Component
@Scope("prototype")
public class CustomerAreaManagerImpl implements CustomerAreaManager {

    @Autowired
    CustomerAreaDao customerAreaDao;

    @Override
    public List<CustomerArea> findCustomerAreas() {
        return customerAreaDao.findAll();
    }

    @Override
    public void deleteCustomerArea(String id) {
        CustomerArea customerArea = findCustomerAreaById(id);
        customerAreaDao.remove(customerArea);
    }

    @Override
    public CustomerArea findCustomerAreaById(String id) {
        return customerAreaDao.findCustomerAreaById(id);
    }

    @Override
    public void save(CustomerArea customerArea) {
        customerArea.setCreated(new org.joda.time.DateTime(new Date()));
        customerAreaDao.save(customerArea);
    }

    @Override
    public void update(CustomerArea customerArea) {
        customerAreaDao.update(customerArea);
    }

}
