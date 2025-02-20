package br.com.joaobarbosadev.professorhub.api.dropbox.services;

import com.dropbox.core.DbxException;

import java.io.IOException;
import java.io.InputStream;

public interface DropboxService {


    String uploadTeacherPhoto(String fileName, String teacherName, Long teacherId, InputStream fileStream) throws DbxException, IOException;

}