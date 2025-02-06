package br.com.joaobarbosadev.professorhub.api.teachers.services;

import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherRequest;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
import br.com.joaobarbosadev.professorhub.api.teachers.mappers.TeacherMapper;
import br.com.joaobarbosadev.professorhub.api.teachers.mappers.TeacherMapperImpl;
import br.com.joaobarbosadev.professorhub.core.repositories.TeacherRepository;
import br.com.joaobarbosadev.professorhub.core.exceptions.custom.CustomEntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
        teacherForSave.setPassword(passwordEncoder.encode(teacherForSave.getPassword()));
        return teacherMapper.toTeacherResponse(teacherRepository.save(teacherForSave));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponse> findByDescription(String description) {
        return teacherRepository.findByDescription(description).stream().map(teacherMapper::toTeacherResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponse findById(Long id) {
       return teacherRepository.findById(id).map(teacherMapper::toTeacherResponse).orElseThrow(()-> new CustomEntityNotFoundException("Professor não localizado com o ID: " + id));
    }


}
