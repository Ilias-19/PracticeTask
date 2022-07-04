import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "ilyas";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/PracticeTaskDB";

    public static void main(String[] args) throws SQLException {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            System.out.println("Внутренняя ошибка сервера");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int code = Authentication.authenticate();
        if (code == 0) {
            System.out.println("Login success");
        } else {
            System.out.println("Login failed");
            System.exit(0);
        }

        while (true) {
            System.out.println();
            System.out.println("1. Добавить доктора");
            System.out.println("2. Добавить пациента");
            System.out.println("3. Добавить приём пациента к доктору");
            System.out.println("4. Вывод всех пациентов");
            System.out.println("5. Редактирование ФИО пациентов");
            System.out.println("6. Удаление пациентов");
            System.out.println("7. Редактирование статуса приёма");
            System.out.println("8. Вывод всех приёмов одного пациента");
            System.out.println("9. Выход");
            System.out.println();
            int command = scanner.nextInt();


            String sql;
            PreparedStatement preparedStatement;
            String date;
            String patientName;
            switch (command) {
                case 1:
                    sql = "insert into doctor (doctor_name) values (?)";
                    preparedStatement = connection.prepareStatement(sql);
                    System.out.println("Введите имя доктора: ");
                    scanner.nextLine();
                    String doctorName = scanner.nextLine();
                    preparedStatement.setString(1, doctorName);
                    preparedStatement.executeUpdate();
                    break;
                case 2:
                    sql = "insert into patient (patient_name, registration_date) values (?, ?)";
                    preparedStatement = connection.prepareStatement(sql);

                    System.out.println("Введите имя пациента: ");
                    scanner.nextLine();
                    patientName = scanner.nextLine();
                    preparedStatement.setString(1, patientName);

                    System.out.println("Введите дату регистрации: ");
                    date = scanner.nextLine();
                    preparedStatement.setDate(2, Date.valueOf(date));
                    preparedStatement.executeUpdate();
                    break;
                case 3:
                    sql = "insert into appointment (patient_id, date, state) values (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql);

                    System.out.println("Введите ID пациента: ");
                    int idPatient = scanner.nextInt();
                    preparedStatement.setInt(1, idPatient);

                    System.out.println("Введите дату: ");
                    scanner.nextLine();
                    date = scanner.nextLine();
                    preparedStatement.setDate(2, Date.valueOf(date));

                    System.out.println("Введите статус приема: ");
                    String state = scanner.nextLine();
                    preparedStatement.setString(3, state);
                    preparedStatement.executeUpdate();
                    break;
                case 4:
                    Statement statement = connection.createStatement();
                    String SQL_SELECT_PATIENTS = "select * from patient";
                    ResultSet result = statement.executeQuery(SQL_SELECT_PATIENTS);
                    while (result.next()) {
                        System.out.println(result.getInt("patient_id") + " " + result.getString("patient_name")
                                + " " + result.getDate("registration_date"));
                    }
                    break;
                case 5:
                    sql ="update patient set name = ? where patient_id = ?";
                    preparedStatement = connection.prepareStatement(sql);

                    System.out.println("Введите ID пациента: ");
                    idPatient = scanner.nextInt();
                    preparedStatement.setInt(2, idPatient);

                    System.out.println("Введите новое ФИО пациента: ");
                    scanner.nextLine();
                    patientName = scanner.nextLine();
                    preparedStatement.setString(1, patientName);
                    preparedStatement.executeUpdate();
                    break;
                case 6:
                    sql = "delete from patient where patient_id = ?";
                    preparedStatement = connection.prepareStatement(sql);

                    System.out.println("Введите ID пациента: ");
                    idPatient = scanner.nextInt();
                    preparedStatement.setInt(1, idPatient);
                    preparedStatement.executeUpdate();
                    break;
                case 7:
                    sql ="update appointment set state = ? where appointment_id = ?";
                    preparedStatement = connection.prepareStatement(sql);

                    System.out.println("Введите ID приема");
                    int idAppointment = scanner.nextInt();
                    preparedStatement.setInt(2, idAppointment);

                    System.out.println("Введите новый статус приема: ");
                    scanner.nextLine();
                    String appointmentName = scanner.nextLine();
                    preparedStatement.setString(1, appointmentName);
                    preparedStatement.executeUpdate();
                    break;
                case 8:
                    System.out.println("Введите ID пациента: ");
                    idPatient = scanner.nextInt();
                    statement = connection.createStatement();
                    String SQL_SELECT_APPOINTMENTS = String.format("select * from appointment where patient_id = %d",idPatient);
                    result = statement.executeQuery(SQL_SELECT_APPOINTMENTS);

                    while (result.next()) {
                        System.out.println(result.getInt("appointment_id") + " " + result.getString("state")
                                + " " + result.getDate("date") + " " + result.getInt("patient_id"));
                    }
                    break;
                case 9:
                    System.exit(0);
                    break;
            }
        }



    }
}
