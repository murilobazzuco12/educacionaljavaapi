package com.educa.educacional.controller;

import com.educa.educacional.DTO.MatriculaRequestDTO;
import com.educa.educacional.model.Matricula;
import com.educa.educacional.model.Aluno;
import com.educa.educacional.model.Turma;
import com.educa.educacional.repository.MatriculaRepository;
import com.educa.educacional.repository.AlunoRepository;
import com.educa.educacional.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    // Listar todas as matrículas
    @GetMapping
    public List<Matricula> listarTodos() {
        return matriculaRepository.findAll();
    }

    // Criar uma nova matrícula com validação de Aluno e Turma
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody MatriculaRequestDTO matriculaRequest) {
        Aluno aluno = alunoRepository.findById(matriculaRequest.getAlunoId())
                .orElse(null);

        Turma turma = turmaRepository.findById(matriculaRequest.getTurmaId())
                .orElse(null);

        if (aluno == null) {
            return ResponseEntity.badRequest().body("Erro: Aluno não encontrado.");
        }

        if (turma == null) {
            return ResponseEntity.badRequest().body("Erro: Turma não encontrada.");
        }

        // Verificar se o aluno já está matriculado na turma
        if (matriculaRepository.existsByAlunoAndTurma(aluno, turma)) {
            return ResponseEntity.badRequest().body("Erro: Aluno já matriculado nesta turma.");
        }

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);

        Matricula novaMatricula = matriculaRepository.save(matricula);
        return ResponseEntity.ok(novaMatricula);
    }

    // Buscar matrícula por ID
    @GetMapping("/{id}")
    public ResponseEntity<Matricula> buscarPorId(@PathVariable Integer id) {
        return matriculaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar matrícula
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody MatriculaRequestDTO matriculaRequest) {
        if (!matriculaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Aluno aluno = alunoRepository.findById(matriculaRequest.getAlunoId())
                .orElse(null);

        Turma turma = turmaRepository.findById(matriculaRequest.getTurmaId())
                .orElse(null);

        if (aluno == null) {
            return ResponseEntity.badRequest().body("Erro: Aluno não encontrado.");
        }

        if (turma == null) {
            return ResponseEntity.badRequest().body("Erro: Turma não encontrada.");
        }

        // Verificar se o aluno já está matriculado na turma, exceto a matrícula atual
        if (matriculaRepository.existsByAlunoAndTurmaAndIdNot(aluno, turma, id)) {
            return ResponseEntity.badRequest().body("Erro: Aluno já matriculado nesta turma.");
        }

        Matricula matricula = matriculaRepository.findById(id).get();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);

        Matricula matriculaAtualizada = matriculaRepository.save(matricula);
        return ResponseEntity.ok(matriculaAtualizada);
    }

    // Deletar matrícula
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!matriculaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        matriculaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
