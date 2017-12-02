package com.gallelloit.heroeditorbackend.core.dao.doc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gallelloit.heroeditorbackend.core.util.Util;
import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;

/**
 * Represents a user doc from Couchbase
 *
 * @author Charz++
 */

@Document
public class HeroDoc implements Serializable {

	private static final long serialVersionUID = 3072475211055736282L;
	protected static final String USER_KEY_PREFIX = "user::";

	@Id
	private String key;

	@Field
	private Long id;
	@Field
	private String name;
	@Field List<Superpower> superpowersList;

	public HeroDoc() {
	}

	public HeroDoc(String key, Long id, String name) {
		super();
		this.key = key;
		this.id = id;
		this.name = name;
	}
	
	public HeroDoc(Long id, String name, List<String> nicknames) {
		this.id = id;
		this.name = name;
	}
	
	public static String getKeyFor(Long id) {
		return USER_KEY_PREFIX + id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		this.key = HeroDoc.getKeyFor(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Superpower> getSuperpowersList() {
		return superpowersList;
	}

	public void setSuperpowersList(List<Superpower> superpowersList) {
		this.superpowersList = superpowersList;
	}

	public void addSuperpower (Superpower superpower){
		if (Util.isNullOrEmpty(this.superpowersList)){
			this.superpowersList = new ArrayList<Superpower>();
		}
		this.superpowersList.add(superpower);
	}

	@Override
	public int hashCode() {
		int hashed = 1;
		if (id != null) {
			hashed = hashed * 31 + id.hashCode();
		}
		if (name != null) {
			hashed = hashed * 31 + name.hashCode();
		}
		return hashed;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		if (obj == this)
			return true;
		HeroDoc other = (HeroDoc) obj;
		return this.hashCode() == other.hashCode();
	}
}
