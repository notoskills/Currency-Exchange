package model.exchangeRate;

import java.sql.SQLException;
import java.util.List;

public interface ExchangeRateDao {
    List<ExchangeRate> getAllExchangeRates() throws SQLException;
    ExchangeRate getExchangeRateByCodes(String codes) throws SQLException;
    ExchangeRate insertExchangeRate() throws SQLException;
    ExchangeRate patchExchangeRate() throws SQLException;
}
