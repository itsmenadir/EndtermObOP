import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CalorieCounter {

    private static final String URL = "jdbc:postgresql://localhost:5432/product";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        try {

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Соединение с базой данных установлено.");


            String productName = "Курица";
            double protein = 18.2;
            double fat = 18.4;
            double carbs = 0.0;
            double weight = 250.0;


            insertProduct(connection, productName, protein, fat, carbs, weight);

            double calories = calculateCalories(protein, fat, carbs);
            System.out.println("Калорийность продукта: " + calories + " ккал.");


            connection.close();
            System.out.println("Соединение с базой данных закрыто.");
        } catch (SQLException e) {
            System.out.println("Ошибка при взаимодействии с базой данных: " + e.getMessage());
        }
    }

    private static void insertProduct(Connection connection, String name, double protein,
                                      double fat, double carbs, double weight) throws SQLException {
        String insertQuery = "INSERT INTO productpfc(productName, protein, fat, carbs, weight) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, name);
        preparedStatement.setDouble(2, protein);
        preparedStatement.setDouble(3, fat);
        preparedStatement.setDouble(4, carbs);
        preparedStatement.setDouble(5, weight);
        preparedStatement.executeUpdate();
        System.out.println("Данные о продукте успешно добавлены в таблицу.");
    }

    private static double calculateCalories(double protein, double fat, double carbs) {

        return 4 * protein + 4 * carbs + 9 * fat;
    }
}
