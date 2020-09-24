package demo;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author 74716
 * @Date 2019/11/1 17:02
 **/
public class Redis
{

    private static final  String host="172.16.9.112";
    private static final  int port=6380;
    private static final  String pwd="Tospur1998";

    private static final String LOCK_SUCCESS = "OK";
    private static final String LOCK_KEY="LOCK_KEY";
    private static final long UNLOCK_SUCCESS=1;

    public static void main(String[] args) {
        Jedis connection = createConnection(host, port, pwd);
//        System.out.println(getKey(connection, LOCK_KEY));
//        int i =312388115;
//        boolean lock_key = unlock(connection,LOCK_KEY,i+"");//tryGetLock(connection, LOCK_KEY, i + "", 300000);
//        if (lock_key){
//            System.out.println("成功！");
//        }else{
//            System.out.println("失败！");
//        }
//        System.out.println(getKey(connection, LOCK_KEY));
        System.out.println(getKey(connection, "f951ab3118c844a3947eacce4ad65c51#U74716"));
//        String A="System.out.println(getKey(connection, LOCK_KEY));";
//        String B="System.out.println(getKey(connection, LOCK_KEY));";
//        System.out.println("---->>>"+A.hashCode());
//        System.out.println("---->>>"+B.hashCode());
    }

    
    /**
     * @Author 74716
     * @Description  尝试获取锁
     * @Date 17:18 2019/11/1
     * @param key lockKey
     * @param value lookValue
     * @param time 过期时间
     * @return true/false
     **/
    private static boolean tryGetLock(Jedis jedis,String key,String value,int time) {
        String set = jedis.set(key, value, "NX", "PX", time);
        jedis.close();
        if (LOCK_SUCCESS.equals(set))
            return true;
        return false;
    }

    
    /**
     * @Author 74716
     * @Description  解锁(听说 redis执行Lua脚本是原子的)
     * @Date 17:29 2019/11/1
     * @param key lockKey
     * @param value lockValue
     * @return 
     **/
    private static boolean unlock(Jedis jedis,String key,String value){
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object eval = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
        if (eval.equals(UNLOCK_SUCCESS)){
            return true;
        }
        return false;
    }

    
    /**
     * @Author 74716
     * @Description  获取所有key
     * @Date 18:53 2019/11/1
     * @param 
     * @return 
     **/
    private static Set<String> getLikeKeys(Jedis jedis,String l) {
        Set<String> keys = jedis.keys(l);//模糊匹配key
        if (null != keys) {
            keys.forEach(it -> {
                System.out.println(it);
            });
        }
        jedis.close();
        return keys;
    }

    
    /**
     * @Author 74716
     * @Description  获取指定key
     * @Date 18:55 2019/11/1
     * @param 
     * @return 
     **/
    private static String getKey(Jedis jedis,String key){
        String s = jedis.get(key);
        jedis.close();
        return s;
    }

    
    /**
     * @Author 74716
     * @Description  创建redis连接
     * @Date 18:55 2019/11/1
     * @param host ip
     * @param port 端口
     * @param pwd 密码
     * @return 
     **/
    private static Jedis createConnection(String host,int port,String pwd) {
        Jedis jedis = new Jedis(host, port);
        if (null != pwd && pwd.length() > 0)
            jedis.auth(pwd);
        return jedis;
    }

    public static Jedis client() {
        return createConnection(host, port, pwd);
    }
}
