package com.educa.educacional.controller;

import com.educa.educacional.model.Aluno;
import com.educa.educacional.repository.AlunoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    // Listar todos os alunos
    @GetMapping
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    // Criar novo aluno
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Aluno aluno) {
        if (alunoRepository.existsByMatricula(aluno.getMatricula())) {
            return ResponseEntity.badRequest().body("Erro: Matrícula já cadastrada!");
        }
        Aluno novoAluno = alunoRepository.save(aluno);
        return ResponseEntity.ok(novoAluno);
    }

    // Buscar aluno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Integer id) {
        return alunoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar aluno
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @Valid @RequestBody Aluno aluno) {
        if (!alunoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Verificar se a matrícula já existe para outro aluno
        if (!alunoRepository.existsByMatriculaAndIdNot(aluno.getMatricula(), id)) {
            aluno.setId(id);
            Aluno alunoAtualizado = alunoRepository.save(aluno);
            return ResponseEntity.ok(alunoAtualizado);
        } else {
            return ResponseEntity.badRequest().body("Erro: Matrícula já cadastrada para outro aluno!");
        }

    }

    // Deletar aluno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!alunoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        alunoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
