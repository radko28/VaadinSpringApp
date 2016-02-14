package cz.morosystems.education.vaadinexampleapp.managers;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;


public interface CustomerAreaManager {

    public List<CustomerArea> findCustomerAreas();

    public void deleteCustomerArea(String id);

    public CustomerArea findCustomerAreaById(String string);

    public void save(CustomerArea customerArea);

    public void update(CustomerArea customerArea);


}
