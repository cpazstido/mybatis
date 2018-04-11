package com.cf;

import com.cf.dao.Impl.UserDaoImpl;
import com.cf.dao.UserDao;
import com.cf.dao.UserMapper;
import com.cf.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        useByDao();
    }

    public void print(String txt){
        System.out.println(txt);
    }

    /**
     * 以dao的方式操作数据
     */
    public static void useByDao() throws Exception{
        UserDao userDao = new UserDaoImpl();
        User user = userDao.findUserById(7);
        System.out.println(user);
    }

    /**
     * 以mapper的方式操作数据
     */
    public static void useByMapper() throws Exception{
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();
        //---------------------
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> user = mapper.findUserAll();
        for (User user2 : user) {
            System.out.println(user2.getUsername());
        }
        //----------------------
        session.close();
    }
}
