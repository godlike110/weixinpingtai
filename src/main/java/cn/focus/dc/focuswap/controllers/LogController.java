package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Path("log")
@AccessLogRequired
public class LogController {
    
    private static Log log = LogFactory.getLog(LogController.class);
    /**
     * 400电话
     * @param inv
     * @param cityName
     * @param groupId
     * @param phone400
     * @return
     */
    
    @Get("collect")
    @Post("collect")
    public String setLog(Invocation inv, @Param("cityName") String cityName, @Param("groupId") int groupId,
            @Param("phone400") int phone400) {
        log.info(cityName + " 楼盘【" + groupId + "】400called!");
        return "@success";
    }
    
    /**
     * 广告条点击统计
     * @param inv
     * @param cityName
     * @return
     */
    @Get("download")
    @Post("download")
    public String downloadLog(Invocation inv,@Param("cityName") String cityName) {
        log.info(cityName + " 下载广告条被点击！");
        return "@success";
    }

}
