package br.com.joaobarbosadev.professorhub.api.teachers.services;

import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;

public interface MeService {
    TeacherResponse getLoggedInTeacher();
}
