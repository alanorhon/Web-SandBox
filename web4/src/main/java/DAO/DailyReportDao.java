package DAO;

import model.Car;
import org.hibernate.*;
import model.DailyReport;

import java.util.List;

public class DailyReportDao {
    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        return dailyReports;
    }

    public void deleteReportTable() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM DailyReport").executeUpdate();
        transaction.commit();
    }

    public void addDailyReport(DailyReport dailyReport) {
        Transaction transaction = session.beginTransaction();
        session.save(dailyReport);
        transaction.commit();
    }
}