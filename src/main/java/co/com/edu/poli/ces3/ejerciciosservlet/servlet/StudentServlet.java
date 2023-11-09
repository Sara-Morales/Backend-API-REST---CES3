package co.com.edu.poli.ces3.ejerciciosservlet.servlet;

import co.com.edu.poli.ces3.ejerciciosservlet.model.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "Estudiante", value = "/student")
public class StudentServlet extends MYServlet {
    private String message;
    private ArrayList <Student> students;
    private GsonBuilder gsonBuilder;
    private Gson gson;

    public void init() {
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        students = new ArrayList<>();

        Student student1 = new Student();
        student1.id = 10;
        student1.setName("Miguel");
        student1.setDocument("123456");

        students.add(student1);

        for(int i=0 ; i< students.size(); i++){
            System.out.println(students.get(i));
        }
        message = "Hola mundo cruel :c";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String studentId = request.getParameter("studentId");
        PrintWriter out = response.getWriter();

        if(studentId ==null){
            out.println(gson.toJson(students));
        }else{
            Student studentSearch= null;

            for(Student s: students){
                if(s.getId() == Integer.parseInt(studentId)) {
                    studentSearch = s;
                    break;
                }
            }
            out.println(gson.toJson(studentSearch));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonObject body = this.getParamsFromPost(req);
        int id = body.get("id").getAsInt();
        String document = body.get("document").getAsString();
        String name = body.get("name").getAsString();
        Student newStudent = new Student(id, document, name);
        students.add(newStudent);
        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(newStudent));
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonObject body = this.getParamsFromPost(req);
        int id = body.get("id").getAsInt();
        String document = body.get("document").getAsString();
        String name = body.get("name").getAsString();
        Student studentToUpdate = null;
        for (Student s : students) {
            if (s.getId() == id) {
                studentToUpdate = s;
                break;
            }
        }

        if (studentToUpdate != null) {
            studentToUpdate.setDocument(document);
            studentToUpdate.setName(name);

            PrintWriter out = resp.getWriter();
            out.println(gson.toJson(studentToUpdate));
            out.flush();
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = this.getParamsFromPost(req);
        resp.setContentType("application/json");
        int id = body.get("id").getAsInt();
        String name = body.get("name").getAsString();
        Student studentToUpdate = null;
        for (Student s : students) {
            if (s.getId() == id) {
                studentToUpdate = s;
                break;
            }
        }

        if (studentToUpdate != null) {
            studentToUpdate.setName(name);
            PrintWriter out = resp.getWriter();
            out.println(gson.toJson(studentToUpdate));
            out.flush();
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentId = req.getParameter("studentId");

        if(studentId != null) {
            int id = Integer.parseInt(studentId);
            Student studentToDelete = null;
            for (Student s : students) {
                if (s.getId() == id) {
                    studentToDelete = s;
                    break;
                }
            }

            if (studentToDelete != null) {
                students.remove(studentToDelete);
                resp.setContentType("application/json");
                resp.getWriter().println("Estudiante con ID " + id + " fue eliminado exitosamente.");

            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}