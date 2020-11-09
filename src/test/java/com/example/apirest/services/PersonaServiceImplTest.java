package com.example.apirest.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.example.apirest.ApirestApplication;
import io.swagger.annotations.Api;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.apirest.entities.Persona;
import com.example.apirest.repositories.PersonaRepository;
import com.example.apirest.services.PersonaServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApirestApplication.class)
class PersonaServiceImplTest {

	@MockBean
	private PersonaRepository personaRepository;
	
	@Autowired
	private PersonaServiceImpl personaService;

	@Test
	public void findAllTest() throws Exception {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");

		Persona persona2 = new Persona();
		persona2.setNombre("Jorge");
		persona2.setApellido("Peña");

		List<Persona> listaEnviada = new ArrayList<Persona>();
		listaEnviada.add(persona1);
		listaEnviada.add(persona2);

		when(personaRepository.findAll()).thenReturn(listaEnviada);


		assertEquals(listaEnviada, personaService.findAll());
	}

	@Test
	public void findOneTest() {

	}

	@Test
	public void updateTest() {

	}

	@Test
	public void saveTest() {

	}


	@Test
	void testSearchString() throws Exception {
		Persona persona = new Persona();
		persona.setNombre("Pablo");
		persona.setApellido("Peña");
		
		List<Persona> listaEnviada = new ArrayList<Persona>();
		listaEnviada.add(persona);
		
		when(personaRepository.searchNative("Pablo")).thenReturn(listaEnviada);
		
		
		assertEquals(listaEnviada, personaService.search("Pablo"));
		
	}


}