package br.com.joaobarbosadev.professorhub.api.teachers.mappers;

import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherPhotoResponse;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherRequest;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
import br.com.joaobarbosadev.professorhub.core.models.entities.Teacher;

public interface TeacherMapper {


    /**
     * Converte a entidade para dto
     */

    TeacherResponse toTeacherResponse(Teacher teacher);
    Teacher toTeacher(TeacherRequest teacherRequest);
    TeacherPhotoResponse toTeacherPhotoResponse(Teacher teacher);

}
