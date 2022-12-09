package fst.rmi.orm.server.hibernate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import fst.rmi.orm.server.entity.Role;
import fst.rmi.orm.server.repository.RoleRepository;
import fst.rmi.orm.server.util.HibernateUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class RoleService extends UnicastRemoteObject implements RoleRepository {

	private static final long serialVersionUID = -7759489518539694295L;
	private Session session;
	private CriteriaBuilder criterBuilder;

	public RoleService() throws RemoteException {
		session = HibernateUtils.getSessionFactory().openSession();
		criterBuilder = session.getCriteriaBuilder();
	}

	public Role save(Role role) throws RemoteException {
		try {
			session.getTransaction().begin();
			session.persist(role);
			session.getTransaction().commit();
			return role;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public List<Role> findAll() throws RemoteException {
		try {
			CriteriaQuery<Role> criteriaQuery = criterBuilder.createQuery(Role.class);
			Root<Role> root = criteriaQuery.from(Role.class);
			criteriaQuery.select(root);
			return session.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Role findByName(String name) throws RemoteException {
		try {
			CriteriaQuery<Role> criteriaQuery = criterBuilder.createQuery(Role.class);
			Root<Role> root = criteriaQuery.from(Role.class);
			criteriaQuery.select(root).where(criterBuilder.equal(root.get("name"), name));
			return session.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Role update(Role role) throws RemoteException {
		try {
			session.getTransaction().begin();
			Role result = findByName(role.getRoleName());
			if (result != null) {
				session.merge(role);
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
	public Role delete(String name) throws RemoteException {
		try {
			session.getTransaction().begin();
			Role role = findByName(name);
			if (role != null) {
				session.delete(role);
			}
			session.getTransaction().commit();
			return role;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}
}
