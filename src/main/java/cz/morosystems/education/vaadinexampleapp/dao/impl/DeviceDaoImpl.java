package cz.morosystems.education.vaadinexampleapp.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.morosystems.education.vaadinexampleapp.dao.DeviceDao;
import cz.morosystems.education.vaadinexampleapp.entities.Device;
import cz.morosystems.education.vaadinexampleapp.entities.User;

/**
 * 
 * Class for manipulating device data.
 * 
 * @author radoslav.kuzma
 * 
 */
@Repository("deviceDao")
public class DeviceDaoImpl implements DeviceDao {

	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * 
	 * Get device entity by primary key.
	 * 
	 * @param id
	 * primary the device entity
	 * @return device entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Device findDeviceById(final String id) {
		Device result = hibernateTemplate.execute(new HibernateCallback<Device>() {

			@Override
			public Device doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer hql = new StringBuffer();
				hql.append("FROM Device WHERE device_id = :id");
				Query query = session.createQuery(hql.toString());
				query.setString("id", id);
				return (Device) query.uniqueResult();
			}
		});

		return result;
	}

	/**
	 * 
	 * Insert device entity.
	 * 
	 * @param device
	 * entity
	 */
	@Override
	@Transactional
	public void save(final Device device) {
		hibernateTemplate.execute(new HibernateCallback<Void>() {

			@Override
			public Void doInHibernate(Session session) throws HibernateException, SQLException {
				session.save(device);
				return null;
			}
		});
	}

	/**
	 * 
	 * Remove device entity by primary key.
	 * 
	 * @param device
	 * entity device
	 */
	@Override
	@Transactional
	public void remove(final Device device) {
		hibernateTemplate.execute(new HibernateCallback<Void>() {

			@Override
			public Void doInHibernate(Session session) throws HibernateException, SQLException {
				session.delete(device);
				return null;
			}
		});
	}

	/**
	 * 
	 * List of devices for user entity.
	 * 
	 * @param user
	 * entity user
	 * @return list of devices
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Device> findDevicesByUser(final User user) {
		@SuppressWarnings("unchecked")
		List<Device> result = hibernateTemplate.executeFind(new HibernateCallback<List<Device>>() {

			@Override
			public List<Device> doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer hql = new StringBuffer();
				hql.append("FROM Device record WHERE record.users = :user");
				Query query = session.createQuery(hql.toString());
				query.setEntity("user", user);
				return query.list();
			}
		});

		return result;
	}

	/**
	 * 
	 * Update device entity for user entity.
	 * 
	 * @param device
	 * entity device
	 */
	@Override
	@Transactional
	public void update(final Device device) {
		hibernateTemplate.execute(new HibernateCallback<Void>() {

			@Override
			public Void doInHibernate(Session session) throws HibernateException, SQLException {

				session.update(device);
				return null;
			}
		});
	}

}
