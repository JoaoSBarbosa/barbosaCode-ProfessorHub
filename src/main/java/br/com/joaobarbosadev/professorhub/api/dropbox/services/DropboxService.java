package br.com.joaobarbosadev.professorhub.api.dropbox.services;

import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherPhotoResponse;
import com.dropbox.core.DbxException;

import java.io.IOException;
import java.io.InputStream;

public interface DropboxService {


    TeacherPhotoResponse uploadTeacherPhoto(String fileName, String teacherName, Long teacherId, InputStream fileStream);

}