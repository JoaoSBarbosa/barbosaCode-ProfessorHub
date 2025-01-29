package br.com.joaobarbosadev.professorhub.api.teachers.services;

import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;

import java.util.List;

public interface TeacherService {

    public List<TeacherResponse> findByDescription(String description);
    TeacherResponse findById(Long id);
}
