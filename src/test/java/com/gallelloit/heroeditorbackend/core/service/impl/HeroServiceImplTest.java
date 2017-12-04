package com.gallelloit.heroeditorbackend.core.service.impl;

import java.util.Arrays;
import java.util.List;

import com.gallelloit.heroeditorbackend.core.dao.HeroRepository;
import com.gallelloit.heroeditorbackend.core.dao.doc.HeroDoc;
import com.gallelloit.heroeditorbackend.core.dao.doc.Superpower;
import com.gallelloit.heroeditorbackend.core.exception.ApiException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test the Hero Service Implementation: test the service logic
 *
 * @author Charz++
 */

@RunWith(MockitoJUnitRunner.class)
public class HeroServiceImplTest {
	
	private static final Long HERO_ONE_ID = 1L;
	private static final String HERO_ONE_KEY = HeroDoc.getKeyFor(HERO_ONE_ID);

	@Mock
	private HeroRepository repoMock;

	@InjectMocks
	private HeroServiceImpl heroService;

	private HeroDoc heroDoc;

	private Superpower superpower1, superpower2;

	@Before
	public void init() {
		heroDoc = new HeroDoc();
		heroDoc.setName("Pedro");
		heroDoc.setId(HERO_ONE_ID);

		superpower1 = new Superpower("Fly");
		superpower2 = new Superpower("Speed");
	}
	

	@Test
	public void findOneAndHeroExists() {
		// Data preparation
		Mockito.when(repoMock.findOne(HERO_ONE_KEY)).thenReturn(heroDoc);

		// Method call
		HeroDoc hero = heroService.findById(HERO_ONE_ID);

		// Verification
		Assert.assertNotNull(hero);
		Mockito.verify(repoMock, Mockito.times(1)).findOne(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(repoMock);
	}

	@Test(expected = ApiException.class)
	public void findOneAndHeroIsNull() {
		// Data preparation
		Mockito.when(repoMock.findOne(HERO_ONE_KEY)).thenReturn(null);

		// Method call
		HeroDoc hero = heroService.findById(HERO_ONE_ID);

		// Verification
		Assert.assertNull(hero);
		Mockito.verify(repoMock, Mockito.times(1)).findOne(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(repoMock);
	}

	@Test(expected = ApiException.class)
	public void createHeroAndHeroAlreadyExists() {
		// Data preparation
		Mockito.when(repoMock.findByName("Pedro")).thenReturn(heroDoc);

		// Method call
		HeroDoc hero = heroService.create(heroDoc);

		// Verification
		Assert.assertNull(hero);
		Mockito.verify(repoMock, Mockito.times(1)).findByName(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(repoMock);
	}

	@Test(expected = ApiException.class)
	public void updateHeroAndHeroNotExists() {
		// Data preparation
		Mockito.when(repoMock.findByName("Pedro")).thenReturn(null);

		// Method call
		heroService.update(HERO_ONE_ID, heroDoc);

		// Verification
		Mockito.verify(repoMock, Mockito.times(1)).findByName(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(repoMock);
	}

	@Test
	public void findAllHeroes() {
		// Data preparation
		List<HeroDoc> heroes = Arrays.asList(heroDoc, heroDoc, heroDoc);
		Mockito.when(repoMock.findAllHeroes()).thenReturn(heroes);

		// Method call
		List<HeroDoc> heroList = heroService.findAll();

		// Verification
		Assert.assertThat(heroList, Matchers.hasSize(3));
		Mockito.verify(repoMock, Mockito.times(1)).findAllHeroes();
		Mockito.verifyNoMoreInteractions(repoMock);
	}

	@Test
	public void findHeroesByNameAndHeroExist() {
		// Data preparation
		List<HeroDoc> heroes = Arrays.asList(heroDoc, heroDoc, heroDoc);
		Mockito.when(repoMock.findHeroesByName(Mockito.anyString())).thenReturn(heroes);

		// Method call
		List<HeroDoc> heroesList = heroService.findHeroesByName("Pedro");

		// Verification
		Assert.assertThat(heroesList, Matchers.hasSize(3));
		Mockito.verify(repoMock, Mockito.times(1)).findHeroesByName(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(repoMock);
	}

	public void addSuperpowerToExistingHero(){
		// Data preparation
		Mockito.when(repoMock.findByName("Pedro")).thenReturn(heroDoc);
		heroDoc.addSuperpower(superpower1);

		// Method call
		heroService.addSuperpower(heroDoc.getId(), superpower1);
		heroService.addSuperpower(heroDoc.getId(), superpower2);

		// Verification
		Mockito.verify(repoMock, Mockito.times(1)).findByName(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(repoMock);

		Assert.assertEquals(2, heroDoc.getSuperpowersList().size());
	}

	public void removeSuperpowerToExistingHero(){
		// Data preparation
		Mockito.when(repoMock.findByName("Pedro")).thenReturn(heroDoc);

		// Method call
		heroService.addSuperpower(heroDoc.getId(), superpower1);
		heroService.addSuperpower(heroDoc.getId(), superpower2);

		heroService.removeSuperpower(heroDoc.getId(), superpower1);

		// Verification
		Mockito.verify(repoMock, Mockito.times(1)).findByName(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(repoMock);
		Assert.assertEquals(1, heroDoc.getSuperpowersList().size());
	}
}
