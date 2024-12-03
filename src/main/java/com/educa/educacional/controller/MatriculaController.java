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
    public ResponseEntity<Matricula> criar(@RequestBody MatriculaRequestDTO matriculaRequest) {
        Aluno aluno = alunoRepository.findById(matriculaRequest.getAlunoId())
                .orElse(null);

        Turma turma = turmaRepository.findById(matriculaRequest.getTurmaId())
                .orElse(null);

        if (aluno == null || turma == null) {
            return ResponseEntity.badRequest().build();
        }

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);

        return ResponseEntity.ok(matriculaRepository.save(matricula));
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
    public ResponseEntity<Matricula> atualizar(@PathVariable Integer id, @RequestBody MatriculaRequestDTO matriculaRequest) {
        if (!matriculaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Aluno aluno = alunoRepository.findById(matriculaRequest.getAlunoId())
                .orElse(null);

        Turma turma = turmaRepository.findById(matriculaRequest.getTurmaId())
                .orElse(null);

        if (aluno == null || turma == null) {
            return ResponseEntity.badRequest().build();
        }

        Matricula matricula = matriculaRepository.findById(id).get();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);

        return ResponseEntity.ok(matriculaRepository.save(matricula));
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
