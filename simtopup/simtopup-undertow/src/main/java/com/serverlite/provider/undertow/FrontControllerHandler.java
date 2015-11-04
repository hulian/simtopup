package com.serverlite.provider.undertow;

import java.io.ByteArrayOutputStream;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serverlite.core.controller.FrontController;
import com.serverlite.core.controller.impl.ServerLiteExchange;
import com.serverlite.core.utils.TokenUtil;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.CookieImpl;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.util.HttpString;

public class FrontControllerHandler  implements HttpHandler {

	private static final Logger logger = LoggerFactory.getLogger(FrontControllerHandler.class);
	
	private final FormParserFactory formParserFactory;

	private FrontController frontController;
	
	public FrontControllerHandler(FrontController frontController) {
		 this.frontController = frontController;
		 this.formParserFactory = FormParserFactory.builder().build();
	}

	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {

		if (exchange.isInIoThread()) {
			exchange.dispatch(this);
			return;
		}
		exchange.getResponseHeaders().add(new HttpString("Access-Control-Allow-Headers"),
				"origin, authorization, content-type, accept, referer, user-agent");
		exchange.getResponseHeaders().add(new HttpString("Access-Control-Allow-Method"), "GET, POST, OPTIONS");
		exchange.getResponseHeaders().add(new HttpString("Access-Control-Allow-Origin"), "*");

		exchange.startBlocking();
		try {
			// 获取token
			String token = null;
			if (exchange.getRequestCookies().get(TokenUtil.TOKEN_COOKIE_NMAE) != null) {
				token = exchange.getRequestCookies().get(TokenUtil.TOKEN_COOKIE_NMAE).getValue();
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ServerLiteExchange serverLiteExchange = new ServerLiteExchange();
			
			logger.debug("pasre:"+frontController.isPasreFormr());
			if(exchange.getRequestMethod().equalToString("POST") && frontController.isPasreFormr()==true ){
				if(exchange.isBlocking()){
					FormDataParser parser = formParserFactory.createParser(exchange);
					FormData formData = parser.parseBlocking();
					Iterator<String> iterator = formData.iterator(); 
					while(iterator.hasNext()){
						String key = iterator.next();
						serverLiteExchange.getQueryParams().put(key,formData.get(key).getLast().getValue());
					}
				}
			}else{
				serverLiteExchange.setInputStream(exchange.getInputStream());
				Iterator<Entry<String, Deque<String>>>  iterator = exchange.getQueryParameters().entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String, Deque<String>> entry = iterator.next();
					serverLiteExchange.getQueryParams().put(entry.getKey(),entry.getValue().getLast());
				}
			}
			serverLiteExchange.setOutputStream(outputStream);
			serverLiteExchange.setToken(token);
			serverLiteExchange.setUrl(exchange.getRelativePath());
			
			frontController.dispatchRequest(serverLiteExchange);

			if (serverLiteExchange.getToken() != null) {
				exchange.getResponseCookies().put(TokenUtil.TOKEN_COOKIE_NMAE,
						new CookieImpl(TokenUtil.TOKEN_COOKIE_NMAE, serverLiteExchange.getToken()));
			}
			exchange.getOutputStream().write(outputStream.toByteArray());
		} catch (Exception e) {
			logger.error("SERVER ERROR", e);
		}
		exchange.endExchange();

	}

}
