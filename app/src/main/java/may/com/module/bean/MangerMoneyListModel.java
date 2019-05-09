package may.com.module.bean;

/**
 * Created by Administrator on 2019/5/6.
 */

public class MangerMoneyListModel {
    private String mangerMoney; // 存入金额
    private String remarks;  // 存入备注
    private String time;  // 存入时间

    public MangerMoneyListModel(String mangerMoney,String remarks,String time) {
        this.mangerMoney = mangerMoney;
        this.remarks = remarks;
        this.time = time;
    }

    public String getMangerMoney() {
        return mangerMoney;
    }

    public void setMangerMoney(String mangerMoney) {
        this.mangerMoney = mangerMoney;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
