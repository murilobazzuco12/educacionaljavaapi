package com.educa.educacional.controller;

import com.educa.educacional.model.Nota;
import com.educa.educacional.repository.NotaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;

    // Listar todas as notas
    @GetMapping
    public List<Nota> listarTodos() {
        return notaRepository.findAll();
    }

    // Criar uma nova nota
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Nota nota) {
        if (nota.getValor().compareTo(BigDecimal.ZERO) < 0 || nota.getValor().compareTo(BigDecimal.TEN) > 0) {
            return ResponseEntity.badRequest().body("Erro: O valor da nota deve estar entre 0 e 10.");
        }
        Nota novaNota = notaRepository.save(nota);
        return ResponseEntity.ok(novaNota);
    }

    // Buscar nota por ID
    @GetMapping("/{id}")
    public ResponseEntity<Nota> buscarPorId(@PathVariable Integer id) {
        return notaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar nota existente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @Valid @RequestBody Nota nota) {
        if (!notaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        if (nota.getValor().compareTo(BigDecimal.ZERO) < 0 || nota.getValor().compareTo(BigDecimal.TEN) > 0) {
            return ResponseEntity.badRequest().body("Erro: O valor da nota deve estar entre 0 e 10.");
        }

        nota.setId(id);
        Nota notaAtualizada = notaRepository.save(nota);
        return ResponseEntity.ok(notaAtualizada);
    }

    // Deletar nota por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!notaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        notaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
