package br.com.joaobarbosadev.professorhub.api.dropbox.services;

import br.com.joaobarbosadev.professorhub.api.common.exceptions.custom.CustomEntityNotFoundException;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;
import br.com.joaobarbosadev.professorhub.core.repositories.DropboxRepository;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class DropboxServiceImpl implements DropboxService {

    private final DbxRequestConfig config = DbxRequestConfig.newBuilder("professorHub").build();
    private final DropboxRepository dropboxRepository;
    private final DropboxConfigService dropboxConfigService;
    private final String rootFolderPath = "/professorHub/images/professor";


    private Dropbox getDropboxEntity() {
        return dropboxRepository.findById(1L).orElseThrow(() -> new CustomEntityNotFoundException("Não foi possivel localizar registros de configuração dropbox com o id 1"));
    }

    @Override
    public String uploadTeacherPhoto(String fileName, String teacherName, Long teacherId, InputStream fileStream) {
        try {
            dropboxConfigService.checkoutValidateAcessToken();
            Dropbox dropbox = getDropboxEntity();
            String accessToken = dropbox.getAccessToken();

            // LOG PARA CHECAR SE O TOKEN ESTÁ CORRETO
            System.out.println("TOKEN USADO NO UPLOAD: " + accessToken);

            DbxClientV2 clientV2 = new DbxClientV2(config, accessToken);

            // Define o caminho no Dropbox
            String dropboxPath = rootFolderPath + "/" + teacherId + "_" + teacherName + "_" + fileName;

            // Faz o upload do arquivo
            FileMetadata metadata = clientV2.files().uploadBuilder(dropboxPath)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(fileStream);

            SharedLinkMetadata sharedLink = clientV2.sharing().createSharedLinkWithSettings(
                    metadata.getPathLower(),
                    SharedLinkSettings.newBuilder().build()
            );

            return sharedLink.getUrl();

        } catch (DbxException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o Dropbox: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar o arquivo: " + e.getMessage());
        }
    }

}
