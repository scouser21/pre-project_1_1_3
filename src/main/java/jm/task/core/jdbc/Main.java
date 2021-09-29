package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        // Создание таблицы User(ов)
        userService.createUsersTable();

        // Добавление 4 User(ов) в таблицу с данными на свой выбор.
                    // После каждого добавления должен быть вывод в консоль
                    // ( User с именем – name добавлен в базу данных )
        User user1 = new User("Tom", "Cole", (byte) 21);
        User user2 = new User("Joe", "Gray", (byte) 22);
        User user3 = new User("Pit", "Kane", (byte) 23);
        User user4 = new User("Sam", "Ings", (byte) 24);

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        // Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        List<User> list = userService.getAllUsers();
        for (User user : list){
            System.out.println(user.toString());
        }

        // Очистка таблицы User(ов)
        for (User user : list){
            userService.removeUserById(user.getId());
        }

        // Удаление таблицы
        userService.dropUsersTable();
        int ttt = 0;

    }
}
