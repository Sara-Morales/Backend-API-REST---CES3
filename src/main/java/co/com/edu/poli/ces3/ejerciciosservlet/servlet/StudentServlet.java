package co.com.edu.poli.ces3.ejerciciosservlet.servlet;

import co.com.edu.poli.ces3.ejerciciosservlet.model.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

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


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doTrace(req, resp);
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        this.getParamsFromPost(req);
        JsonObject body = this.getParamsFromPost(req);

        Student std = new Student(
                body.get("id").getAsInt(),
                body.get("document").getAsString(),
                body.get("name").getAsString()
        );
        this.students.add(std);
        out.print(gson.toJson(std));
        out.flush();


        out.println("<b>Hello from post method </b>");
        out.flush();
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

    public void destroy() {
    }
}