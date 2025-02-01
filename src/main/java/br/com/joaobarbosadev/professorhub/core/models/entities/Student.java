package br.com.joaobarbosadev.professorhub.core.models.entities;
import br.com.joaobarbosadev.professorhub.api.common.TableName.TableName;
import br.com.joaobarbosadev.professorhub.core.models.abstractions.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableName.TABLE_ALUNOS)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Student extends Auditable {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(name = "nome")
    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "data_aula")
    private LocalDateTime classDate;

    @ManyToOne(fetch = FetchType.LAZY) // carrega dados do professor sempre que carregar os dados do aluno
    @JoinColumn(name = "professor_id")
    private Teacher teacher;

}
