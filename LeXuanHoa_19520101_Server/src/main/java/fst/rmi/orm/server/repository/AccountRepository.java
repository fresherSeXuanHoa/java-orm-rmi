package fst.rmi.orm.server.repository;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import fst.rmi.orm.server.entity.Account;

public interface AccountRepository extends Remote {
	List<Account> findAll() throws RemoteException;

	Account findByUserName(String username) throws RemoteException;

	Account save(Account account) throws RemoteException;

	Account update(Account account) throws RemoteException;

	Account delete(String username) throws RemoteException;
}
