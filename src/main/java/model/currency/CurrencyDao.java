package model.currency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public interface CurrencyDao {
    List<Currency> getAllCurrencies() throws SQLException;
    Currency insertCurrency(HttpServletRequest request, HttpServletResponse response) throws SQLException;
    Currency getCurrencyByCode(String code) throws SQLException;
    Currency getCurrencyById(int id) throws SQLException;

}
