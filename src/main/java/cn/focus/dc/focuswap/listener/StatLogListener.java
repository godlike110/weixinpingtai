package cn.focus.dc.focuswap.listener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import cn.focus.dc.commons.event.WapStatEvent;
import cn.focus.dc.focuswap.service.LogStatService;

/**
 * 日志事件监听
 */
@SuppressWarnings("rawtypes")
@Service
public class StatLogListener implements ApplicationListener {

    private static Logger logger = LoggerFactory.getLogger(StatLogListener.class);

    @Autowired
    private LogStatService statLogService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        try {

            String logStr = StringUtils.EMPTY;

            if (event instanceof WapStatEvent) {
                logStr = (String) event.getSource();
                if (StringUtils.isNotBlank(logStr)) {
                    statLogService.wapStatLog(logStr);
                }
            } else {
                return;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

}
