package br.com.joaobarbosadev.professorhub.api.dropbox.services;

import br.com.joaobarbosadev.professorhub.api.common.exceptions.custom.CustomEntityNotFoundException;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherPhotoResponse;
import br.com.joaobarbosadev.professorhub.api.teachers.mappers.TeacherMapper;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;
import br.com.joaobarbosadev.professorhub.core.models.entities.Teacher;
import br.com.joaobarbosadev.professorhub.core.repositories.DropboxRepository;
import br.com.joaobarbosadev.professorhub.core.repositories.TeacherRepository;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DropboxServiceImpl implements DropboxService {

    private final DbxRequestConfig config = DbxRequestConfig.newBuilder("professorHub").build();
    private final DropboxRepository dropboxRepository;
    private final DropboxConfigService dropboxConfigService;
    private final String rootFolderPath = "/professorHub/images/professor";

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;


    private Dropbox getDropboxEntity() {
        return dropboxRepository.findById(1L).orElseThrow(() -> new CustomEntityNotFoundException("Não foi possivel localizar registros de configuração dropbox com o id 1"));
    }

//    @Override
//    public String uploadTeacherPhoto(String fileName, String teacherName, Long teacherId, InputStream fileStream) {
//        try {
//            dropboxConfigService.checkoutValidateAcessToken();
//            Dropbox dropbox = getDropboxEntity();
//            String accessToken = dropbox.getAccessToken();
//
//            // LOG PARA CHECAR SE O TOKEN ESTÁ CORRETO
//            System.out.println("TOKEN USADO NO UPLOAD: " + accessToken);
//
//            DbxClientV2 clientV2 = new DbxClientV2(config, accessToken);
//
//            String userFolderPath = rootFolderPath + "/" + teacherId + "/" + teacherName;
//            crea
//            // Define o caminho no Dropbox
//            String dropboxPath = rootFolderPath + "/" + teacherId + "_" + teacherName + "_" + fileName;
//
//            // Faz o upload do arquivo
//            FileMetadata metadata = clientV2.files().uploadBuilder(dropboxPath)
//                    .withMode(WriteMode.OVERWRITE)
//                    .uploadAndFinish(fileStream);
//
//            SharedLinkMetadata sharedLink = clientV2.sharing().createSharedLinkWithSettings(
//                    metadata.getPathLower(),
//                    SharedLinkSettings.newBuilder().build()
//            );
//
//            return sharedLink.getUrl();
//
//        } catch (DbxException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Erro ao conectar com o Dropbox: " + e.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Erro ao processar o arquivo: " + e.getMessage());
//        }
//    }

    @Override
    public TeacherPhotoResponse uploadTeacherPhoto(String fileName, String teacherName, Long teacherId, InputStream fileStream) {
        try {
            dropboxConfigService.checkoutValidateAcessToken();
            Dropbox dropbox = getDropboxEntity();
            String accessToken = dropbox.getAccessToken();

            DbxClientV2 clientV2 = new DbxClientV2(config, accessToken);

            // Criar a pasta do professor se não existir
            String userFolderPath = rootFolderPath + "/" + teacherId + "_" + teacherName;
            createFolderIfNotExists(clientV2, userFolderPath);

            // Caminho completo do arquivo no Dropbox
            String dropboxFilePath = userFolderPath + "/" + fileName;

            // Fazer upload do arquivo
            FileMetadata metadata = clientV2.files().uploadBuilder(dropboxFilePath)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(fileStream);

            // Obter ou criar o link de compartilhamento
            String sharedLink = getOrCreateSharedLink(clientV2, metadata.getPathLower());

            return teacherMapper.toTeacherPhotoResponse(updateProfessorPhoto(teacherId, dropboxFilePath, sharedLink));
        } catch (DbxException | IOException e) {
            throw new RuntimeException("Erro ao interagir com o Dropbox: " + e.getMessage(), e);
        }
    }

    private Teacher updateProfessorPhoto(Long teacherId, String dropboxFilePath, String sharedLink) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new CustomEntityNotFoundException("Professor não encontrado com ID: " + teacherId));
        teacher.setUrlPhoto(dropboxFilePath);
        teacher.setLinkFotoPerfil(sharedLink);
        teacherRepository.save(teacher);
        return teacher;
    }

    private void createFolderIfNotExists(DbxClientV2 clientV2, String folderPath) throws DbxException {
        try {
            // Verifica se a pasta já existe
            Metadata metadata = clientV2.files().getMetadata(folderPath);
            if (!(metadata instanceof FolderMetadata)) {
                throw new RuntimeException("O caminho já existe, mas não é uma pasta: " + folderPath);
            }
        } catch (GetMetadataErrorException e) {
            // Se a pasta não existir, cria uma nova
            if (e.errorValue.isPath() && e.errorValue.getPathValue().isNotFound()) {
                clientV2.files().createFolderV2(folderPath);
            } else {
                throw e;
            }
        }
    }


    private String getOrCreateSharedLink(DbxClientV2 clientV2, String filePath) throws DbxException {
        List<SharedLinkMetadata> links = clientV2.sharing().listSharedLinksBuilder()
                .withPath(filePath)
                .withDirectOnly(true)
                .start()
                .getLinks();

        if (!links.isEmpty()) {
            return links.get(0).getUrl();
        }

        SharedLinkMetadata newLink = clientV2.sharing().createSharedLinkWithSettings(
                filePath, SharedLinkSettings.newBuilder().build());

        return newLink.getUrl();
    }

}
