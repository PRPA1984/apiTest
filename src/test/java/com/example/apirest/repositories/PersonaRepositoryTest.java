package com.example.apirest.repositories;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.apirest.entities.Persona;
import com.example.apirest.repositories.PersonaRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
class PersonaRepositoryTest {

	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Test
	void findAllTest() {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");
		
		Persona persona2 = new Persona();
		persona2.setNombre("Jorge");
		persona2.setApellido("Peña");

		List<Persona> listaVacia = new ArrayList<>();

		assertEquals(listaVacia, personaRepository.findAll());

		List<Persona> listaEnviada = new ArrayList();
		listaEnviada.add(persona1);
		listaEnviada.add(persona2);
		
		entityManager.persist(persona1);
		entityManager.persist(persona2);
		entityManager.flush();


		assertEquals(listaEnviada, personaRepository.findAll());
	}
	@Test
	void findByIdTest() {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");

		Persona persona2 = new Persona();
		persona2.setNombre("Jorge");
		persona2.setApellido("Peña");

		entityManager.persist(persona1);
		entityManager.persist(persona2);
		entityManager.flush();

		assertEquals(persona1,personaRepository.findById(1L).get());
	}
	@Test
	void deleteTest() {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");

		Persona persona2 = new Persona();
		persona2.setNombre("Jorge");
		persona2.setApellido("Peña");

		List<Persona> listaEnviada = new ArrayList();
		listaEnviada.add(persona1);
		listaEnviada.add(persona2);

		entityManager.persist(persona1);
		entityManager.persist(persona2);
		entityManager.flush();

		personaRepository.deleteById(1L);

		assertEquals(1, personaRepository.findAll().size());



	}
	@Test
	void saveTest() {
		Persona persona1 = new Persona();
		persona1.setNombre("Pablo");
		persona1.setApellido("Peña");

		Persona persona2 = new Persona();
		persona2.setNombre("Jorge");
		persona2.setApellido("Peña");


		List<Persona> listaEnviada = new ArrayList();
		listaEnviada.add(persona1);
		listaEnviada.add(persona2);

		entityManager.persist(persona1);
		personaRepository.save(persona2);
		entityManager.flush();

		assertEquals(listaEnviada, personaRepository.findAll());
	}
	
	
	
	
	
	
	
	@Test
	void testSearchString() {
		Persona persona = new Persona();
		persona.setNombre("Pablo");
		persona.setApellido("Peña");
		
		List<Persona> listaEnviada = new ArrayList();
		listaEnviada.add(persona);
		
		entityManager.persist(persona);
		entityManager.flush();
		
		assertEquals(listaEnviada, personaRepository.searchNative("Pablo"));
		assertEquals(listaEnviada, personaRepository.searchNative("Peña"));
		
		
	}

}