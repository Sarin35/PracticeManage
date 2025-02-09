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
        HashMap<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("name", "zhangsan");
        user.put("age", 18);
        user.put("birthday", "2021-01-01");
        String key = "user:002";
        redisTemplate.opsForValue().set(key, user);
        System.out.printf("value: %s\n", redisTemplate.opsForValue().get(key));
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
    public void testList(){
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

}


