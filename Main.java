import java.sql.*;import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        try {            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("----------Database of the workers of Aitu----------");                System.out.println("Выберите операцию:");
                System.out.println("1. Просмотр всех работников компании");                System.out.println("2. Добавление нового работника");
                System.out.println("3. Обновление информации о пользователе");                System.out.println("4. Удаление работника из базы данных");
                System.out.println("5. Выход");                System.out.print("Введите номер операции: ");
                int choice = scanner.nextInt();                scanner.nextLine();
                switch (choice) {
                    case 1:                        viewAllUsers(connection);
                        break;                    case 2:
                        addUser(connection, scanner);                        break;
                    case 3:                        updateUser(connection, scanner);
                        break;                    case 4:
                        deleteUser(connection, scanner);                        break;
                    case 5:                        connection.close();
                        System.out.println("Работа завершена.");                        return;
                    default:                        System.out.println("Неверный выбор операции.");
                        break;                }
            }        } catch (SQLException e) {
            e.printStackTrace();
        }    }
    public static void viewAllUsers(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");        ResultSet r = statement.executeQuery();
        while (r.next()) {
            System.out.println("Id: " + r.getInt("id"));            System.out.println("Name: " + r.getString("name"));
            System.out.println("Profession: " + r.getString("profession"));            System.out.println("Age: " + r.getInt("age"));
            System.out.println("Email: " + r.getString("email"));            System.out.println("________________________________________________");
        }    }
    public static void addUser(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите имя: ");        String name = scanner.nextLine();
        System.out.print("Введите професиию: ");        String profession = scanner.nextLine();
        System.out.print("Введите возраст: ");        int age = scanner.nextInt();
        scanner.nextLine();        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, profession, age, email) VALUES (?, ?, ?, ?)");        statement.setString(1, name);
        statement.setString(2, profession);        statement.setInt(3, age);
        statement.setString(4, email);        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Пользователь успешно добавлен.");        } else {
            System.out.println("Ошибка при добавлении пользователя.");        }
    }
    public static void updateUser(Connection connection, Scanner scanner) throws SQLException {        System.out.print("Введите ID пользователя для обновления: ");
        int id = scanner.nextInt();        scanner.nextLine();
        System.out.print("Введите новое имя: ");
        String name = scanner.nextLine();        System.out.print("Введите новую фамилию: ");
        String profession = scanner.nextLine();
        System.out.print("Введите новый возраст: ");        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите новый email: ");
        String email = scanner.nextLine();
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET name=?, profession=?, age=?, email=? WHERE id=?");
        statement.setString(1, name);        statement.setString(2, profession);
        statement.setInt(3, age);        statement.setString(4, email);
        statement.setInt(5, id);        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Информация о пользователе успешно обновлена.");
        } else {            System.out.println("Пользователь с указанным ID не найден.");
        }    }
    public static void deleteUser(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите ID пользователя для удаления: ");            int id = scanner.nextInt();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
        statement.setInt(1, id);            int rowsAffected = statement.executeUpdate();
        System.out.println("Пользователь успешно удален.");            if (rowsAffected > 0) {
            System.out.println("Пользователь успешно удален.");            } else {
            System.out.println("Пользователь с указанным ID не найден.");            }
    }
}