package fst.rmi.orm.server.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 2739647883304632620L;

	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn()
	private Account account;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "computerName")
	private List<Computer> computers;

	public Employee(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}

	public Employee() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", address=" + address + "]";
	}
}
