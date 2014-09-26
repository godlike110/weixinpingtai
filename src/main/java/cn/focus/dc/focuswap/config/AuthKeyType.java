package cn.focus.dc.focuswap.config;

/**
 * Auth校验类型
 * @author rogantian
 * @date 2014-4-14
 * @email rogantianwz@gmail.com
 */
public enum AuthKeyType {

    XINFANG_API_V4 {    /*新房API4.0版本使用的校验方式*/
        @Override
        public String getParamKey() {
            return "auth_token";
        }

        @Override
        public String getAddi() {
            // TODO Auto-generated method stub
            return "authKey";
        }
    },     
    PC_SIGN_AT_URL {    /*PC提供的接口，其中需要在url后边添加sign验证*/
        @Override
        public String getParamKey() {
            return "sign";
        }

        @Override
        public String getAddi() {
            // TODO Auto-generated method stub
            return "key";
        }
    }; 
    /**
     * 添加在url后边的校验值使用的参数名称
     * @return
     */
    public abstract String getParamKey();
    
    /**
     * 计算MD5值时使用到的附加参数的名称
     * @return
     */
    public abstract String getAddi();
}
