package com.example.apirest.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Libro")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Libro extends Base{
	
	@Column(name = "fecha")
	private int fecha;
	
	@Column(name = "genero")
	private String genero;
	
	@Column(name = "paginas")
	private int paginas;
	
	@Column(name = "titulo")
	private String titulo;
	
	@ManyToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name = "libro_autor",
				joinColumns = @JoinColumn(name = "fk_libro"),
				inverseJoinColumns = @JoinColumn(name = "fk_autor"))
	private List<Autor> autores = new ArrayList<Autor>();

}
