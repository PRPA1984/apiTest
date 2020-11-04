package com.example.apirest.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.example.apirest.ApirestApplication;
import com.example.apirest.entities.Persona;
import com.example.apirest.repositories.PersonaRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApirestApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  //locations = "classpath:application-testing.properties")
		locations = "classpath:application-test.properties")
class PersonaControllerIntegration {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Test
	void testSearchString() throws Exception{
		Persona persona = new Persona();
		persona.setNombre("Paul");
		persona.setApellido("McCartney");
		
		
		personaRepository.save(persona);
		
		mockMvc.perform(get("/api/v1/personas/search")
				.param("filtro", "Paul")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nombre", is("Paul")))
				.andExpect(jsonPath("$[0].apellido", is("McCartney")));
		
		
		
	}

}
