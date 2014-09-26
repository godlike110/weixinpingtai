package cn.focus.dc.focuswap.config;

public class SecretkeyPair {
    private boolean isMd5;
    
    private String secretKey;

    public SecretkeyPair(boolean isMd5, String secretKey) {
        super();
        this.isMd5 = isMd5;
        this.secretKey = secretKey;
    }  

    public boolean isMd5() {
        return isMd5;
    }

    public String getSecretKey() {
        return secretKey;
    }
    
    
}
