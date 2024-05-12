package model.exchangeRate;

import model.currency.Currency;
import model.currency.CurrencyDao;
import model.currency.CurrencyDaoImpl;

import java.sql.SQLException;

public class ExchangeRate {
    private int id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private float rate;

    public ExchangeRate() {
    }

    public ExchangeRate(int baseCurrencyId, int targetCurrencyId, float rate) throws SQLException {
        super();
        CurrencyDao currencyDao = new CurrencyDaoImpl();
        this.baseCurrency = currencyDao.getCurrencyById(baseCurrencyId);
        this.targetCurrency = currencyDao.getCurrencyById(targetCurrencyId);
        this.rate = rate;
    }

    public ExchangeRate(int id, int baseCurrencyId, int targetCurrencyId, float rate) throws SQLException {
        super();
        CurrencyDao currencyDao = new CurrencyDaoImpl();
        this.id = id;
        this.baseCurrency = currencyDao.getCurrencyById(baseCurrencyId);
        this.targetCurrency = currencyDao.getCurrencyById(targetCurrencyId);
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
