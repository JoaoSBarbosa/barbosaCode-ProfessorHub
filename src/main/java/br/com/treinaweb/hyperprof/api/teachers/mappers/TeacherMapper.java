package br.com.treinaweb.hyperprof.api.teachers.mappers;

import br.com.treinaweb.hyperprof.api.teachers.dtos.TeacherResponse;
import br.com.treinaweb.hyperprof.core.models.Teacher;

public interface TeacherMapper {


    /**
     * Converte a entidade para dto
     */

    TeacherResponse toTeacherResponse(Teacher teacher);

}
