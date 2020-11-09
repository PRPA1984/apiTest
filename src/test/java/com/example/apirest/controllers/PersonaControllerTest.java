package com.example.apirest.controllers;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.apirest.controllers.PersonaController;
import com.example.apirest.entities.Persona;
import com.example.apirest.services.PersonaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonaController.class)
class PersonaControllerTest {

	@MockBean
	private PersonaServiceImpl personaService;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	void getAllTest() throws Exception {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");

		Persona persona2 = new Persona();
		persona2.setNombre("Jorge");
		persona2.setApellido("P");

		List<Persona> listaEnviada = new ArrayList();
		listaEnviada.add(persona1);
		listaEnviada.add(persona2);

		when(personaService.findAll()).thenReturn(listaEnviada);

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


		when(personaService.findById(1L)).thenReturn(persona1);

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


		when(personaService.save(persona1)).thenReturn(persona1);
		ObjectMapper om = new ObjectMapper();
		mockMvc.perform(post("/api/v1/personas/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(persona1))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre", is("Pablo")))
				.andExpect(jsonPath("$.apellido", is("Peña")));

	}

	@Test
	void deleteTest() throws Exception {

		when(personaService.delete(1L)).thenReturn(true);
		mockMvc.perform(delete("/api/v1/personas/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$",is(true)));

	}

	@Test
	void updateTest() throws Exception {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");


		when(personaService.update(persona1,1L)).thenReturn(persona1);
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
	void testSearchString() throws Exception {
		Persona persona = new Persona();
		persona.setNombre("Pablo");
		persona.setApellido("Peña");
		
		List<Persona> listaEnviada = new ArrayList();
		listaEnviada.add(persona);
		
		when(personaService.search("Pablo")).thenReturn(listaEnviada);
		
		mockMvc.perform(get("/api/v1/personas/search")
				.param("filtro", "Pablo")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nombre", is("Pablo")))
				.andExpect(jsonPath("$[0].apellido", is("Peña")));

	}
}