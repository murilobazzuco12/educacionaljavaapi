package com.educa.educacional.repository;

import com.educa.educacional.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    boolean existsByCpf(String cpf); // Verifica CPF para criar

    boolean existsByCpfAndIdNot(String cpf, Integer id); // Verifica CPF para atualizar
}