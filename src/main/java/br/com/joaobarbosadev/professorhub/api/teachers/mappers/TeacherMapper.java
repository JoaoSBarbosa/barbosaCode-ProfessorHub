package br.com.joaobarbosadev.professorhub.api.teachers.mappers;

import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
import br.com.joaobarbosadev.professorhub.core.models.Teacher;

public interface TeacherMapper {


    /**
     * Converte a entidade para dto
     */

    TeacherResponse toTeacherResponse(Teacher teacher);

}
