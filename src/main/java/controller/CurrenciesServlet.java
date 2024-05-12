package controller;

import com.google.gson.Gson;
import model.currency.Currency;
import model.currency.CurrencyDao;
import model.currency.CurrencyDaoImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;


public class CurrenciesServlet extends HttpServlet {

    /*
    Getting the list of all currencies.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try {
            String currencies = getAllCurrencies();
            response.getWriter().write(currencies);
        } catch (SQLException e) {
            response.setStatus(500);
        }
    }

    /*
    Inserting a new currency into the database.
    Добавление новой валюты в базу. Данные передаются в теле запроса в виде полей формы (x-www-form-urlencoded).
    Поля формы - name, code, sign. Пример ответа - JSON представление вставленной в базу записи, включая её ID.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try {
            String currency = addCurrency(request, response);
            response.getWriter().write(currency);
        } catch (SQLException e) {
            response.setStatus(500);
        }
    }

    private String getAllCurrencies() throws SQLException {
        CurrencyDaoImpl currencyDAO = new CurrencyDaoImpl();
        List<Currency> currencies = currencyDAO.getAllCurrencies();
        return new Gson().toJson(currencies);
    }

    private String addCurrency(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        CurrencyDao currencyDAO = new CurrencyDaoImpl();
        Currency currency = currencyDAO.insertCurrency(request, response);
        return new Gson().toJson(currency);
    }
}