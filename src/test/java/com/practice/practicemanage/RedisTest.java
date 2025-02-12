package com.practice.practicemanage;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    protected RedisTemplate redisTemplate;
    @Autowired
    protected UserRepository userRepository;

    @Test
    public void testString(){
        String key = "user:token:035";
        redisTemplate.opsForValue().set(key, "hello", 30, TimeUnit.MINUTES);
        System.out.printf("value: %s\n", redisTemplate.opsForValue().get(key));
    }

    @Test
    public void testString2(){
//        访问次数
        String key = "article:A00035:content";
        redisTemplate.opsForValue().increment(key);
        System.out.printf("value: %s\n", redisTemplate.opsForValue().get(key));
    }

    @Test
    public void testString3(){
//        存储对象，不需要后续的修改，只用于查询
//        HashMap是使用 具体类类型 来声明变量的，而Map是使用 接口类型 来声明变量的
//        Map可以通过更改new后面的具体类类型来更改Map的实现类，而HashMap只能是HashMap
        HashMap<String, Object> users = new HashMap<>();
        users.put("id", 1);
        users.put("name", "zhangsan");
        users.put("age", 18);
        users.put("birthday", "2021-01-01");
        String key = "users:002";
        redisTemplate.opsForValue().set(key, users);
        System.out.printf("value: %s\n", redisTemplate.opsForValue().get(key));

//        从数据库中获取数据并保存到redis中
        Optional<User> userList =  userRepository.findById(1);
        userList.ifPresent(user -> {
            String keys = "users: " + user.getId();
            redisTemplate.opsForValue().set(keys, user);
        });
    }

    @Test
    public void testHashMap(){
        String key = "user:arkUser:001";
        Map<String, Object> user = new HashMap<>();
        user.put("user", "userName");
        user.put("pass", "passWord");
        List<Map<String, Object>> userList = List.of(
                Map.of("id", 1, "name", "陈", "age", 12, "birthday", "2021-01-01"),
                Map.of("id", 2, "name", "张", "age", 19, "birthday", "2021-01-02"),
                Map.of("id", 3, "name", "李", "age", 15, "birthday", "2021-01-02")
        );
        user.put("userList", userList);
        user.put("count", userList.size());
        user.put("status", 1);
        redisTemplate.opsForHash().putAll(key, user);
//        System.out.printf("value: %s\n", redisTemplate.opsForHash().entries(key));
        System.out.printf("value: %s\n", redisTemplate.opsForHash().get(key, "userList"));
    }

    @Test
    public void testHashList(){
        List<User> userList = userRepository.findAll();
        Map<String, Object> user = new HashMap<>();
        String key = "user: UserAll";
        for (User users: userList) {
//            key相同会导致覆盖
            user.put(String.valueOf(users.getId()), users);
        };
        redisTemplate.opsForHash().putAll(key, user);
        System.out.printf("value: %s\n", redisTemplate.opsForHash().entries(key));

//        List返回前端形式 返回前端return ResponseMessage.success("获取用户列表成功", userList);格式更规范
//        List<Object> userList2 = new ArrayList<>(redisTemplate.opsForHash().values(key));

    }

    @Test
//    无法存储重复数据
    public void testSet(){
        String key = "user: 0002: fans";
//        Set<User> userList = new HashSet<>(userRepository.findAll());
//        redisTemplate.opsForSet().add(key, userList);
        redisTemplate.opsForSet().add(key, "1", "2", "3", "4", "5");
        System.out.printf("value: %s\ncount: %d\n", redisTemplate.opsForSet().members(key), redisTemplate.opsForSet().size(key));
    }

    @Test
//    有序且唯一的集合
    public void testZSet(){
        String key = "user: 0002: Friends";
        redisTemplate.opsForZSet().add(key, "一号", System.currentTimeMillis());
        redisTemplate.opsForZSet().add(key, "二号", System.currentTimeMillis());
        redisTemplate.opsForZSet().add(key, "三号", System.currentTimeMillis());
        Set set = redisTemplate.opsForZSet().reverseRange(key, 0, -1);
        System.out.println(set);
    }

    @Test
    public void testList2WithTransaction() {
        String key = "user:queues:0001";
        Map<String, Object> order1 = new HashMap<>();
        Map<String, Object> order2 = new HashMap<>();

        order1.put("id", 1);
        order1.put("name", "zhangsan");
        order1.put("age", 18);
        order1.put("birthday", "2021-01-01");
        order2.put("id", 2);
        order2.put("name", "lisi");
        order2.put("age", 20);
        order2.put("birthday", "2021-01-02");

        // 开启事务
//        redisTemplate.multi(); // 开始事务

        try {
            // 写入队列
            redisTemplate.opsForList().leftPush(key, order1);
            redisTemplate.opsForList().leftPush(key, order2);

            // 提交事务
//            redisTemplate.exec();  // 提交事务
        } catch (Exception e) {
            // 发生异常时回滚事务
//            redisTemplate.discard();  // 取消事务
            e.printStackTrace();
        }

        while (true) {
            // 读取队列
            Map<String, Object> order = (Map<String, Object>) redisTemplate.opsForList().rightPop(key);
            if (order == null) {
                break;
            }
            System.out.printf("value: %s\n", order);
        }
    }


}


