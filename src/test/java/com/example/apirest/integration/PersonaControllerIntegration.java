package com.example.apirest.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	void getAllTest() throws Exception {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");

		Persona persona2 = new Persona();
		persona2.setNombre("Jorge");
		persona2.setApellido("P");


		personaRepository.save(persona1);
		personaRepository.save(persona2);

		mockMvc.perform(get("/api/v1/personas/")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nombre", is("Pablo")))
				.andExpect(jsonPath("$[0].apellido", is("Peña")))
				.andExpect(jsonPath("$[1].nombre", is("Jorge")))
				.andExpect(jsonPath("$[1].apellido", is("P")));

	}

	@Test
	void getOneTest() throws Exception {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");

		Persona persona2 = new Persona();
		persona2.setNombre("Jorge");
		persona2.setApellido("P");

		personaRepository.save(persona1);
		personaRepository.save(persona2);


		mockMvc.perform(get("/api/v1/personas/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre", is("Pablo")))
				.andExpect(jsonPath("$.apellido", is("Peña")));

	}

	@Test
	void saveTest() throws Exception {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");


		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(post("/api/v1/personas/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(persona1))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre", is("Pablo")))
				.andExpect(jsonPath("$.apellido", is("Peña")));

		List<Persona> listaEnviada = new ArrayList<>();
		listaEnviada.add(persona1);

		//assertEquals(persona1, personaRepository.findAll().get(0)); //ERROR A SOLUCIONAR

	}

	@Test
	void deleteTest() throws Exception {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");

		Persona persona2 = new Persona();
		persona2.setNombre("Jorge");
		persona2.setApellido("P");

		personaRepository.save(persona1);
		personaRepository.save(persona2);

		mockMvc.perform(delete("/api/v1/personas/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$",is(true)));

		//assertEquals(personaRepository.findAll().get(0),persona2);

	}

	@Test
	void updateTest() throws Exception {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");

		Persona persona2 = new Persona();
		persona2.setNombre("Jorge");
		persona2.setApellido("Peña");

		personaRepository.save(persona2);

		ObjectMapper om = new ObjectMapper();
		mockMvc.perform(put("/api/v1/personas/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(persona1))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre", is("Pablo")))
				.andExpect(jsonPath("$.apellido", is("Peña")));


	}
	
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
