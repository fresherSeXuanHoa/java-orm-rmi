package fst.rmi.orm.server.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "computer")
public class Computer implements Serializable {

	private static final long serialVersionUID = 6665843347328326763L;

	@Id
	@Column(name = "computer_name")
	private String computerName;

	@Column(name = "manufacturer")
	private String manufacturer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_name")
	private Employee employee;

	public Computer(String computerName, String manufacturer) {
		super();
		this.computerName = computerName;
		this.manufacturer = manufacturer;
	}

	public Computer(String computerName, String manufacturer, Employee employee) {
		super();
		this.computerName = computerName;
		this.manufacturer = manufacturer;
		this.employee = employee;
	}

	public Computer() {
		super();
	}

	public String getName() {
		return computerName;
	}

	public void setName(String computerName) {
		this.computerName = computerName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Computer(Employee employee) {
		super();
		this.employee = employee;
	}
}
