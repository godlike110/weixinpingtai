package cn.focus.dc.focuswap.config;

public class YiBaoConfig {

    private String yibaoPublicKey;

    private String merchantPrivateKey;

    private String merchantaccount;

    private String identityid;

    private Integer identitytype;

    private String productcatalog;

    private String productnamePrefix;

    private String callbackurl;

    private String mobilePayUrl;

    public String getYibaoPublicKey() {
        return yibaoPublicKey;
    }

    public void setYibaoPublicKey(String yibaoPublicKey) {
        this.yibaoPublicKey = yibaoPublicKey;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public void setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public String getMerchantaccount() {
        return merchantaccount;
    }

    public void setMerchantaccount(String merchantaccount) {
        this.merchantaccount = merchantaccount;
    }

    public String getIdentityid() {
        return identityid;
    }

    public void setIdentityid(String identityid) {
        this.identityid = identityid;
    }

    public Integer getIdentitytype() {
        return identitytype;
    }

    public void setIdentitytype(Integer identitytype) {
        this.identitytype = identitytype;
    }

    public String getProductcatalog() {
        return productcatalog;
    }

    public void setProductcatalog(String productcatalog) {
        this.productcatalog = productcatalog;
    }

    public String getProductnamePrefix() {
        return productnamePrefix;
    }

    public void setProductnamePrefix(String productnamePrefix) {
        this.productnamePrefix = productnamePrefix;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }

    public String getMobilePayUrl() {
        return mobilePayUrl;
    }

    public void setMobilePayUrl(String mobilePayUrl) {
        this.mobilePayUrl = mobilePayUrl;
    }

}
