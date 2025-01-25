package br.com.treinaweb.hyperprof.core.models;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

/**
 * Passo 1 - Criar uma classe de configuração e ativar o Auditable e adionar as notaoes de beans: @Configuration e @EnableJpaAuditing
 * Passo 2 - Adicionar a anotação @CreatedDate para campos que serão gravado automaticamento ao inserir um registro
 * Passo 3 - Adicionar o anatação @LastModifiedDate para campo que sera prechido automaticamnte após ser atualizada
 * Passo 4 - Na classe adicoanr a anotaçao @EntityListener(AuditongEntityList.class para ativar na classe
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
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

}
