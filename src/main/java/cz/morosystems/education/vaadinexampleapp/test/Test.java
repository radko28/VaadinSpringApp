package cz.morosystems.education.vaadinexampleapp.test;


import java.util.Date;

import org.hibernate.Session;

import cz.morosystems.education.vaadinexampleapp.entities.Authority;
import cz.morosystems.education.vaadinexampleapp.entities.CellPhone;
import cz.morosystems.education.vaadinexampleapp.entities.Computer;
import cz.morosystems.education.vaadinexampleapp.entities.ComputerType;
import cz.morosystems.education.vaadinexampleapp.entities.RoleType;
import cz.morosystems.education.vaadinexampleapp.entities.User;


public class Test {

    public static void main(String[] args) {
        Test mgr = new Test();

        mgr.createAndStoreEvent();

        Util.getSessionFactory().close();
    }

    private void createAndStoreEvent() {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        // admin
        User user2 = new User();
        user2.setEnabled(true);
        user2.setFirstname("Radoslav");
        user2.setLastname("Kuzma");
        user2.setUsername("user");
        user2.setUserId("1977");
        user2.setPassword("user");
        user2.setCreated(new org.joda.time.DateTime(new Date()));
        user2.setBirthdate(new org.joda.time.DateTime(new Date()));
        // user2.setEnabled(true);


        session.save(user2);

        // user

        User user = new User();
        user.setEnabled(true);
        user.setFirstname("Radoslav");
        user.setLastname("Kuzma");
        user.setUsername("admin");
        user.setUserId("1977");
        user.setPassword("admin");
        user.setCreated(new org.joda.time.DateTime(new Date()));
        user.setBirthdate(new org.joda.time.DateTime(new Date()));
        // user.setEnabled(true);


        session.save(user);


        Authority auth = new Authority();
        auth.setAuthority(RoleType.ROLE_USER);
        auth.setUsername(user.getUsername());
        auth.setUsers(user);
        session.save(auth);


        Authority auth2 = new Authority();
        auth2.setAuthority(RoleType.ROLE_ADMIN);
        auth2.setUsername(user2.getUsername());
        auth2.setUsers(user2);
        session.save(auth2);


        CellPhone device = new CellPhone();
        device.setCreated(new org.joda.time.DateTime(new Date()));
        device.setDeviceId("1976");
        device.setName("sonyericson");
        device.setPhoneNumber("0911760775");


        device.setUsers(user2);
        session.save(device);

        Computer device2 = new Computer();
        device2.setCreated(new org.joda.time.DateTime(new Date()));
        device2.setDeviceId("1978");
        device2.setName("dell");
        device2.setComputerType(ComputerType.NOTEBOOK);

        device2.setUsers(user2);
        session.save(device2);

        /*
         * 
         * 
         * 
         * Computer device3 = new Computer(); device3.setCreated(new org.joda.time.DateTime(new Date())); device3.setDeviceId("1979");
         * device3.setName("siemens"); device3.setComputerType(ComputerType.NOTEBOOK);
         * 
         * device3.setUsers(user); session.save(device3);
         */
        session.getTransaction().commit();
    }
}
