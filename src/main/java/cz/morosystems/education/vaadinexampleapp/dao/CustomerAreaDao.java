package cz.morosystems.education.vaadinexampleapp.dao;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;
import cz.morosystems.education.vaadinexampleapp.entities.CustomerAreaType;


public interface CustomerAreaDao {

    public List<CustomerArea> findAll();

    public CustomerArea findCustomerAreaByType(CustomerAreaType customerAreaType);

    public CustomerArea findCustomerAreaById(String id);

    public void remove(CustomerArea customerArea);

    public void save(CustomerArea customerArea);

    public void update(CustomerArea customerArea);


}
