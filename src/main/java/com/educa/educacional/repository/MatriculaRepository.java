package com.educa.educacional.repository;

import com.educa.educacional.model.Matricula;
import com.educa.educacional.model.Aluno;
import com.educa.educacional.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    boolean existsByAlunoAndTurma(Aluno aluno, Turma turma);
}
