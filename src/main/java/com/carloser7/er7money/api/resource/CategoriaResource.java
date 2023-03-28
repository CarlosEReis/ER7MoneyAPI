package com.carloser7.er7money.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.carloser7.er7money.api.event.RecursoCriadoEvent;
import com.carloser7.er7money.api.model.Categoria;
import com.carloser7.er7money.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		
		Categoria categoriaSalva = this.categoriaRepository.save(categoria);
		
		this.eventPublisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Categoria> categoria = this.categoriaRepository.findById(codigo);
		
		return categoria.isPresent() ? 
				ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}
	
	// É uma boa prática retornar o status code 200 mesmo se o retorno do rescurso em questão estiver vazio.
	@GetMapping
	public List<Categoria> Listar() {
		return this.categoriaRepository.findAll();
	}	
}
