package com.educa.educacional.controller;

import com.educa.educacional.model.Curso;
import com.educa.educacional.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    // Listar todos os cursos
    @GetMapping
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    // Criar novo curso
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Curso curso) {
        if (cursoRepository.existsByNome(curso.getNome())) {
            return ResponseEntity.badRequest().body("Erro: Curso com esse nome já cadastrado!");
        }
        Curso novoCurso = cursoRepository.save(curso);
        return ResponseEntity.ok(novoCurso);
    }

    // Buscar curso por ID
    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Integer id) {
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar curso
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @Valid @RequestBody Curso curso) {
        if (!cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Verificar se o nome do curso já está em uso por outro curso
        if (cursoRepository.existsByNomeAndIdNot(curso.getNome(), id)) {
            return ResponseEntity.badRequest().body("Erro: Curso com esse nome já cadastrado!");
        }

        curso.setId(id);
        Curso cursoAtualizado = cursoRepository.save(curso);
        return ResponseEntity.ok(cursoAtualizado);
    }

    // Deletar curso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
