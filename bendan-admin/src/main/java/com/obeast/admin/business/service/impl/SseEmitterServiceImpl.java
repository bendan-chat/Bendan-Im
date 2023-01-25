package com.obeast.admin.business.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpStatus;
import com.obeast.admin.business.service.SseEmitterService;
import com.obeast.common.three.domain.FlyweightRes;
import com.obeast.common.three.domain.MessageVo;
import com.obeast.core.exception.BendanException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author wxl
 * Date 2023/1/4 13:36
 * @version 1.0
 * Description: SseEmitter发送信息的服务类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SseEmitterServiceImpl extends SseEmitter implements SseEmitterService {

    private final FlyweightRes res;
    /**
     * 容器，保存连接，用于输出返回 ;可使用其他方法实现
     */
    private static final Map<Long, SseEmitter> sseCache = new ConcurrentHashMap<>();



    /**
     * 根据客户端id获取SseEmitter对象
     *
     * @param clientId 客户端ID
     */
    @Override
    public SseEmitter getSseEmitterByClientId(Long clientId) {
        return sseCache.get(clientId);
    }




    /**
     * 创建连接
     *
     * @param clientId 客户端ID
     */
    @Override
    public SseEmitter createConnect(Long clientId) {
        if (clientId == null) {
            throw new BendanException("用户Id为不能建立连接");
        }
        // 设置超时时间，0表示不过期。默认30秒，超过时间未完成会抛出异常：AsyncRequestTimeoutException
        SseEmitter sseEmitter = new SseEmitter(100000L);
        // 是否需要给客户端推送ID
//        if (StrUtil.isBlank(clientId)) {
//            clientId = IdUtil.simpleUUID();
//            res.put(clientId, Map.of("", clientId));
//        }
//        res.put("clientId", Map.of("clientId", clientId));
        log.info("当前clientId----->{}", clientId);

        // 注册回调
        // 长链接完成后回调接口(即关闭连接时调用)
        sseEmitter.onCompletion(completionCallBack(clientId));
        // 连接超时回调
        sseEmitter.onTimeout(timeoutCallBack(clientId));
        // 推送消息异常时，回调方法
        sseEmitter.onError(errorCallBack(clientId));
        sseCache.put(clientId, sseEmitter);
        log.info("创建新的sse连接，当前用户：{}    累计用户:{}", clientId, sseCache.size());
        try {
//             注册成功返回用户信息
            sseEmitter.send(SseEmitter.event()
                    .id(String.valueOf(clientId))
                    .data("HttpStatus----->" + HttpStatus.HTTP_CREATED)
            );
        } catch (IOException e) {
            log.error("创建长链接异常，客户端ID:{}   异常信息:{}", clientId, e.getMessage());
        }
        return sseEmitter;
    }


    /**
     * 发送消息给所有客户端
     *
     * @param msg 消息内容
     */
    @Override
    public void sendMessageToAllClient(String msg) {
        if (MapUtil.isEmpty(sseCache)) {
            return;
        }
        // 判断发送的消息是否为空
        for (Map.Entry<Long, SseEmitter> entry : sseCache.entrySet()) {
            MessageVo messageVo = new MessageVo();
            messageVo.setClientId(entry.getKey());
            messageVo.setData(msg);
            sendMsgToClientByClientId(entry.getKey(), messageVo, entry.getValue());
        }

    }

    /**
     * 给指定客户端发送消息
     *
     * @param clientId 客户端ID
     * @param msg      消息内容
     */
    @Override
    public void sendMessageToOneClient(Long clientId, String msg) {
        MessageVo messageVo = new MessageVo(clientId, msg);
        sendMsgToClientByClientId(clientId, messageVo, sseCache.get(clientId));
    }

    /**
     * 给指定客户端发送消息
     *
     * @param clientId 客户端ID
     * @param flyweightRes      消息内容
     */
    @Override
    public void sendResMapToOneClient(Long clientId, FlyweightRes flyweightRes) {
        sendResMapToClientByClientId(clientId, flyweightRes, sseCache.get(clientId));
    }


    /**
     * @description: 服务器给客户端发送消息
     * @author wxl
     * @date 2022/7/21 16:00
     * @param clientId 客户端ID
     **/
    @Override
    public void sendMessage(Long clientId)  {
        sendResMapToOneClient(clientId, res);
    }

    /**
     * 推送消息到客户端
     * 此处做了推送失败后，重试推送机制，可根据自己业务进行修改
     *
     * @param clientId  客户端ID
     * @param flyweightRes 推送信息，此处结合具体业务，定义自己的返回值即可
     **/
    private void sendResMapToClientByClientId(Long clientId, FlyweightRes flyweightRes, SseEmitter sseEmitter) {
        if (sseEmitter == null) {
            log.error("推送消息失败：客户端{}未创建长链接,失败消息:{}",
                    clientId, flyweightRes.toString());
            return;
        }
        SseEventBuilder sendData = SseEmitter.event().data("HttpStatus:" + HttpStatus.HTTP_OK, MediaType.APPLICATION_JSON)
                .data(flyweightRes, MediaType.APPLICATION_JSON);
        try {
            sseEmitter.send(sendData);
        } catch (IOException e) {
            // 推送消息失败，记录错误日志，进行重推
            log.error("推送消息失败：{},尝试进行重推", flyweightRes.toString());
            boolean isSuccess = true;
            // 推送消息失败后，每隔10s推送一次，推送5次
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(10000);
                    sseEmitter = sseCache.get(clientId);
                    if (sseEmitter == null) {
                        log.error("{}的第{}次消息重推失败，未创建长链接", clientId, i + 1);
                        continue;
                    }
                    sseEmitter.send(sendData);
                } catch (Exception ex) {
                    log.error("{}的第{}次消息重推失败", clientId, i + 1, ex);
                    continue;
                }
                log.info("{}的第{}次消息重推成功,{}", clientId, i + 1, flyweightRes.toString());
                return;
            }
        }
    }


    /**
     * 推送消息到客户端
     * 此处做了推送失败后，重试推送机制，可根据自己业务进行修改
     *
     * @param clientId  客户端ID
     * @param messageVo 推送信息，此处结合具体业务，定义自己的返回值即可
     **/
    private void sendMsgToClientByClientId(Long clientId, MessageVo messageVo, SseEmitter sseEmitter) {
        if (sseEmitter == null) {
            log.error("推送消息失败：客户端{}未创建长链接,失败消息:{}",
                    clientId, messageVo.toString());
            return;
        }
        SseEventBuilder sendData = SseEmitter.event().data("HttpStatus:" + HttpStatus.HTTP_OK, MediaType.APPLICATION_JSON)
                .data(messageVo, MediaType.APPLICATION_JSON);
        try {
            sseEmitter.send(sendData);
        } catch (IOException e) {
            // 推送消息失败，记录错误日志，进行重推
            log.error("推送消息失败：{},尝试进行重推", messageVo.toString());
            boolean isSuccess = true;
            // 推送消息失败后，每隔10s推送一次，推送5次
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(10000);
                    sseEmitter = sseCache.get(clientId);
                    if (sseEmitter == null) {
                        log.error("{}的第{}次消息重推失败，未创建长链接", clientId, i + 1);
                        continue;
                    }
                    sseEmitter.send(sendData);
                } catch (Exception ex) {
                    log.error("{}的第{}次消息重推失败", clientId, i + 1, ex);
                    continue;
                }
                log.info("{}的第{}次消息重推成功,{}", clientId, i + 1, messageVo.toString());
                return;
            }
        }
    }


    /**
     * 关闭连接
     *
     * @param clientId 客户端ID
     */
    @Override
    public void closeConnect(Long clientId) {
        SseEmitter sseEmitter = sseCache.get(clientId);
        if (sseEmitter != null) {
            sseEmitter.complete();
            removeUser(clientId);
        }
    }



    /**
     * 长链接完成后回调接口(即关闭连接时调用)
     *
     * @param clientId 客户端ID
     **/
    private Runnable completionCallBack(Long clientId) {
        return () -> {
            log.info("结束连接：{}", clientId);
            removeUser(clientId);
        };
    }

    /**
     * 连接超时时调用
     *
     * @param clientId 客户端ID
     **/
    private Runnable timeoutCallBack(Long clientId) {
        return () -> {
            log.info("连接超时：{}", clientId);
            removeUser(clientId);
        };
    }

    /**
     * 推送消息异常时，回调方法
     *
     * @param clientId 客户端ID
     **/
    private Consumer<Throwable> errorCallBack(Long clientId) {
        return throwable -> {
            log.error("SseEmitterServiceImpl[errorCallBack]：连接异常,客户端ID:{}", clientId);

            // 推送消息失败后，每隔10s推送一次，推送5次
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(10000);
                    SseEmitter sseEmitter = sseCache.get(clientId);
                    if (sseEmitter == null) {
                        log.error("SseEmitterServiceImpl[errorCallBack]：第{}次消息重推失败,未获取到 {} 对应的长链接", i + 1, clientId);
                        continue;
                    }
                    sseEmitter.send("失败后重新推送");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 移除用户连接
     *
     * @param clientId 客户端ID
     **/
    private void removeUser(Long clientId) {
        sseCache.remove(clientId);
        log.info("SseEmitterServiceImpl[removeUser]:移除用户：{}", clientId);
    }
}
