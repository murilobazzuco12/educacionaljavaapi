package com.educa.educacional.repository;

import com.educa.educacional.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    boolean existsByMatriculaIdAndDisciplinaId(Integer matriculaId, Integer disciplinaId);
}