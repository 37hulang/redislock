package demo;

import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.*;

/**
 * @Description
 * @Author 74716
 * @Date 2019/11/23 10:21
 **/
public class RedisListDemo
{

    private static final String redis_list="REDIS_LIST";
    private static final String redis_set="REDIS_SET1";
    private static final String redis_set_object="REDIS_SET_OBJECT";
    private static final String reids_set_list_object="REIDS_SET_LIST_OBJECT";
    public static void main(String[] args) throws IOException {
        //setRedisList();
        //setRedisSet();
        //ifExists(redis_set);
        //setRedisSet();
        //setObject();
        setLisetObject();
    }

    
    /**
     * @Author 74716
     * @Description  写入到redis
     * @Date 10:23 2019/11/23
     * @param 
     * @return 
     **/
    public static void setRedisList() {
        Jedis client = Redis.client();
        client.lpush(redis_list, "a", "b", "c", "d", "e", "f");
        List<String> lrange = client.lrange(redis_list, 0, -1);
        lrange.forEach(it -> {
            System.out.println("--->>>" + it);
        });
    }

    public static void setRedisSet(){
        Jedis client = Redis.client();
        client.sadd(redis_set,"a","c");
        client.sadd(redis_set,"b");
//        System.out.println(client.scard(redis_set));
        client.smembers(redis_set).forEach(it->{
            System.out.println(it);
        });
    }

    public static void ifExists(String key){
        Jedis client = Redis.client();
        System.out.println(client.sismember(key, "d"));
    }

    public static void setObject() throws IOException {
        Jedis client = Redis.client();
        Map<String,Object> map=new HashMap<>();
        map.put("name","拉不拉卡");
        map.put("age",123);
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream=null;
        ByteArrayInputStream byteArrayInputStream=null;
        ObjectInputStream objectInputStream=null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(map);
            client.set(redis_set_object.getBytes(),byteArrayOutputStream.toByteArray());
            byte[] bytes = client.get(redis_set_object.getBytes());
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Map<String, Object> map1 = (Map<String, Object>) objectInputStream.readObject();
            System.out.println(map1.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null!=byteArrayOutputStream){
                byteArrayOutputStream.close();
            }
            if (null!=objectOutputStream){
                objectOutputStream.close();
            }
            if (null!=byteArrayInputStream){
                byteArrayInputStream.close();
            }
            if (null!=objectInputStream){
                objectInputStream.close();
            }
        }
    }

    public static void setLisetObject() throws IOException {
        Jedis client = Redis.client();
        byte[] bytes = null;
        List<DemoObj> mapList = new LinkedList<>();
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("name", "旧老去");
//        map1.put("age", 200);
        mapList.add(new DemoObj("旧老去",200));
//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("name", "喔喔喔");
//        map2.put("age", 230);
        mapList.add(new DemoObj("喔喔喔",230));
        ByteArrayOutputStream byteArrayOutputStream=null;
        ObjectOutputStream objectOutputStream=null;
        ByteArrayInputStream byteArrayInputStream=null;
        ObjectInputStream objectInputStream=null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(mapList);
            bytes = byteArrayOutputStream.toByteArray();
            client.set(reids_set_list_object.getBytes(), bytes);
            byte[] bytesResult = client.get(reids_set_list_object.getBytes());
            byteArrayInputStream=  new  ByteArrayInputStream(bytesResult);
            objectInputStream=new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            List<DemoObj> object1 = (List<DemoObj>) object;
            if (null!=object1){
                for (DemoObj map:
                        object1) {
                    System.out.println(map.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null!=byteArrayOutputStream){
                byteArrayOutputStream.close();
            }
            if (null!=objectOutputStream){
                objectOutputStream.close();
            }
            if (null!=byteArrayInputStream){
                byteArrayInputStream.close();
            }
            if (null!=objectInputStream){
                objectInputStream.close();
            }
        }
    }
}
