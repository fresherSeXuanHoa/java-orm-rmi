package fst.rmi.orm.server.hibernate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import fst.rmi.orm.server.entity.Computer;
import fst.rmi.orm.server.repository.ComputerRepository;
import fst.rmi.orm.server.util.HibernateUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ComputerService extends UnicastRemoteObject implements ComputerRepository {

	private static final long serialVersionUID = 6635030898888424203L;
	private Session session;
	private CriteriaBuilder criterBuilder;

	public ComputerService() throws RemoteException {
		session = HibernateUtils.getSessionFactory().openSession();
		criterBuilder = session.getCriteriaBuilder();
	}

	public Computer save(Computer computer) throws RemoteException {
		try {
			session.getTransaction().begin();
			session.persist(computer);
			session.getTransaction().commit();
			return computer;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public List<Computer> findAll() throws RemoteException {
		try {
			CriteriaQuery<Computer> criteriaQuery = criterBuilder.createQuery(Computer.class);
			Root<Computer> root = criteriaQuery.from(Computer.class);
			criteriaQuery.select(root);
			return session.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Computer findByName(String name) throws RemoteException {
		try {
			CriteriaQuery<Computer> criteriaQuery = criterBuilder.createQuery(Computer.class);
			Root<Computer> root = criteriaQuery.from(Computer.class);
			criteriaQuery.select(root).where(criterBuilder.equal(root.get("name"), name));
			return session.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Computer update(Computer computer) throws RemoteException {
		try {
			session.getTransaction().begin();
			Computer result = findByName(computer.getName());
			if (result != null) {
				session.merge(computer);
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
	public Computer delete(String name) throws RemoteException {
		try {
			session.getTransaction().begin();
			Computer computer = findByName(name);
			if (computer != null) {
				session.delete(computer);
			}
			session.getTransaction().commit();
			return computer;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}
}
