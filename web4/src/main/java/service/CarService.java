package service;

import DAO.CarDao;
import model.Car;
import model.SoldCars;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DBHelper;
import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {

        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);
        List<Car> allCars = carDao.getAllCars();
        session.close();
        return allCars;
    }

    public boolean addCar(Car car) {
        if (checkBrandCount(car.getBrand()) < 10) {
            Session session = sessionFactory.openSession();
            CarDao carDao = new CarDao(session);
            carDao.addCar(car);
            session.close();
            return true;
        } else {
            return false;
        }

    }

    public void deleteCar(Car car) {
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);
        carDao.deleteCar(car);
        session.close();
    }

    public Integer checkBrandCount(String brand) {
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);
        Integer count = carDao.checkBrandCount(brand);
        session.close();
        return count;
    }

    public void deleteCarTable() {
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);
        carDao.deleteCarTable();
        session.close();
    }

    public boolean sellCar(String brand, String model, String licensePlate) {
        SoldCars soldCars = SoldCars.getInstance();
        for (Car car : getAllCars()) {
            if (car.getBrand().equals(brand)
                    & car.getModel().equals(model)
                    & car.getLicensePlate().equals(licensePlate)) {
                soldCars.setSummaryPrice(soldCars.getSummaryPrice() + car.getPrice());
                soldCars.setCountSoldCars(soldCars.getCountSoldCars() + 1);
                deleteCar(car);
                return true;
            }
        }
        return false;
    }
}