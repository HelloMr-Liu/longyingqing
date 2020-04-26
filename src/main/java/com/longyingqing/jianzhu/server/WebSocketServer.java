package com.longyingqing.jianzhu.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ================================================================
 * 说明：管理一个WebSocket通讯服务类 也就是一个与后端连接的请求入口
 * <p>
 * 作者          时间                     注释
 * 刘梓江	2020/4/21  14:32            创建
 * =================================================================
 **/
@ServerEndpoint(value = "/myWebSocket")
@Component
public class WebSocketServer {

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    //高并发的情况下 多个线程操作同一个i数据，i++无法保证原子性 也无法保证数据一致性，往往会出现问题，所以引入AtomicInteger类。
    //自增：OnlineCount.incrementAndGet();
    //获取：OnlineCount.get();
    //自减：OnlineCount.decrementAndGet();
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);

    //concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    //它是线程安全的无序的集合，可以将它理解成线程安全的HashSet
    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();

    @PostConstruct
    public void init() {
        log.info("当前web通讯服务已经加载");
    }

    //功能：发送消息，实践表明，每session建立依次就 执行一次 并将当前获取的消息返回给前端对应执行一次socket.onmessage 方法
    public static void SendMessage(Session session, String message) {
        try {
            //getAsyncRemote()和getBasicRemote()确实是异步与同步的区别，大部分情况下，推荐使用getAsyncRemote()。
            //由于getBasicRemote()的同步特性，并且它支持部分消息的发送即sendText(xxx,boolean isLast). isLast的值表示是否一次发送消息中的部分消息，
            session.getBasicRemote().sendText(String.format("名字为：%s (你好我是后端我们已经建立并将你发的消息返回给你，消息=%s)",session.getId(),message));
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    //功能：连接建立成功调用该方法
    @OnOpen
    public void onOpen(Session session) {
        SessionSet.add(session);
        int cnt = OnlineCount.incrementAndGet(); // 在线数加1
        log.info("有连接加入，当前连接数为：{}", cnt);
        //SendMessage(session, "连接成功");
    }

    //收到客户端对应send()方法后 调用该onMessage的方法
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：{}",message);
        SendMessage(session, message);
    }

    //连接关闭调用的方法
    @OnClose
    public void onClose(Session session) {
        SessionSet.remove(session);
        int cnt = OnlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    //出现错误
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
        error.printStackTrace();
    }

    //群发消息
    public static void BroadCastInfo(String message) throws IOException {
        for (Session session : SessionSet) {
            if(session.isOpen()){
                SendMessage(session, message);
            }
        }
    }

    //指定Session发送消息
    public static void SendMessage(String message,String sessionId) throws IOException {
        Session session = null;
        for (Session s : SessionSet) {
            if(s.getId().equals(sessionId)){
                session = s;
                break;
            }
        }
        if(session!=null){
            SendMessage(session, message);
        }
        else{
            log.warn("没有找到你指定ID的会话：{}",sessionId);
        }
    }


}
    
    
    