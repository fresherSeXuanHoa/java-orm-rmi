package fst.rmi.orm.server.repository;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import fst.rmi.orm.server.entity.Employee;

public interface EmployeeRepository extends Remote {
	List<Employee> findAll() throws RemoteException;

	Employee findByName(String name) throws RemoteException;

	Employee save(Employee employee) throws RemoteException;

	Employee update(Employee employee) throws RemoteException;

	Employee delete(String name) throws RemoteException;
}
