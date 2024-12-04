package com.educa.educacional.repository;

import com.educa.educacional.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {

    boolean existsByNome(String nome);  // Verifica se já existe uma disciplina com esse nome

    boolean existsByNomeAndIdNot(String nome, Integer id);  // Verifica se já existe com nome diferente do ID atual
}
