package servlet;

import exception.DBException;
import model.BankClient;
import service.BankClientService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class RegistrationServlet extends HttpServlet {

    BankClientService bankClientService = new BankClientService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        pageVariables.put("message", "Sing Up");
        resp.setStatus(200);
        resp.setContentType("text/html");
        resp.getWriter().println(PageGenerator.getInstance().getPage("registrationPage.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        resp.setContentType("text/html");
        String name = req.getParameter("name"); // принимаем имя
        String password = req.getParameter("password"); // принимаем пароль
        long money; // создание переменной для денег
        try {
            money = Long.parseLong(req.getParameter("money")); // пытаемся запарсить значение в long
        } catch (NumberFormatException e) {
            money = 0;
        }
        if (!name.isEmpty() & !password.isEmpty() & money != 0) { // проверка на заполнение полей
            if (!bankClientService.addClient(new BankClient(name, password, money))) { // если метод возвращает false, клиент уже существует
                pageVariables.put("message", "Client not add");
            } else {
                pageVariables.put("message", "Add client successful");
            }

        } else { // выявление незаполненого поля
            if (name.isEmpty()) {
                pageVariables.put("message", "Name should not be empty");
            }
            if (password.isEmpty()) {
                pageVariables.put("message", "Password should not be empty");
            }
            if (money == 0) {
                pageVariables.put("message", "Deposit should not be empty");
            }
        }
        resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
    }
}

