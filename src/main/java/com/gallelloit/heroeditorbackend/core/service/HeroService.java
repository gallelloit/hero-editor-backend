package com.gallelloit.heroeditorbackend.core.service;

import java.util.List;

import com.gallelloit.heroeditorbackend.core.dao.doc.HeroDoc;
import com.gallelloit.heroeditorbackend.core.dao.doc.Superpower;


/**
 * Interface for the User Service Implementation
 *
 * @author Charz++
 */

public interface HeroService {
	
	// CRUD repository interface
	
	HeroDoc findById(Long id);

	List<HeroDoc> findHeroesByName(String heroName);

	HeroDoc findByName(String heroName);

	HeroDoc create(HeroDoc user);

	void update(Long id, HeroDoc user);

	void delete(Long id);
	
	Boolean exists(Long id);

	List<HeroDoc> findAll();

	void addSuperpower(Long id, Superpower superpower);
}
