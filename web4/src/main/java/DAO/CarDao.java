package DAO;

import model.Car;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public void acceptCar(Car car) {
        session.save(car);
        session.close();
    }

    public List<Car> getAllCars() {
        Query query = session.createQuery("FROM Car");
        List<Car> allCars = query.list();
        return allCars;
    }

    public void addCar(Car car) {
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
    }

    public void deleteCar(Car car) {
        Transaction transaction = session.beginTransaction();
        session.delete(car);
        transaction.commit();
    }

    public void deleteCarTable() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Car").executeUpdate();
        transaction.commit();
    }

    public Integer checkBrandCount(String brand) {
        Integer count = 0;
        Query query = session.createQuery("FROM Car where brand=:param");
        query.setParameter("param", brand);
        List<Car> selectCars = query.list();
        count = selectCars.size();
        return count;
    }

}