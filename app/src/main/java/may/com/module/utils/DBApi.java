package may.com.module.utils;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import may.com.module.bean.MangerMoneyAimModel;
import may.com.module.bean.User;


public class DBApi {
    /**
     * @param string 用户名
     * @param pwd    密码
     */
    public static long login(String string, String pwd) {
        long id = -1;
        List<User> usrs = User.find(User.class, "account = ? and password = ?", string, pwd);
        if (usrs.size() > 0) {
            return usrs.get(0).getId();
        }
        return id;
    }

    /**
     * @param usr 用户名
     * @return 注册的用户ID, 如果为-1则表示用户已注册
     */
    public static long register(User usr) {
        String phone = usr.getAccount();
        if (isExist(phone)) {
            return -1;
        }
        return usr.save();
    }

    public static User getUsr(long id) {
        User usr = User.findById(User.class, id);
        return usr;
    }

    public static long updateUsr(User usr) {
        return usr.save();
    }

    /**
     * @param string 用户名
     * @return 用户是否存在
     */
    public static boolean isExist(String string) {
        List<User> users = User.find(User.class, "account = ?", string);
        if (users.size() > 0) {
            return true;
        }

        return false;
    }

    public static <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }


// 查询该用户下所有存钱目标
    public static List<MangerMoneyAimModel> getSaveMoneyList(long userId){
      List<MangerMoneyAimModel> list =  MangerMoneyAimModel.find(MangerMoneyAimModel.class, "user_id = ?",userId+"");
      return list;
    }

    //获取某个存钱目标
    public static MangerMoneyAimModel getOneMangerMoneyAimModel(long id) {
       MangerMoneyAimModel model = MangerMoneyAimModel.findById(MangerMoneyAimModel.class,id);
        return model;
    }




}
