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
import com.carloser7.er7money.api.model.Lancamento;
import com.carloser7.er7money.api.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoReource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@RequestBody @Valid Lancamento lancamento, HttpServletResponse reponse) {
		
		Lancamento lancamentoSalvo = this.lancamentoRepository.save(lancamento);
		this.publisher.publishEvent(new RecursoCriadoEvent(this, reponse, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		
		Optional<Lancamento> lancamento = this.lancamentoRepository.findById(codigo);
		return lancamento.isPresent() ?
				ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping
	public List<Lancamento> listar() {
		return this.lancamentoRepository.findAll();
	}
	
}
