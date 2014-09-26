package cn.focus.dc.focuswap.servlet;

import cn.focus.dc.focuswap.model.LogMessageVO;
import cn.focus.dc.focuswap.model.LogMessageVO.LOG_MSG_TYPE;

import java.util.concurrent.BlockingQueue;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import static cn.focus.dc.focuswap.config.AppConstants.LOG_THREAD_COUNT;

/**
 * @author qiaowang
 *
 */
public class LogStatServlet extends HttpServlet {

    private static Log logger = LogFactory.getLog(LogStatServlet.class);
    
    private static Log statlog = LogFactory.getLog("statlog");

    private static Log statAccesslog = LogFactory.getLog("statAccesslog");
    
    private static final long serialVersionUID = 7150144203497199604L;

    @SuppressWarnings("unchecked")
    public void init() throws ServletException {
        ServletContext sc = this.getServletContext();

        WebApplicationContext web = WebApplicationContextUtils.getWebApplicationContext(sc);
        BlockingQueue<LogMessageVO> msgQueue = (BlockingQueue<LogMessageVO>) web.getBean("msgQueue");
        
        for (int i = 0; i < LOG_THREAD_COUNT; i++) {
            CollectTask task = new CollectTask(msgQueue);
            Thread thread = new Thread(task);
            thread.start();
        }
        
    }
    
    static class CollectTask implements Runnable {

        private BlockingQueue<LogMessageVO> taskQueue = null;

        public CollectTask(BlockingQueue<LogMessageVO> taskQueue) {
            this.taskQueue = taskQueue;
        }

        @Override
        public void run() {

            while (true) {
                LogMessageVO logMessage = null;
                try {
                    logMessage = taskQueue.take();
                    if (LOG_MSG_TYPE.COMMONLOG == logMessage.getMsgType()) {
                        statlog.info(logMessage.getMessage());
                    } else if (LOG_MSG_TYPE.ACCESSLOG == logMessage.getMsgType()) {
                        statAccesslog.info(logMessage.getMessage());
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                }
                
            }
        }
    }
}
