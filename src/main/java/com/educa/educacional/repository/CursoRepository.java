package com.educa.educacional.repository;

import com.educa.educacional.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

    boolean existsByNome(String nome);  // Verifica se o nome já existe

    boolean existsByNomeAndIdNot(String nome, Integer id);  // Verifica se o nome já existe para outro ID
}
