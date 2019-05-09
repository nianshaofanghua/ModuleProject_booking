package may.com.module.bean;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2019/5/6.
 */

public class MangerMoneyAimModel extends SugarRecord implements Serializable{
    private String beginTime; // 存钱起始时间
    private String endTime;  // 存钱结束时间
    private String saveMoney;  // 存钱目标金额
    private String saveMoneyList;  // 序列化 存取列表
private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSaveMoneyList() {
        return saveMoneyList;
    }

    public void setSaveMoneyList(String saveMoneyList) {
        this.saveMoneyList = saveMoneyList;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(String saveMoney) {
        this.saveMoney = saveMoney;
    }
}
