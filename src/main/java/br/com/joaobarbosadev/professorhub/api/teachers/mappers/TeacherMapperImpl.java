package br.com.joaobarbosadev.professorhub.api.teachers.mappers;

import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherRequest;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
import br.com.joaobarbosadev.professorhub.core.models.entities.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapperImpl implements TeacherMapper {
    @Override
    public TeacherResponse toTeacherResponse(Teacher teacher) {
        if (teacher == null) return null;


        return TeacherResponse
                .builder()
                .id(teacher.getId())
                .name(teacher.getNome())
                .description(teacher.getDescricao())
                .age(teacher.getIdade())
                .email(teacher.getEmail())
                .createdAt(teacher.getCreatedAt())
                .hourlyRate(teacher.getValorHora())
                .password(teacher.getSenha())
                .updatedAt(teacher.getUpdatedAt())
                .urlPhoto(teacher.getUrlPhoto())
                .build();
//
//       var response = new TeacherResponse();
//
//       if(teacher.getId() != null) response.setId(teacher.getId());
//       if(teacher.getName() != null) response.setName(teacher.getName());
//       if(teacher.getDescription() != null) response.setDescription(teacher.getDescription());
//       if(teacher.getUrlPhoto() != null) response.setUrlPhoto(teacher.getUrlPhoto());
//       if(teacher.getPassword() != null) response.setPassword(teacher.getPassword());
//       if(teacher.getEmail() != null) response.setEmail(teacher.getEmail());
//       if(teacher.getAge() != null) response.setAge(teacher.getAge());
//       if(teacher.getHourlyRate() != null) response.setHourlyRate(teacher.getHourlyRate());
//       if(teacher.getCreatedAt() != null) response.setCreatedAt(teacher.getCreatedAt());
//       if(teacher.getUpdatedAt() != null) response.setUpdatedAt(teacher.getUpdatedAt());
//       return response;
    }

    @Override
    public Teacher toTeacher(TeacherRequest teacherRequest) {
        if (teacherRequest == null) return null;

        return Teacher.builder()
                .descricao( teacherRequest.getDescricao() )
                .nome( teacherRequest.getNome() )
                .idade( teacherRequest.getIdade() )
                .email( teacherRequest.getEmail() )
                .senha( teacherRequest.getSenha() )
                .valorHora( teacherRequest.getValorHora() )
                .build();
    }

}
