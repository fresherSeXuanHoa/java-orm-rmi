package fst.rmi.orm.server.repository;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import fst.rmi.orm.server.entity.Role;

public interface RoleRepository extends Remote {
	List<Role> findAll() throws RemoteException;

	Role findByName(String username) throws RemoteException;

	Role save(Role Role) throws RemoteException;

	Role update(Role Role) throws RemoteException;

	Role delete(String username) throws RemoteException;
}
