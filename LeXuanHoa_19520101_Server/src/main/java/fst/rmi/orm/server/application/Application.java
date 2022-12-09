package fst.rmi.orm.server.application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import fst.rmi.orm.server.hibernate.AccountService;
import fst.rmi.orm.server.hibernate.ComputerService;
import fst.rmi.orm.server.hibernate.EmployeeService;
import fst.rmi.orm.server.hibernate.RoleService;

public class Application {
	public static void main(String[] args) {
		String RMI_SERVER_URL = null;
		final int RMI_SERVER_PORT = 8080;
		try {
			RMI_SERVER_URL = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		try {
			LocateRegistry.createRegistry(RMI_SERVER_PORT);

			Naming.bind("rmi://" + RMI_SERVER_URL + ":" + RMI_SERVER_PORT + "/accountService", new AccountService());
			Naming.bind("rmi://" + RMI_SERVER_URL + ":" + RMI_SERVER_PORT + "/employeeService", new EmployeeService());
			Naming.bind("rmi://" + RMI_SERVER_URL + ":" + RMI_SERVER_PORT + "/computerService", new ComputerService());
			Naming.bind("rmi://" + RMI_SERVER_URL + ":" + RMI_SERVER_PORT + "/roleService", new RoleService());

			System.err.println("RMI Server is running in address: " + RMI_SERVER_URL + ":" + RMI_SERVER_PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
