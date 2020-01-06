package service;

import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import model.SoldCars;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        Session session = sessionFactory.openSession();
        List<DailyReport> dailyReportList = new DailyReportDao(session).getAllDailyReport();
        session.close();
        return dailyReportList;
    }


    public DailyReport getLastReport() {
        List<DailyReport> listAllReports = getAllDailyReports();
        return listAllReports.get(listAllReports.size() - 1);
    }

    public void addDailyReport(DailyReport dailyReport) {
        Session session = sessionFactory.openSession();
        DailyReportDao dailyReportDao = new DailyReportDao(session);
        dailyReportDao.addDailyReport(dailyReport);
        session.close();
        SoldCars soldCars = SoldCars.getInstance();
        soldCars.setSummaryPrice(0L);
        soldCars.setCountSoldCars(0L);
    }

    public void deleteReportTable() {
        Session session = sessionFactory.openSession();
        new DailyReportDao(session).deleteReportTable();
        session.close();
    }
}