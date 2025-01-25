package br.com.treinaweb.hyperprof.core.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "professores")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Teacher extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "nome")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "idade")
    private Integer age;
    @Column(name = "descricao")
    private String description;
    @Column(name = "valor_hora")
    private BigDecimal hourlyRate;
    @Column(name = "foto_perfil")
    private String urlPhoto;
    @Column(name = "password")
    private String password;

}
