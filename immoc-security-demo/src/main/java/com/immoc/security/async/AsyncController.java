package com.immoc.security.async;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 主线程 接受请求，副线程处理
     * 当副线程处理业务比较复杂，比如不同应用之间调用消息队列
     * @return
     * @throws Exception
     */
    @RequestMapping("/order")
    public Callable<String> order() throws Exception {
        logger.info("主线程开始");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始");
                Thread.sleep(1000);
                logger.info("副线程开始");
                return null;
            }
        };
        logger.info("主线程返回");
        return result;
    }

    @RequestMapping("/order2")
    public DeferredResult<String> order2() throws Exception {
        logger.info("主线程开始");
        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);
        logger.info("主线程返回");
        return result;
    }

}
