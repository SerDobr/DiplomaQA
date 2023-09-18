package ru.netology.diplomaqa.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.diplomaqa.payment.Credit;
import ru.netology.diplomaqa.payment.Payment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static Connection connect;

    private static Connection getConnection() {
        try {
            connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "app", "pass");
            // jdbc:mysql://localhost:3306/app, "app", "pass"
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connect;
    }

    public static String getPaymentStatus() {
        var runner = new QueryRunner();
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
            var runner = new QueryRunner();
            var paymentAmount = runner.query(connect, payAmount, new BeanHandler<>(Payment.class));
            return paymentAmount.getAmount();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getCreditStatus() {
        var runner = new QueryRunner();
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
