package com.cf;

import com.cf.dao.Impl.UserDaoImpl;
import com.cf.dao.UserDao;
import com.cf.dao.UserMapper;
import com.cf.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class UseByMapper {
    @Test
    public void findUserByID() throws Exception {
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
    public void findAll() throws Exception {
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
    public void insertTest() throws Exception {
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
    public void deleteUserById() throws Exception {
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
    public void updateUserPassword() throws Exception {
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

    @Test
    public void testTransaction() {
        UserDao userDao = new UserDaoImpl();
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num);
        for (int i = 0; i < num; i++) {
            new Thread(new TestThread(countDownLatch, cyclicBarrier, i)).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class TestThread implements Runnable {
        private CountDownLatch countDownLatch;
        private CyclicBarrier cyclicBarrier;
        private int i;

        @Override
        public void run() {
            try {
                System.out.println(i + "等待");
                cyclicBarrier.await();
                System.out.println(i + "开始执行");
                String resource = "SqlMapConfig.xml";
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession session = factory.openSession(false);
                UserMapper mapper = session.getMapper(UserMapper.class);
                User user = mapper.findUserById(1);
//                Random random = new Random(1000);
//                int time = random.nextInt();
//                if (time > 0) {
////                    Thread.sleep(time);
//                }
                user.setAge(user.getAge() - 1);
                mapper.updateAge(user);
                session.commit();
                session.close();
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public TestThread(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier, int i) {
            this.countDownLatch = countDownLatch;
            this.cyclicBarrier = cyclicBarrier;
            this.i = i;
        }

    }
}
