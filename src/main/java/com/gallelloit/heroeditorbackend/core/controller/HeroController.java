package com.gallelloit.heroeditorbackend.core.controller;

import java.util.List;

import javax.validation.Valid;

import com.gallelloit.heroeditorbackend.core.dao.doc.HeroDoc;
import com.gallelloit.heroeditorbackend.core.service.HeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * User REST Controller
 * 
 * @author charz
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/heroes")
public class HeroController {

	protected static final Logger logger = LoggerFactory.getLogger(HeroController.class);

	@Autowired
	private HeroService heroService;

	@RequestMapping(value = "/{heroId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public HeroDoc findById(@PathVariable Long heroId) {
		logger.info("Find hero by id: {}", heroId);
		return heroService.findById(heroId);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public HeroDoc create(@Valid @RequestBody HeroDoc user) {
		logger.info("Create hero");
		return heroService.create(user);
	}

	@RequestMapping(value = "/{heroId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void update(@PathVariable Long heroId, @Valid @RequestBody HeroDoc user) {
		logger.info("Update hero: {}", heroId);
		heroService.update(heroId, user);
	}

	@RequestMapping(value = "/{heroId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable Long heroId) {
		logger.info("Delete hero: {}", heroId);
		heroService.delete(heroId);
	}

	@RequestMapping(value = "/{heroId}/exists", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Boolean exists(@PathVariable Long heroId) {
		logger.info("Exists hero {}?", heroId);
		return heroService.exists(heroId);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<HeroDoc> findAll() {
		logger.info("Get all heroes");
		return heroService.findAll();
	}

	@RequestMapping(value = "/findByName/{heroName}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<HeroDoc> findByName(@PathVariable String heroName) {
		logger.info("Find hero by name: {}", heroName);
		return heroService.findHeroesByName(heroName);
	}

}
