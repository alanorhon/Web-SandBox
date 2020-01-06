package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");
        try {
            response.setStatus(200);
            response.getWriter().println(Integer.parseInt(request.getParameter("value")) * 2);
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            response.getWriter().println(0);
        }
    }
}
