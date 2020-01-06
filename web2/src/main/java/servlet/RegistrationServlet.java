package servlet;

import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {

    private UserService userService = UserService.getInstance();
    private PageGenerator pg = PageGenerator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        pageVariables.put("email", "Enter email: ");
        pageVariables.put("password", "Enter password: ");
        resp.setContentType("text/html");
        resp.getWriter().println(pg.getPage("registerPage.html", pageVariables));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        resp.setContentType("text/html");

        if (email.isEmpty() || pass.isEmpty()) {
            resp.setStatus(400);
            pageVariables.put("message", "Email and Password must be not empty");
            pageVariables.put("mapping", "/register");
        } else {
            if (userService.addUser(new User(email, pass))) {
                resp.setStatus(400);
                pageVariables.put("message", "User already registered");
                pageVariables.put("mapping", "/register");
            } else {
                resp.setStatus(200);
                pageVariables.put("message", "Congratulation! Your registration have complite");
                pageVariables.put("mapping", "/login");
            }
        }
        resp.getWriter().println(pg.getPage("info.html", pageVariables));
    }
}
