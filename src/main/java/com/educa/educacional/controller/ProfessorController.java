package com.educa.educacional.controller;

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
    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    // Criar um novo professor
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Professor professor) {
        // Verificar se o professor com CPF já existe (ajuste conforme o campo único)
        if (professorRepository.existsByCpf(professor.getCpf())) {
            return ResponseEntity.badRequest().body("Erro: CPF já cadastrado!");
        }
        Professor novoProfessor = professorRepository.save(professor);
        return ResponseEntity.ok(novoProfessor);
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
