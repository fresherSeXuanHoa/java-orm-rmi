package fst.rmi.orm.server.hibernate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import fst.rmi.orm.server.entity.Employee;
import fst.rmi.orm.server.repository.EmployeeRepository;
import fst.rmi.orm.server.util.HibernateUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class EmployeeService extends UnicastRemoteObject implements EmployeeRepository {

	private static final long serialVersionUID = -3166943347247974457L;
	private Session session;
	private CriteriaBuilder criterBuilder;

	public EmployeeService() throws RemoteException {
		session = HibernateUtils.getSessionFactory().openSession();
		criterBuilder = session.getCriteriaBuilder();
	}

	public Employee save(Employee employee) throws RemoteException {
		try {
			session.getTransaction().begin();
			session.persist(employee);
			session.getTransaction().commit();
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public List<Employee> findAll() throws RemoteException {
		try {
			CriteriaQuery<Employee> criteriaQuery = criterBuilder.createQuery(Employee.class);
			Root<Employee> root = criteriaQuery.from(Employee.class);
			criteriaQuery.select(root);
			return session.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Employee findByName(String name) throws RemoteException {
		try {
			CriteriaQuery<Employee> criteriaQuery = criterBuilder.createQuery(Employee.class);
			Root<Employee> root = criteriaQuery.from(Employee.class);
			criteriaQuery.select(root).where(criterBuilder.equal(root.get("name"), name));
			return session.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Employee update(Employee employee) throws RemoteException {
		try {
			session.getTransaction().begin();
			Employee result = findByName(employee.getName());
			if (result != null) {
				session.merge(employee);
			}
			session.getTransaction().commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public Employee delete(String name) throws RemoteException {
		try {
			session.getTransaction().begin();
			Employee employee = findByName(name);
			if (employee != null) {
				session.delete(employee);
			}
			session.getTransaction().commit();
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}
}
