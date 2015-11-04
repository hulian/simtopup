package com.serverlite.provider.undertow;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serverlite.core.controller.FrontController;
import com.serverlite.core.server.ServerProvider;

import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.encoding.ContentEncodedResourceManager;
import io.undertow.server.handlers.encoding.ContentEncodingRepository;
import io.undertow.server.handlers.encoding.GzipEncodingProvider;
import io.undertow.server.handlers.resource.CachingResourceManager;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;

public class UndertowProvider implements ServerProvider{
	
	private static final Logger logger = LoggerFactory.getLogger(UndertowProvider.class);
	
	private Undertow undertow;
	
	//配置Undertow服务
	public void configServer(Properties configuration , List<FrontController> frontControllers ) {
	    int ioThread = Integer.parseInt(
	    		configuration.getProperty("io_thread_number",
				String.valueOf(Runtime.getRuntime().availableProcessors()+1)));
	    int workerThread = Integer.parseInt(
	    		configuration.getProperty("work_thread_number", 
				String.valueOf(Runtime.getRuntime().availableProcessors()*200)));
		String httpHost = configuration.getProperty("http.host", "0.0.0.0");
        int httpPort=Integer.parseInt( configuration.getProperty("http.port", "7080"));
        String staticResourcePath = configuration.getProperty("server.context.path.static","static");
        
        PathHandler pathHandler = new PathHandler();
        
        //静态资源，压缩
        ResourceManager resourceManager = new FileResourceManager(new File(staticResourcePath), 10240);
        ContentEncodedResourceManager encoder =  new ContentEncodedResourceManager(Paths.get(staticResourcePath), new CachingResourceManager(100, 10000, null, resourceManager, -1), new ContentEncodingRepository()
                .addEncodingHandler("gzip", new GzipEncodingProvider(), 50, null),1024, 1024*500, null);
        ResourceHandler resourceHandler = new ResourceHandler(resourceManager);
        
        if(configuration.getProperty("http.gzip", "false").equals("true")){
        	resourceHandler.setContentEncodedResourceManager(encoder);
        }
        pathHandler.addPrefixPath("/", resourceHandler);
        
		//API
		for( FrontController frontController : frontControllers ){
        	logger.info("Setup Undertow server path:"+frontController.getPath());
        	pathHandler.addPrefixPath(frontController.getPath(), new FrontControllerHandler(frontController));
        }
        
		undertow = Undertow.builder()
                .addHttpListener(httpPort,httpHost)
                .setIoThreads(ioThread)
                .setWorkerThreads(workerThread)
                .setHandler(pathHandler).build();
        logger.info("Setup Undertow server static resources at path:"+staticResourcePath+" at location:"+staticResourcePath);
	}


	public void start(){
		if(undertow!=null){
			logger.info("Start Undertow server......");
			undertow.start();
			logger.info("Start Undertow OK!");
			 //kill消息钩子函数，关闭系统使用的资源
	      	Runtime.getRuntime().addShutdownHook(new Thread( new Runnable() {
	      			public void run() {
	      				logger.info("Stop Undertow......");
	      				undertow.stop();
	      				logger.info("Stop Undertow OK!");
	      			}
	      	}));
		}
	}
	
	public void stop(){
		if(undertow!=null){
			logger.info("Stop Undertow......");
			undertow.stop();
			logger.info("Stop Undertow OK!");
		}
	}
	

}
