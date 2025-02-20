package br.com.joaobarbosadev.professorhub.api.dropbox.controller;

import br.com.joaobarbosadev.professorhub.api.dropbox.services.DropboxService;
import com.dropbox.core.DbxException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dropbox")
public class DropboxController {

    private final DropboxService dropboxService;

//    @PostMapping("/upload/teacher/photo")
//    public ResponseEntity<?> uploadPhoto(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("teacherName") String teacherName,
//            @RequestParam("teacherId") Long teacherId) {
//
//        try (InputStream fileStream = file.getInputStream()) {
//            String fileUrl = dropboxService.uploadTeacherPhoto(file.getOriginalFilename(), teacherName, teacherId, fileStream);
//            return ResponseEntity.ok(fileUrl);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar arquivo: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer upload: " + e.getMessage());
//        }
//    }
@PostMapping("/upload/teacher/photo")
public ResponseEntity<?> uploadPhoto(
        @RequestParam("file") MultipartFile file,
        @RequestParam("teacherName") String teacherName,
        @RequestParam("teacherId") String teacherIdStr) { // Receba como String

    try {
        Long teacherId = Long.parseLong(teacherIdStr); // Converta para Long
        try (InputStream fileStream = file.getInputStream()) {
            String fileUrl = dropboxService.uploadTeacherPhoto(file.getOriginalFilename(), teacherName, teacherId, fileStream);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar arquivo: " + e.getMessage());
        }
    } catch (NumberFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("teacherId deve ser um número válido.");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer upload: " + e.getMessage());
    }
}
}
