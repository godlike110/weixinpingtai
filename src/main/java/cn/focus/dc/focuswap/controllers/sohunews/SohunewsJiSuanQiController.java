package cn.focus.dc.focuswap.controllers.sohunews;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("tools")
@AccessLogRequired
@LoginRequired
public class SohunewsJiSuanQiController {

    /**
     * 商业贷款
     * @return
     */
    @Get({"/","/sydk/"})
    public String bussinessLoan() {
        return "f:/tools/sydk/?from=sn";
    }
    
    /**
     * 公积金贷款
     */
    @Get("/gjjdk/")
    public String fundLoan() {
        return "f:/tools/gjjdk/?from=sn";
    }
    /**
     * 组合贷款
     * @return
     */
    @Get("/zhdk/")
    public String combinationLoan() {
        return "f:/tools/zhdk/?from=sn";
    }
    /**
     * 税费计算器
     * @return
     */
    @Get("/sf/")
    public String taxation() {
        return "f:/tools/sf/?from=sn";
    }
    /**
     * 提前还贷计算器
     * @return
     */
    @Get("/tqhd/")
    public String loanTerms() {
        return "f:/tools/tqhd/?from=sn";
    }
    
    /**
     * 购房能力评估
     * @return
     */
    @Get("/gfnlpg/")
    public String buyHouseAbility() {
        return "f:/tools/gfnlpg/?from=sn";
    }
    
    /**
     * 购房能力评估之推荐楼盘
     */
    @Get("/gfnlpg/tjlp/")
    public String buyHouseAbilityBuiling(Invocation inv) {
        return "f:/tools/gfnlpg/tjlp/?from=sn";
    }
    
    /**
     * 商业贷款还款详情\公积金贷款还款详情\公积金贷款还款详情
     * @return
     */
    @Get({"/sydk/hkxq/",  "/gjjdk/hkxq/", "/zhdk/hkxq/"})
    public String loanDetail(Invocation inv) {
        String path = inv.getRequestPath().getUri().replaceFirst("sohunews/", "");
       return "f:" + path + "?from=sn";
    }
    
}
