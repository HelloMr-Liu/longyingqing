package com.longyingqing.jianzhu.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * ================================================================
 * 说明：抛出服务Socket端点出口配置
 * 作用：该配置会将后端的webSocket 请求服务给抛出去 给前端浏览器连接使用
 *      如果不配置前端 那边Socket就不能创立连接 会报
 *      WebSocket connection to 'ws://localhost:8090/zhengqi/myWebSocket/test' failed:
 *      Error during WebSocket handshake: Unexpected response code: 404
 *
 * <p>
 * 作者          时间                    注释
 * 刘梓江	2020/4/21  14:58            创建
 * =================================================================
 **/
@Configuration
public class WebSocketConfiguration {

    private static Logger log = LoggerFactory.getLogger(WebSocketConfiguration.class);
    @Bean
    public ServerEndpointExporter serverEndpointExporter () {
        log.info("创建了抛出服务Socket端点出口配置。。。");
        return new ServerEndpointExporter();
    }
}
    
    
    