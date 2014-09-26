package cn.focus.dc.focuswap.controllers.sohunews;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("/")
@AccessLogRequired
public class SohunewsPaFangTuanController {

    
    @Get("kanfangtuan")
    public String pftList(Invocation inv, @Param("cityName") @DefValue("bj")String cityName) {
        
        return "f:/" + cityName + "/pafangtuan/?from=sn"; 
    }
}
