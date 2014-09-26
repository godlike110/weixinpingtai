package cn.focus.dc.focuswap.config;

public class AuthKeyPair {

    private AuthKeyType type;
    
    private String secretKey;

    public AuthKeyPair(AuthKeyType type, String secretKey) {
        super();
        this.type = type;
        this.secretKey = secretKey;
    }

    public AuthKeyType getType() {
        return type;
    }

    public String getSecretKey() {
        return secretKey;
    }
    
    
}
