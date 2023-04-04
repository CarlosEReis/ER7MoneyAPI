package com.carloser7.er7money.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carloser7.er7money.api.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	public Page<Pessoa> findByNomeContains(String nome, Pageable pageable);
}
