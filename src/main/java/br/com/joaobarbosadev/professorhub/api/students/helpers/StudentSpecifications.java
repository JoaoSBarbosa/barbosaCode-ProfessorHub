package br.com.joaobarbosadev.professorhub.api.students.helpers;

import br.com.joaobarbosadev.professorhub.core.models.entities.Student;
import org.springframework.data.jpa.domain.Specification;

public abstract class StudentSpecifications {


    public static Specification<Student> byName(String name) {
        return ((root, query, criteriaBuilder) ->
                name != null && !name.isEmpty() ? criteriaBuilder.like(root.get("name"), "%" + name + "%") : null);
    }

    public static Specification<Student> byEmail(String email) {
        return ((root, query, criteriaBuilder) ->
                email != null && !email.isEmpty() ? criteriaBuilder.like(root.get("email"), "%" + email + "%") : null);
    }

    public static Specification<Student> byTeacherId(Long teacherId) {
        return ((root, query, criteriaBuilder) ->
                teacherId != null ? criteriaBuilder.equal(root.get("teacher").get("id"), teacherId) : null);
    }
}
