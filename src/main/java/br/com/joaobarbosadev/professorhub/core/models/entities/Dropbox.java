package br.com.joaobarbosadev.professorhub.core.models.entities;
import br.com.joaobarbosadev.professorhub.core.models.abstractions.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dropbox")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Dropbox extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dropbox_id")
    private Long id;
    @Column(name = "api_key")
    private String apiKey;
    @Column(name = "api_secret")
    private String apiSecret;
    @Column(name = "access_token", columnDefinition = "TEXT")
    private String accessToken;
    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;


}
