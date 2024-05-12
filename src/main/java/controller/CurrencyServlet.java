package controller;

import com.google.gson.Gson;
import model.currency.Currency;
import model.currency.CurrencyDao;
import model.currency.CurrencyDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CurrencyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String url = request.getRequestURL().toString(); //"http://localhost:8080/currency/EUR"
        String[] parts = url.split("/");
        String code = parts[parts.length - 1];
        request.setAttribute("code", code);

        try {
            String currency = getCurrencyByCode(request, response);
            if (currency == null) {
                return;
            }
            response.getWriter().write(currency);
        } catch (SQLException e) {
            response.setStatus(500);
        }
    }

    private String getCurrencyByCode(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        CurrencyDao currencyDAO = new CurrencyDaoImpl();
        String code = (String) request.getAttribute("code");
        if (code.equals("currency")) {
            response.setStatus(400);
            return "Error: the currency code is missing in the URL.";
        }
        /*
        Handle exceptions:
        Успех - 200
        Код валюты отсутствует в адресе - 400
        Валюта не найдена - 404
        Ошибка (например, база данных недоступна) - 500
         */
        Currency currency = currencyDAO.getCurrencyByCode(code);
        if (currency == null) {
            response.setStatus(404);
            return "Error: currency not found.";
        } else {
            response.setStatus(200);
        }
        return new Gson().toJson(currency);
    }
}
