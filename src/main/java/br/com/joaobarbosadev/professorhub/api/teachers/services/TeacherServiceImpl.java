package br.com.joaobarbosadev.professorhub.api.teachers.services;

import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherRequest;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
import br.com.joaobarbosadev.professorhub.api.teachers.mappers.TeacherMapper;
import br.com.joaobarbosadev.professorhub.core.repositories.TeacherRepository;
import br.com.joaobarbosadev.professorhub.api.common.exceptions.custom.CustomEntityNotFoundException;
import br.com.joaobarbosadev.professorhub.core.services.authentication.model.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public TeacherResponse saveTeacher(TeacherRequest teacherRequest) {
        var teacherForSave = teacherMapper.toTeacher(teacherRequest);
        teacherForSave.setSenha(passwordEncoder.encode(teacherForSave.getSenha()));
        return teacherMapper.toTeacherResponse(teacherRepository.save(teacherForSave));
    }

    @Override
    @Transactional
    public TeacherResponse updateTeacher(TeacherRequest teacherRequest) {
        var teacherForUpdate = ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getTeacher();
        // Os campos da fomte deve ser igual aos campos do destino
        // primeiro valor fonte, segundo valor destino, terceiro valor em diantes são propriedades que deverão ser ignoradas
        BeanUtils.copyProperties(teacherRequest, teacherForUpdate, "id", "senha","createdAt", "updatedAt");
        if( teacherRequest.getSenha() != null ) {
            teacherForUpdate.setSenha(passwordEncoder.encode(teacherRequest.getSenha()));
        }
        var teacherUpdated = teacherRepository.save(teacherForUpdate);
        return teacherMapper.toTeacherResponse(teacherUpdated);

    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponse> findByDescription(String description) {
        return teacherRepository.findByDescription(description).stream().map(teacherMapper::toTeacherResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponse findById(Long id) {
        return teacherRepository.findById(id).map(teacherMapper::toTeacherResponse).orElseThrow(() -> new CustomEntityNotFoundException("Professor não localizado com o ID: " + id));
    }


}
