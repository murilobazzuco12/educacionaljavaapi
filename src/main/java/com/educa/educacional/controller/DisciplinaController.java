package com.educa.educacional.controller;

import com.educa.educacional.model.Disciplina;
import com.educa.educacional.repository.DisciplinaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    // Listar todas as disciplinas
    @GetMapping
    public List<Disciplina> listarTodos() {
        return disciplinaRepository.findAll();
    }

    // Criar nova disciplina
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Disciplina disciplina) {
        if (disciplinaRepository.existsByNome(disciplina.getNome())) {
            return ResponseEntity.badRequest().body("Erro: Disciplina com esse nome já cadastrada!");
        }
        Disciplina novaDisciplina = disciplinaRepository.save(disciplina);
        return ResponseEntity.ok(novaDisciplina);
    }

    // Buscar disciplina por ID
    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscarPorId(@PathVariable Integer id) {
        return disciplinaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar disciplina
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @Valid @RequestBody Disciplina disciplina) {
        if (!disciplinaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Verificar se o nome da disciplina já está em uso por outra disciplina
        if (disciplinaRepository.existsByNomeAndIdNot(disciplina.getNome(), id)) {
            return ResponseEntity.badRequest().body("Erro: Disciplina com esse nome já cadastrada!");
        }

        disciplina.setId(id);
        Disciplina disciplinaAtualizada = disciplinaRepository.save(disciplina);
        return ResponseEntity.ok(disciplinaAtualizada);
    }

    // Deletar disciplina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!disciplinaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        disciplinaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
