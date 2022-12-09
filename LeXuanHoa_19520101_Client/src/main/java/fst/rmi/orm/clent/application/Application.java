package fst.rmi.orm.clent.application;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

import fst.rmi.orm.server.entity.Account;
import fst.rmi.orm.server.entity.Role;
import fst.rmi.orm.server.repository.AccountRepository;
import fst.rmi.orm.server.repository.ComputerRepository;
import fst.rmi.orm.server.repository.EmployeeRepository;
import fst.rmi.orm.server.repository.RoleRepository;

public class Application {
	public static void main(String[] args) {
		String RMI_SERVER_URI = null;
		final int RMI_SERVER_PORT = 8080;
		try {
			RMI_SERVER_URI = InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException e1) {
			e1.printStackTrace();
		}
		System.err.println("Client connected to server in address: " + RMI_SERVER_URI + ":" + RMI_SERVER_PORT);

		AccountRepository accountService = null;
		EmployeeRepository employeeService = null;
		ComputerRepository computerService = null;
		RoleRepository roleSevice = null;

		try {
			accountService = (AccountRepository) Naming
					.lookup("rmi://" + RMI_SERVER_URI + ":" + RMI_SERVER_PORT + "/accountService");
			if (accountService != null) {
				System.out.println("Get accountService...");
			}

			employeeService = (EmployeeRepository) Naming
					.lookup("rmi://" + RMI_SERVER_URI + ":" + RMI_SERVER_PORT + "/employeeService");
			if (employeeService != null) {
				System.out.println("Get employeeService...");
			}

			computerService = (ComputerRepository) Naming
					.lookup("rmi://" + RMI_SERVER_URI + ":" + RMI_SERVER_PORT + "/computerService");
			if (computerService != null) {
				System.out.println("Get computerService...");
			}

			roleSevice = (RoleRepository) Naming
					.lookup("rmi://" + RMI_SERVER_URI + ":" + RMI_SERVER_PORT + "/roleService");
			if (computerService != null) {
				System.out.println("Get roleService...");
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}

		Account account = new Account("fresherSeXuanHoa", "fresherSeXuanHoa");
		Account otherAccount = new Account("internSeXuanHoa", "internSeXuanHoa");

		Role role = new Role("Write", Arrays.asList(account, account));
		Role otheRole = new Role("Read", Arrays.asList(account));

		try {
			roleSevice.save(role);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
