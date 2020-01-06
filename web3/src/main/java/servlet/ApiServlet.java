package servlet;

import com.google.gson.Gson;
import exception.DBException;
import model.BankClient;
import service.BankClientService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApiServlet extends HttpServlet {

    BankClientService bankClientService = new BankClientService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        Gson gson = new Gson();
        String json = "";
        if (req.getPathInfo().contains("all")) {
            json = gson.toJson(bankClientService.getAllClient());
        } else {
            //todo corrected APIServlet
            json = gson.toJson(bankClientService.getClientByName(req.getParameter("name")));
        }
        resp.getWriter().write(json);
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        bankClientService.createTable();
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo().contains("all")){
            bankClientService.cleanUp();
        }
    }
}
