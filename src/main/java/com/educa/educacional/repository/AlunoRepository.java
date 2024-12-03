package com.educa.educacional.repository;

import com.educa.educacional.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    boolean existsByMatricula(String matricula);

    boolean existsByMatriculaAndIdNot(String matricula, Integer id);
}
