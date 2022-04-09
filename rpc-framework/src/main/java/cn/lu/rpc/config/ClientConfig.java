package cn.lu.rpc.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * cn.lu.cn.lu.rpc.config
 *
 * @author lkxBruce
 * @date 2022/4/5 19:58
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class ClientConfig {
    public static Properties properties;
    static{
        try(InputStream in = ClientConfig.class.getResourceAsStream("/application.properties")){
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getZookeeperAddress(){
        String host = properties.getProperty("zookeeper.host");
        String port = properties.getProperty("zookeeper.port");
        if(host == null || port == null){
            return null;
        }
        return host + ":" + port;
    }
}
