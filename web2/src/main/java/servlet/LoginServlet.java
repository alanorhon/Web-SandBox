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

public class LoginServlet extends HttpServlet {
    private PageGenerator pg = PageGenerator.getInstance();
    private UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        resp.setContentType("text/html");
        if (!email.isEmpty() && !pass.isEmpty()) {
            User user = new User(email, pass);
            if (userService.isExistsThisUser(user)) {
                if (userService.authUser(user)) {
                    resp.setStatus(200);
                    resp.getWriter().println("User " + email + " logged OK");
                } else {
                    resp.setStatus(400);
                    pageVariables.put("message", "Wrong login or password");
                    pageVariables.put("mapping", "/login");
                    resp.getWriter().println(pg.getPage("info.html", pageVariables));
                }
            } else {
                resp.setStatus(400);
                pageVariables.put("message", "User with such email is not registered");
                pageVariables.put("mapping", "/login");
                resp.getWriter().println(pg.getPage("info.html", pageVariables));
            }
        } else {
            resp.setStatus(400);
            pageVariables.put("message", "Email or password field should not be empty");
            pageVariables.put("mapping", "/login");
            resp.getWriter().println(pg.getPage("info.html", pageVariables));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        resp.getWriter().println(pg.getPage("authPage.html", pageVariables));
    }
}
