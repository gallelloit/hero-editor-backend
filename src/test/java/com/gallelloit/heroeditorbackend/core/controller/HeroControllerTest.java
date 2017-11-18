package com.gallelloit.heroeditorbackend.core.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import com.gallelloit.heroeditorbackend.core.EntryPoint;
import com.gallelloit.heroeditorbackend.core.dao.HeroRepository;
import com.gallelloit.heroeditorbackend.core.dao.doc.HeroDoc;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

/**
 * Test the Hero Controller: test the endpoints directly
 *
 * @author Charz++
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EntryPoint.class)
@WebAppConfiguration
public class HeroControllerTest {

	private static final Long HERO_ONE_ID = 1L;
	private static final Long HERO_TWO_ID = 2L;
	private static final Long HERO_THREE_ID = 3L;
	private static final Long HERO_FOUR_ID = 4L;
	private static final String HERO_ONE_KEY = HeroDoc.getKeyFor(HERO_ONE_ID);
	private static final String HERO_TWO_KEY = HeroDoc.getKeyFor(HERO_TWO_ID);
	private static final String HERO_THREE_KEY = HeroDoc.getKeyFor(HERO_THREE_ID);
	private static final String HERO_FOUR_KEY = HeroDoc.getKeyFor(HERO_FOUR_ID);
	private static MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private HeroRepository heroRepository;

	@Autowired
	public void setConverters(HttpMessageConverter<?>[] converters) {
		mappingJackson2HttpMessageConverter = Arrays.asList(converters)
															.stream()
															.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
															.findAny()
															.orElse(null);
		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void init() {
		mockMvc = webAppContextSetup(webApplicationContext).build();
		// create the heros
		HeroDoc hero1 = new HeroDoc(HERO_ONE_KEY, HERO_ONE_ID, "Carlos");
		HeroDoc hero2 = new HeroDoc(HERO_TWO_KEY, HERO_TWO_ID, "Luis");
		HeroDoc hero3 = new HeroDoc(HERO_THREE_KEY, HERO_THREE_ID, "Jose");
		// save the heros in the database
		heroRepository.save(hero1);
		heroRepository.save(hero2);
		heroRepository.save(hero3);
	}
	
	@After
	public void tearDown() {
		// delete all the test data created from the database
		heroRepository.delete(HERO_ONE_KEY);
		heroRepository.delete(HERO_TWO_KEY);
		heroRepository.delete(HERO_THREE_KEY);
		heroRepository.delete(HERO_FOUR_KEY);
		heroRepository.delete("hero::counter");
	}
	

	@Test (expected = NestedServletException.class)
    public void getNonExistingHero() throws Exception {
        mockMvc.perform(get("/heroes/100"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(CONTENT_TYPE));
    }

    @Test
    public void getExistingHero() throws Exception {
        mockMvc.perform(get("/heroes/" + HERO_ONE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Carlos")));
    }

    @Test
    public void createHero() throws Exception {
    	HeroDoc hero4 = new HeroDoc(HERO_FOUR_KEY, HERO_FOUR_ID, "Alfredo");
        mockMvc.perform(post("/heroes")
               .contentType(CONTENT_TYPE)
               .content(json(hero4)))
               .andExpect(status().isCreated());
    }

    @Test
    public void updateHero() throws Exception {
    	HeroDoc hero3 = new HeroDoc(HERO_THREE_ID, "Jose", Arrays.asList("pancho"));
        mockMvc.perform(put("/heroes/" + HERO_THREE_ID)
               .contentType(CONTENT_TYPE)
               .content(json(hero3)))
               .andExpect(status().isOk());
    }

    @Test
	public void findAll() throws Exception{
		mockMvc.perform(get("/heroes"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$", Matchers.hasSize(4)));
	}

	@Test
	public void getExistingHeroByName() throws Exception {
		mockMvc.perform(get("/heroes/findByName/arl"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$", Matchers.hasSize(1)));
	}

	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
