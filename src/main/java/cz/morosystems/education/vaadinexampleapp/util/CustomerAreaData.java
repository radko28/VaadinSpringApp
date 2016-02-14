package cz.morosystems.education.vaadinexampleapp.util;

import cz.morosystems.education.vaadinexampleapp.entities.CustomerAreaType;


public class CustomerAreaData {

    private String name;
    private CustomerAreaType customerAreaType;


    public CustomerAreaData(String name, CustomerAreaType customerAreaType) {
        this.name = name;
        this.customerAreaType = customerAreaType;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public CustomerAreaType getCustomerAreaType() {
        return customerAreaType;
    }


    public void setCustomerAreaType(CustomerAreaType customerAreaType) {
        this.customerAreaType = customerAreaType;
    }

}
