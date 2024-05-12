package model.exchangeRate;

import model.JDBCConnector;
import model.currency.Currency;
import model.currency.CurrencyDao;
import model.currency.CurrencyDaoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDaoImpl implements ExchangeRateDao {
    private static final String SELECT_ALL_EXCHANGE_RATES_SQL = "select * from ExchangeRates;";
    private static final String SELECT_EXCHANGE_RATE_BY_CODES_SQL = "select * from ExchangeRates where BaseCurrencyID = ? and TargetCurrencyID = ?";

    @Override
    public List<ExchangeRate> getAllExchangeRates() throws SQLException {
        Connection connection = JDBCConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_EXCHANGE_RATES_SQL);
        ResultSet resultSet = statement.executeQuery();

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        while (resultSet.next()) {
            int id = Integer.parseInt(resultSet.getString("ID"));
            int baseCurrencyId = Integer.parseInt(resultSet.getString("BaseCurrencyID"));
            int targetCurrencyId = Integer.parseInt(resultSet.getString("TargetCurrencyID"));
            float rate = Float.parseFloat(resultSet.getString("Rate"));

            exchangeRates.add(new ExchangeRate(id, baseCurrencyId, targetCurrencyId, rate));
        }

        return exchangeRates;
    }

    @Override
    public ExchangeRate getExchangeRateByCodes(String codes) throws SQLException {
        String targetCurrencyCode = codes.substring(2);
        String baseCurrencyCode = codes.substring(0, 2);
        CurrencyDao currencyDao = new CurrencyDaoImpl();

        Currency baseCurrency;
        Currency targetCurrency;
        ExchangeRate exchangeRate;

        try {
            baseCurrency = currencyDao.getCurrencyByCode(baseCurrencyCode);
            targetCurrency = currencyDao.getCurrencyByCode(targetCurrencyCode);
        } catch (SQLException e) {
            return null; //HttpError 400
        }
        Connection connection = JDBCConnector.getConnection();
        int baseCurrencyId = baseCurrency.getId();
        int targetCurrencyId = targetCurrency.getId();

        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXCHANGE_RATE_BY_CODES_SQL);
        preparedStatement.setString(1, String.valueOf(baseCurrencyId));
        preparedStatement.setString(2, String.valueOf(targetCurrencyId));

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = Integer.parseInt(resultSet.getString("ID"));
            float rate = Float.parseFloat(resultSet.getString("Rate"));

            exchangeRate = new ExchangeRate(id, baseCurrencyId, targetCurrencyId, rate);
        } else {
            return null; //HttpError 404
        }

        return exchangeRate;
    }

    @Override
    public ExchangeRate insertExchangeRate() {
        return null;
    }

    @Override
    public ExchangeRate patchExchangeRate() {
        return null;
    }


}
