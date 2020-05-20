package com.sap.ibso.ato.training.tools.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Advertisement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;

	public Advertisement() {

	}

	public Advertisement(Long id, String title) {
		this();
		this.id = id;
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
		  return true;
		}
		if (o == null || getClass() != o.getClass()) {
		  return false;
		}
		Advertisement that = (Advertisement) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(title, that.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}

	@Override
	public String toString() {
		return "Advertisement{" +
				"id=" + id +
				", title='" + title + '\'' +
				'}';
	}
}
