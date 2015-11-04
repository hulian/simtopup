package com.serverlite.provider.socketio;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.serverlite.core.controller.FrontController;
import com.serverlite.core.controller.impl.ServerLiteExchange;
import com.serverlite.core.server.ServerProvider;
import com.serverlite.core.utils.TokenUtil;

public class SocketIoProvider implements ServerProvider{

private static final Logger logger = LoggerFactory.getLogger(SocketIoProvider.class);
	
	private SocketIOServer socketIOServer;
	
	
	//配置NettySocketIo服务
	public void configServer(Properties configuration ,List<FrontController> frontControllers) {
	    
		int ioThread = Integer.parseInt(
	    		configuration.getProperty("io_thread_number",
				String.valueOf(Runtime.getRuntime().availableProcessors()+1)));
	    int workerThread = Integer.parseInt(
	    		configuration.getProperty("work_thread_number", 
				String.valueOf(Runtime.getRuntime().availableProcessors()*200)));
	    String webSocketHost=configuration.getProperty("websocket.host", "0.0.0.0");
	    int webSocketPort =  Integer.parseInt(configuration.getProperty("websocket.port", "7079"));
	    String webSocketAptConextPath = configuration.getProperty("server.namespace","/api");
        
	    Configuration config = new Configuration();
        config.setHostname(webSocketHost);
        config.setPort(webSocketPort);
        config.setBossThreads(ioThread);
        config.setWorkerThreads(workerThread);
        socketIOServer = new SocketIOServer(config);
        
        SocketIONamespace socketIONamespace = socketIOServer.addNamespace(webSocketAptConextPath);
        socketIONamespace.addEventListener("event", InputStream.class, new DataListener<InputStream>() {

			@Override
			public void onData(SocketIOClient client, InputStream inputStream,
					AckRequest ackSender) throws Exception {
				String token = client.get(TokenUtil.TOKEN_COOKIE_NMAE);
    			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    			ServerLiteExchange serverLiteExchange = new ServerLiteExchange();
    			serverLiteExchange.setInputStream(inputStream);
    			serverLiteExchange.setOutputStream(outputStream);
    			serverLiteExchange.setToken(token);
				//frontController.dispatchRequest(serverLiteExchange);
				if( serverLiteExchange.getToken()!=null ){
					client.set(TokenUtil.TOKEN_COOKIE_NMAE, serverLiteExchange.getToken());
				}
				ackSender.sendAckData( outputStream );
			}
        	
		});
        
        logger.info("Setup SocketIOServer server "+webSocketHost+":"+webSocketPort+webSocketAptConextPath);
	}


	public void start(){
		if(socketIOServer!=null){
			logger.info("Start SocketIOServer server......");
			socketIOServer.start();
		    //kill消息钩子函数，关闭系统使用的资源
	      	Runtime.getRuntime().addShutdownHook(new Thread( new Runnable() {
	      			public void run() {
	      				logger.info("Stop SocketIOServer......");
	      				socketIOServer.stop();
	      				logger.info("Stop SocketIOServer OK!");
	      			}
	      	}));
			logger.info("Start SocketIOServer OK!");
		}
	}
	
	public void stop(){
		if(socketIOServer!=null){
			logger.info("Stop SocketIOServer......");
			socketIOServer.stop();
			logger.info("Stop SocketIOServer OK!");
		}
	}


}
