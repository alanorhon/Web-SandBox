package servlet;

import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResultServlet extends HttpServlet {

    private PageGenerator pg = PageGenerator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        pageVariables.put("message", "Go to registration or transaction link!");
        resp.setStatus(200);
        resp.setContentType("text/html");
        resp.getWriter().println(pg.getPage("resultPage.html", pageVariables));
    }
}
