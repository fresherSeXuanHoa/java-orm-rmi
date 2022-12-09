package fst.rmi.orm.clent.application;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import fst.rmi.orm.server.entity.Account;
import fst.rmi.orm.server.entity.Employee;
import fst.rmi.orm.server.repository.AccountRepository;
import fst.rmi.orm.server.repository.EmployeeRepository;

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

		try {
			accountService = (AccountRepository) Naming
					.lookup("rmi://" + RMI_SERVER_URI + ":" + RMI_SERVER_PORT + "/accountService");
			employeeService = (EmployeeRepository) Naming
					.lookup("rmi://" + RMI_SERVER_URI + ":" + RMI_SERVER_PORT + "/employeeService");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}

		Employee employee = new Employee("internSeXuanHoa", "internSeXuanHoa");
		Account account = new Account("internSeXuanHoa", "interSeXuanHoa", employee);
		try {
			accountService.save(account);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
