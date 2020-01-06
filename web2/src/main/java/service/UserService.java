package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {
    private static UserService userService;
    private UserService() {}

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());


    public List<User> getAllUsers() {
        return new ArrayList<>(dataBase.values());
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
        boolean result = isExistsThisUser(user);
        if (!result) {
            long id = maxId.incrementAndGet();
            user.setId(id);
            dataBase.put(id, user);
        }
        return !result;
    }

    public void deleteAllUser() {
        dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        return dataBase.containsValue(user);
    }

    public List<User> getAllAuth() {
        return  new ArrayList<>(authMap.values());
    }

    public boolean authUser(User user) {
        boolean result = user != null
                && user.getEmail() != null
                && getIdByEmail(user.getEmail()) != -1
                && user.getPassword() != null
                && isExistsThisUser(user);
        if (result && getUserById(getIdByEmail(user.getEmail())).getPassword().equals(user.getPassword())) {
            Long id = getIdByEmail(user.getEmail());
            user.setId(id);
            authMap.put(id, user);
        }
        return result;
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }
    public long getIdByEmail(String email) {
        long result = -1;
        for (User user : getAllUsers()) {
            if (user.getEmail().equals(email)) {
                result = user.getId();
            }
        }
        return result;
    }

}
