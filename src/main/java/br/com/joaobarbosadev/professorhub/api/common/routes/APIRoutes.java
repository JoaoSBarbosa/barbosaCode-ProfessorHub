package br.com.joaobarbosadev.professorhub.api.common.routes;

public class APIRoutes {

    public static final String API = "/api";
    public static final String TEACHERS="/teachers";


    public static final String ROUTE_TEACHERS = API + TEACHERS;
    public static final String DESCRIPTION = "/description";
    public static final String TEACHER_ID_VARIABLE = "/{professorId}";


    public static final String STUDENTS = "/students";
    public static final String ROUTE_STUDENTS = API + STUDENTS;
    public static final String ROUTE_POST_STUDENTS_TEACHERS = TEACHERS + TEACHER_ID_VARIABLE;

    private APIRoutes() {}
}
