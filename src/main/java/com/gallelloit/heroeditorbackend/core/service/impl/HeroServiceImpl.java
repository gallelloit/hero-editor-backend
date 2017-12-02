package com.gallelloit.heroeditorbackend.core.service.impl;

import java.util.List;
import java.util.Optional;

import com.gallelloit.heroeditorbackend.core.dao.doc.Superpower;
import com.gallelloit.heroeditorbackend.core.exception.ApiException;
import com.gallelloit.heroeditorbackend.core.exception.ErrorType;
import com.gallelloit.heroeditorbackend.core.dao.HeroRepository;
import com.gallelloit.heroeditorbackend.core.service.HeroService;
import com.gallelloit.heroeditorbackend.core.util.Util;
import com.gallelloit.heroeditorbackend.core.dao.doc.HeroDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gallelloit.heroeditorbackend.core.dao.HeroCounterRepository;

/**
 * Service that manages the valid operations over the user repository
 *
 * @author Charz++
 */

@Service
public class HeroServiceImpl implements HeroService {

	@Autowired
	private HeroRepository heroRepository;
	
	@Autowired
	private HeroCounterRepository heroCounterRepository;

	@Override
	public HeroDoc findById(Long id) {
		final Optional<HeroDoc> userObj = Optional.ofNullable(heroRepository.findOne(HeroDoc.getKeyFor(id)));
		return userObj.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
	}

	@Override
	public HeroDoc create(HeroDoc hero) {
		// We first search by email, the user should not exist

		final Optional<HeroDoc> heroDocOptional = Optional.ofNullable(this.findByName(hero.getName()));
		if (heroDocOptional.isPresent()) {
			throw new ApiException(ErrorType.USER_ALREADY_EXISTS);
		}

		hero.setId(heroCounterRepository.counter()); // internally we set the key with that id
		return heroRepository.save(hero);
	}

	@Override
	public void update(Long id, HeroDoc user) {
		// The user should exist
		final HeroDoc existingUser = this.findById(id);
		if (!Util.isNullOrEmpty(user.getName())) {
			existingUser.setName(user.getName());
		}
		// Save
		heroRepository.save(existingUser);
	}

	@Override
	public void delete(Long id) {
		// The user should exist
		this.findById(id);
		heroRepository.delete(HeroDoc.getKeyFor(id));
	}

	@Override
	public Boolean exists(Long id) {
		return heroRepository.exists(HeroDoc.getKeyFor(id));
	}

	@Override
	public List<HeroDoc> findAll() {
		return heroRepository.findAllHeroes();
	}

	@Override
	public List<HeroDoc> findHeroesByName(String heroName) {
		return heroRepository.findHeroesByName(heroName);
	}

	@Override
	public HeroDoc findByName(String heroName) {
		return heroRepository.findByName(heroName);
	}

	public void addSuperpower(Long id, Superpower superpower) {
		// The user should exist
		final HeroDoc existingHero = this.findById(id);
		if (!Util.isNullOrEmpty(existingHero.getName())) {
			existingHero.addSuperpower(superpower);
		}
		// Save
		heroRepository.save(existingHero);
	}
}
