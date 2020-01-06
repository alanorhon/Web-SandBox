package servlet;

import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        Long price;
        try {
            price = Long.parseLong(req.getParameter("price"));
        } catch (IllegalArgumentException e) {
            price = null;
        }
        if (!brand.isEmpty() && !model.isEmpty() && !licensePlate.isEmpty()
                && price != null && price > 0) {
            Car tempCar = new Car(brand, model, licensePlate, price);
            CarService.getInstance().addCar(tempCar);
            resp.setStatus(200);
        } else {
            resp.setStatus(403);
        }
    }
}