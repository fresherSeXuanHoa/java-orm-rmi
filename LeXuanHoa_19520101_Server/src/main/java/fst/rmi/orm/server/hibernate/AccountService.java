package fst.rmi.orm.server.hibernate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import fst.rmi.orm.server.entity.Account;
import fst.rmi.orm.server.repository.AccountRepository;
import fst.rmi.orm.server.util.HibernateUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class AccountService extends UnicastRemoteObject implements AccountRepository {

	private static final long serialVersionUID = 1617126661667824734L;

	private Session session;
	private CriteriaBuilder criterBuilder;

	public AccountService() throws RemoteException {
		session = HibernateUtils.getSessionFactory().openSession();
		criterBuilder = session.getCriteriaBuilder();
	}

	public Account save(Account account) throws RemoteException {
		try {
			session.getTransaction().begin();
			session.persist(account);
			session.getTransaction().commit();
			return account;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public List<Account> findAll() throws RemoteException {
		try {
			CriteriaQuery<Account> criteriaQuery = criterBuilder.createQuery(Account.class);
			Root<Account> root = criteriaQuery.from(Account.class);
			criteriaQuery.select(root);
			return session.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Account findByUserName(String username) throws RemoteException {
		try {
			CriteriaQuery<Account> criteriaQuery = criterBuilder.createQuery(Account.class);
			Root<Account> root = criteriaQuery.from(Account.class);
			criteriaQuery.select(root).where(criterBuilder.equal(root.get("username"), username));
			return session.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Account update(Account account) throws RemoteException {
		try {
			session.getTransaction().begin();
			Account result = findByUserName(account.getUsername());
			if (result != null) {
				session.merge(account);
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
	public Account delete(String username) throws RemoteException {
		try {
			session.getTransaction().begin();
			Account account = findByUserName(username);
			if (account != null) {
				session.delete(account);
			}
			session.getTransaction().commit();
			return account;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}
}
