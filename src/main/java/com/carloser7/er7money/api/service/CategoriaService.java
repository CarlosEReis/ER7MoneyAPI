package com.carloser7.er7money.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carloser7.er7money.api.model.Categoria;
import com.carloser7.er7money.api.repository.CategoriaRepository;
import com.carloser7.er7money.api.service.exception.CategoriaInexistenteException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public void validaCategoria(Categoria categoria) {
		Optional<Categoria> categoriaBanco = this.categoriaRepository.findById(categoria.getCodigo());		
		if (categoriaBanco.isEmpty()) {
			throw new CategoriaInexistenteException();
		}
	}
}
