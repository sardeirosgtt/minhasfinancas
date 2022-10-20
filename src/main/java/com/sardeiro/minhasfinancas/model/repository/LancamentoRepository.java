package com.sardeiro.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sardeiro.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
