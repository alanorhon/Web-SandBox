package servlet;

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

public class MoneyTransactionServlet extends HttpServlet {

    BankClientService bankClientService = new BankClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        pageVariables.put("message", "On this page You can send money for our clients");
        resp.setStatus(200);
        resp.setContentType("text/html");
        resp.getWriter().println(PageGenerator.getInstance().getPage("moneyTransactionPage.html", pageVariables));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        String senderName = req.getParameter("senderName");
        String senderPass = req.getParameter("senderPass");
        String sum = req.getParameter("count");
        String nameTo = req.getParameter("nameTo");
        long sumToSend = Long.parseLong(sum);
        if (bankClientService.sendMoneyToClient
                (new BankClient(senderName, senderPass, sumToSend), nameTo, sumToSend)) {
            pageVariables.put("message", "The transaction was successful");
        } else {
            pageVariables.put("message", "transaction rejected");
        }
        resp.setStatus(200);
        resp.setContentType("text/html");
        resp.getWriter().println(PageGenerator.getInstance().getPage("registrationPage.html", pageVariables));
    }
}
