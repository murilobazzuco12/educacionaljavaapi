package com.educa.educacional.controller;

import com.educa.educacional.DTO.ProfessorRequestDTO;
import com.educa.educacional.model.Professor;
import com.educa.educacional.repository.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    // Listar todos os professores
    @GetMapping
    public ResponseEntity<List<Professor>> listarTodos() {
        List<Professor> professores = professorRepository.findAll();
        return ResponseEntity.ok(professores);
    }

    @PostMapping
    public Professor save(@RequestBody ProfessorRequestDTO dto) {
        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setTelefone(dto.telefone());
        professor.setEspecialidade(dto.especialidade());
        professor.setCpf(dto.cpf());

        return professorRepository.save(professor);
    }


    // Buscar professor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable Integer id) {
        return professorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar professor existente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @Valid @RequestBody Professor professor) {
        if (!professorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Verificar se o CPF já existe para outro professor
        if (professorRepository.existsByCpfAndIdNot(professor.getCpf(), id)) {
            return ResponseEntity.badRequest().body("Erro: CPF já cadastrado para outro professor!");
        }

        professor.setId(id);
        Professor professorAtualizado = professorRepository.save(professor);
        return ResponseEntity.ok(professorAtualizado);
    }

    // Deletar professor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!professorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        professorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
