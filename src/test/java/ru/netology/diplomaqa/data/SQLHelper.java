package ru.netology.diplomaqa.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.diplomaqa.payment.Credit;
import ru.netology.diplomaqa.payment.Payment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final String URL = System.getProperty("db.url");
    private static final String USERNAME = System.getProperty("db.username");
    private static final String PASSWORD = System.getProperty("db.password");
    private static Connection connect;
    private static QueryRunner runner = new QueryRunner();

    private static Connection getConnection() {
        try {
            connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connect;
    }

    public static String getPaymentStatus() {
        var payStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";

        try (var connect = getConnection()) {
            var paymentStatus = runner.query(connect, payStatus, new BeanHandler<>(Payment.class));
            return paymentStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getPaymentAmount() {
        var payAmount = "SELECT amount FROM payment_entity ORDER BY created DESC LIMIT 1";

        try (var connect = getConnection()) {
            var paymentAmount = runner.query(connect, payAmount, new BeanHandler<>(Payment.class));
            return paymentAmount.getAmount();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getCreditStatus() {
        var cStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";

        try (var connect = getConnection()) {
            var creditStatus = runner.query(connect, cStatus, new BeanHandler<>(Credit.class));
            return creditStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}
