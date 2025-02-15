package br.com.joaobarbosadev.professorhub.core.models.entities;
import br.com.joaobarbosadev.professorhub.core.models.abstractions.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "professores")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Teacher extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "email")
    private String email;
    @Column(name = "idade")
    private Integer idade;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "valor_hora")
    private BigDecimal valorHora;
    @Column(name = "foto_perfil")
    private String urlPhoto;
    @Column(name = "password")
    private String senha;
}
