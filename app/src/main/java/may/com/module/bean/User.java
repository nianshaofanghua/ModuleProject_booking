package may.com.module.bean;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class User extends SugarRecord {

    @Unique
    private String account;
    private String password;
    private String consumeDayMoney;   // 消费日额度
    private String cconsumeMonthMoney;  // 消费月额度

    public User() {

    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getConsumeDayMoney() {
        return consumeDayMoney;
    }

    public void setConsumeDayMoney(String consumeDayMoney) {
        this.consumeDayMoney = consumeDayMoney;
    }

    public String getCconsumeMonthMoney() {
        return cconsumeMonthMoney;
    }

    public void setCconsumeMonthMoney(String cconsumeMonthMoney) {
        this.cconsumeMonthMoney = cconsumeMonthMoney;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
