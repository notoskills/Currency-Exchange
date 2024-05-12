package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.currency.Currency;
import model.currency.CurrencyDao;
import model.currency.CurrencyDaoImpl;
import model.exchangeRate.ExchangeRate;
import model.exchangeRate.ExchangeRateDao;
import model.exchangeRate.ExchangeRateDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ExchangeRatesServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            String exchangeRates = getAllExchangeRates();
            response.getWriter().write(exchangeRates);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getAllExchangeRates() throws SQLException {
        ExchangeRateDao exchangeRateDAO = new ExchangeRateDaoImpl();
        List<ExchangeRate> exchangeRates = exchangeRateDAO.getAllExchangeRates();

        return new Gson().toJson(exchangeRates);
    }
}
