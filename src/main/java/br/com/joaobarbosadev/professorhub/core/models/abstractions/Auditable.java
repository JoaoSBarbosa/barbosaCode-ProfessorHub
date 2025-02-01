package br.com.joaobarbosadev.professorhub.core.models.abstractions;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Lembrete: Configuração para auditoria automática de entidades com Spring Data JPA.
 *
 * Etapas para ativar e configurar a auditoria:
 *
 * 1. Crie uma classe de configuração e ative o recurso de auditoria:
 *    - Utilize as anotações: @Configuration e @EnableJpaAuditing.
 *
 * 2. Para registrar automaticamente a data de criação ao inserir um registro:
 *    - Adicione a anotação @CreatedDate no campo correspondente.
 *
 * 3. Para registrar automaticamente a última data de modificação ao atualizar um registro:
 *    - Adicione a anotação @LastModifiedDate no campo correspondente.
 *
 * 4. Ative o suporte de auditoria na classe base:
 *    - Adicione a anotação @EntityListeners(AuditingEntityListener.class).
 *
 * Observação:
 * - Utilize a anotação @MappedSuperclass para informar que esta classe será herdada
 *   por outras entidades, mas não será mapeada como uma tabela separada no banco de dados.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass // informa que não tera tabela
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
