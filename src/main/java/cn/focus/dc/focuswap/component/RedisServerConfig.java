package cn.focus.dc.focuswap.component;

public class RedisServerConfig {

    private String ip;
    
    private Integer port;
    
    //1表示master， 0表示slave
    private Integer master;
    
    //1表示持久化，0表示不持久化
    private Integer persistence;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getMaster() {
        return master;
    }

    public void setMaster(Integer master) {
        this.master = master;
    }

    public Integer getPersistence() {
        return persistence;
    }

    public void setPersistence(Integer persistence) {
        this.persistence = persistence;
    }
    
    
}
