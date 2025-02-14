package br.com.joaobarbosadev.professorhub.core.models.entities;
import br.com.joaobarbosadev.professorhub.api.common.TableName.TableName;
import br.com.joaobarbosadev.professorhub.core.models.abstractions.Auditable;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableName.TABLE_TOKEN_INVALIDO)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class InvalidToken extends Auditable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
}
