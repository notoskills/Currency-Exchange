package model.currency;


import model.JDBCConnector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;

public class CurrencyDaoImpl implements CurrencyDao {
    private static final String INSERT_CURRENCY_SQL = "insert into Currencies(FullName, Code, Sign) values (?,?,?);";
    private static final String SELECT_CURRENCY_BY_CODE_SQL = "select * from Currencies where Code = ?;";
    private static final String SELECT_ALL_CURRENCIES_SQL = "select * from Currencies;";
    private static final String SELECT_CURRENCY_BY_ID_SQL = "select * from Currencies where ID = ?;";

    @Override
    public Currency insertCurrency(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Connection connection = JDBCConnector.getConnection();
        Currency currency = new Currency();

        String[] requiredParams = {"name", "code", "sign"};

        if (request.getParameterMap().keySet().size() != 3) {
            response.setStatus(400);
            return null;
        }

        for (String param : request.getParameterMap().keySet()) {
            if (!Arrays.asList(requiredParams).contains(param)) {
                response.setStatus(400);
                return null;
            }
        }

        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String sign = request.getParameter("sign");

        currency.setName(name);
        currency.setCode(code);
        currency.setSign(sign);

        //test, if a currency with the same code is already exist
        if (!(getCurrencyByCode(code) == null)) {
            response.setStatus(409);
            return null;
        }

        PreparedStatement statement = connection.prepareStatement(INSERT_CURRENCY_SQL);

        statement.setString(1, currency.getName());
        statement.setString(2, currency.getCode());
        statement.setString(3, currency.getSign());

        //insertion
        statement.executeUpdate();

        int id = getCurrencyByCode(currency.getCode()).getId();

        currency.setId(id);
        response.setStatus(201);
        connection.close();
        return currency;
    }


    public List<Currency> getAllCurrencies() throws SQLException {
        Connection connection = JDBCConnector.getConnection();
        List<Currency> currencies = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CURRENCIES_SQL);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("FullName");
            String code = resultSet.getString("Code");
            String sign = resultSet.getString("Sign");
            currencies.add(new Currency(id, name, code, sign));
        }

        connection.close();
        return currencies;
    }

    @Override
    public Currency getCurrencyByCode(String code) throws SQLException {
        Connection connection = JDBCConnector.getConnection();

        PreparedStatement statement = connection.prepareStatement(SELECT_CURRENCY_BY_CODE_SQL);
        statement.setString(1, code);

        ResultSet resultSet = statement.executeQuery();

        // if there is no such currency -> return null
        if (!resultSet.next()) {
            return null;
        }

        Currency currency = new Currency();
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("FullName");
        String sign = resultSet.getString("Sign");

        currency.setId(id);
        currency.setName(name);
        currency.setCode(code);
        currency.setSign(sign);

        connection.close();
        return currency;
    }

    @Override
    public Currency getCurrencyById(int id) throws SQLException {
        Connection connection = JDBCConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_CURRENCY_BY_ID_SQL);
        statement.setString(1, String.valueOf(id));
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        Currency currency = new Currency();

        String name = resultSet.getString("FullName");
        String code = resultSet.getString("Code");
        String sign = resultSet.getString("Sign");

        currency.setId(id);
        currency.setName(name);
        currency.setCode(code);
        currency.setSign(sign);

        connection.close();
        return currency;
    }

}
