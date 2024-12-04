package com.educa.educacional.controller;

import com.educa.educacional.DTO.TurmaRequestDTO;
import com.educa.educacional.model.Curso;
import com.educa.educacional.model.Turma;
import com.educa.educacional.repository.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.educa.educacional.repository.CursoRepository;

import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    // Listar todas as turmas
    @GetMapping
    public ResponseEntity<List<Turma>> listarTodos() {
        List<Turma> turmas = turmaRepository.findAll();
        return ResponseEntity.ok(turmas);
    }

    @PostMapping
    public Turma save(@RequestBody TurmaRequestDTO dto) {
        Turma turma = new Turma();
        turma.setNome(dto.nome());
        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());

        Curso curso = cursoRepository.findById(dto.cursoId()).orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        turma.setCurso(curso);

        return turmaRepository.save(turma);
    }

    // Buscar turma por ID
    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscarPorId(@PathVariable Integer id) {
        return turmaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar turma existente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @Valid @RequestBody Turma turma) {
        if (!turmaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        turma.setId(id);
        Turma turmaAtualizada = turmaRepository.save(turma);
        return ResponseEntity.ok(turmaAtualizada);
    }

    // Deletar turma por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!turmaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        turmaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
