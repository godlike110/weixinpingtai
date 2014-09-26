package cn.focus.dc.focuswap.controllers;

import net.paoding.rose.web.ControllerErrorHandler;
import net.paoding.rose.web.Invocation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorHandler implements ControllerErrorHandler {

    private static Log logger = LogFactory.getLog(ErrorHandler.class);

    public Object onError(Invocation inv, Throwable ex)  {
        logger.error("", ex);
        return "e:500";
    }

}
