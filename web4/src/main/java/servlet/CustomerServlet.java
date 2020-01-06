package servlet;

import com.google.gson.Gson;
import model.Car;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());
        resp.setStatus(200);
        resp.getWriter().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        resp.setStatus(200);
        Gson gson = new Gson();
        if (CarService.getInstance().sellCar(brand, model, licensePlate)) {
            String json = gson.toJson("Congratulations! You buy this car");
            resp.getWriter().println(json);
        } else {
            String json = gson.toJson("no car with this parameters");
            resp.getWriter().println(json);
        }
    }
}