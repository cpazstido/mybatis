package com.cf;

import com.cf.dao.UserMapper;
import com.cf.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class UseByMapper {
    @Test
    public void findUserByID() throws Exception{
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();
        //---------------
        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.findUserById(2);
        System.out.println(user);
        //--------------
        session.close();
    }
    @Test
    public void findAll() throws Exception{
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
    @Test
    public void insertTest() throws Exception{
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();
        //---------------------
        User user = new User();
        user.setUsername("lalala");
        user.setPassword("asdf");
        user.setAge(12);

        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.insertUser(user);
        session.commit();
        //----------------------
        session.close();
    }
    @Test
    public void deleteUserById() throws Exception{
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();
        //---------------------
        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.deleteUserById(2);
        session.commit();
        //----------------------
        session.close();
    }
    @Test
    public void updateUserPassword() throws Exception{
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();
        //---------------------
        User user = new User();
        user.setId(4);
        user.setPassword("newPassword3");

        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.updateUserPassword(user);
        session.commit();
        //----------------------
        session.close();
    }
}
