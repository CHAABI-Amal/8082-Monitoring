package com.chaabiamal.springboot_cassandra_demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "ID",
		scope = Status.class)
@Table(value = "status")

public class Status implements Serializable {
	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
	@Column("id")
	@CassandraType(type = CassandraType.Name.UUID)
	private int ID;
/*			ID  VALUE
			1	Working
			2	Warning
			3	Faulty
			4	Unknown
			5	Disconnected
*/

	@Column("value")
	@CassandraType(type = CassandraType.Name.TEXT)
	private String Value;// string ula hadi????

	@Column("description")
	@CassandraType(type = CassandraType.Name.TEXT)
	private String Description;


//*******************************************


	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}


}
