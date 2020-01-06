package servlet;

import model.DailyReport;
import model.SoldCars;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewDayServlet extends HttpServlet {
    DailyReportService drs = DailyReportService.getInstance();
    SoldCars sc = SoldCars.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        drs.addDailyReport(new DailyReport(sc.getSummaryPrice(), sc.getCountSoldCars()));
        resp.setStatus(200);
    }
}
