package com.carloser7.er7money.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carloser7.er7money.api.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{

	List<Cidade> findByEstadoCodigo(Long estadoCodigo);
}
