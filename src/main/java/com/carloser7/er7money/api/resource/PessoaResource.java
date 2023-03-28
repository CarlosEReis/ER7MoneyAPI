package com.carloser7.er7money.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carloser7.er7money.api.event.RecursoCriadoEvent;
import com.carloser7.er7money.api.model.Pessoa;
import com.carloser7.er7money.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	@NotNull
	public ResponseEntity<Pessoa> criar(@NotNull @Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

		Pessoa pessoaSalva = this.pessoaRepository.save(pessoa);

		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Pessoa> pessoa = this.pessoaRepository.findById(codigo);
		
		return pessoa.isPresent() ? 
				ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping
	public List<Pessoa> listar() {
		return this.pessoaRepository.findAll();
	}
	
	// Falando um pouco sobre atualização de recursos em REST
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo ) {
		this.pessoaRepository.deleteById(codigo);
	}
}
