package com.educa.educacional.DTO;

public record MatriculaRequestDTO() {
    private static Integer alunoId;
    private static Integer turmaId;

    // Getters e Setters
    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Integer turmaId) {
        this.turmaId = turmaId;
    }
}