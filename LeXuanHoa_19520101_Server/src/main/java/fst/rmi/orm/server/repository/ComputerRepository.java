package fst.rmi.orm.server.repository;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import fst.rmi.orm.server.entity.Computer;

public interface ComputerRepository extends Remote {
	List<Computer> findAll() throws RemoteException;

	Computer findByName(String name) throws RemoteException;

	Computer save(Computer computer) throws RemoteException;

	Computer update(Computer computer) throws RemoteException;

	Computer delete(String name) throws RemoteException;
}
