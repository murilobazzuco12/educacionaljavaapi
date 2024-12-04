package com.educa.educacional.DTO;

import java.time.LocalDate;

public record AlunoRequestDTO(String nome, String email, String matricula, LocalDate dataNascimento){
}
