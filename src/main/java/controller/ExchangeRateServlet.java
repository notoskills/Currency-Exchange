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

public class ExchangeRateServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String url = request.getRequestURL().toString(); //"http://localhost:8080/exchangeRate/EURUSD"
        String[] parts = url.split("/");
        String codes = parts[parts.length - 1];

        try {
            String exchangeRate = getExchangeRateByCodes(codes);
            if (exchangeRate == null) {
                return;
            }
            response.getWriter().write(exchangeRate);
        } catch (SQLException e) {
            response.setStatus(500);
        }
    }

    private String getExchangeRateByCodes(String codes) throws SQLException {
        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();
        ExchangeRate exchangeRate = exchangeRateDao.getExchangeRateByCodes(codes);
        return new Gson().toJson(exchangeRate);
    }
}
