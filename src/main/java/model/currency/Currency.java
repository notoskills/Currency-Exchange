package model.currency;

import com.google.gson.Gson;

public class Currency {
    private int id;
    private String code;
    private String name;
    private String sign;

    public Currency() {}

    public Currency(String code, String name, String sign) {
        super();
        this.code = code;
        this.name = name;
        this.sign = sign;
    }

    public Currency(int id, String code, String name, String sign) {
        super();
        this.id = id;
        this.code = code;
        this.name = name;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
