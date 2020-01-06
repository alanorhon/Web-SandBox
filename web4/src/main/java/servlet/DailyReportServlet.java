package servlet;

import com.google.gson.Gson;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {
    CarService carService = CarService.getInstance();
    DailyReportService dailyReportService = DailyReportService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String report = null;
        if (req.getPathInfo().contains("all")) {
            report = gson.toJson(DailyReportService.getInstance().getAllDailyReports());
        } else if (req.getPathInfo().contains("last")) {
            report = gson.toJson(DailyReportService.getInstance().getLastReport());
        }
        resp.setStatus(200);
        resp.getWriter().println(report == null ? gson.toJson("There are no daily reports!") : report);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarService.getInstance().deleteCarTable();
        DailyReportService.getInstance().deleteReportTable();
        resp.setStatus(200);
    }
}