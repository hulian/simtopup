package com.simtopup.user.controller.topup;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.serverlite.core.constant.Code;
import com.simtopup.common.util.HttpClientUtil;
import com.simtopup.user.Configuration;
import com.simtopup.user.boundary.Request;
import com.simtopup.user.boundary.Response;
import com.simtopup.user.entity.ApiConfig;
import com.simtopup.user.entity.TopUpOrder;
import com.simtopup.user.service.ApiConfigService;
import com.simtopup.user.service.PaymentService;
import com.simtopup.user.service.TopUpOrderService;
import com.simtopup.user.service.TopUpService;
import com.simtopup.user.service.bean.PaymentAccountBean;
import com.simtopup.user.service.bean.PaymentOrderBean;
import com.simtopup.user.service.bean.PaymentSubmitBean;
import com.simtopup.user.service.impl.paypal.PaypalServiceImpl;

public class TopUpController {
	
	private static final Logger logger = LoggerFactory.getLogger(TopUpController.class);
	
	private TopUpOrderService topUpOrderService;
	
	private TopUpService thaiTopUpService;
	
	private PaymentService paypalService;
	
	private PaymentService alipayTrustService;
	
	private ApiConfigService apiConfigService;
	
	/**
	 * http://www.hwsjcz.com/payment/alipay?buyer_email=hu.lian.cn%40gmail.com&buyer_id=2088122294944971&discount=0.00&gmt_create=2015-11-12+10%3A01%3A22&gmt_logistics_modify=2015-11-12+10%3A02%3A49&gmt_payment=2015-11-12+10%3A03%3A03&is_success=T&is_total_fee_adjust=N&logistics_fee=0.00&logistics_payment=SELLER_PAY&logistics_type=EXPRESS&notify_id=RqPnCoPT3K9%252Fvwbh3InVadX5j8fKybyU6azXdChY2YVtf58hbtidlNK3AWhsbMiFWLT9&notify_time=2015-11-12+10%3A03%3A07&notify_type=trade_status_sync&out_trade_no=2f969049-bf5b-4e34-a393-7183d978abee&payment_type=1&price=2.00&quantity=1&receive_address=%E5%9B%9B%E5%B7%9D%E7%9C%81+%E5%AE%9C%E5%AE%BE%E5%B8%82+%E9%95%BF%E5%AE%81%E5%8E%BF+%E8%BF%87%E5%B2%81%E5%B0%8F%E5%8C%BA&receive_name=%E8%83%A1%E7%82%BC&receive_phone=18508313751&receive_zip=644300&seller_actions=SEND_GOODS&seller_email=lian.hu.chinese%40gmail.com&seller_id=2088312171023422&subject=%E6%B5%B7%E5%A4%96%E6%89%8B%E6%9C%BA%E5%85%85%E5%80%BC%E8%AE%A2%E5%8D%95+%E6%82%A8%E7%9A%84%E6%89%8B%E6%9C%BA%E5%8F%B7%E6%98%AF%3A0800000000&total_fee=2.00&trade_no=2015111200001000970068011944&trade_status=WAIT_SELLER_SEND_GOODS&use_coupon=N&sign=cc5009d57b80b059601fe950a62322b8&sign_type=MD5
	 * @param topUpOrderService
	 * @param paypalService
	 * @param alipayTrustService
	 * @param thaiTopUpService
	 */
	
	public TopUpController( ApiConfigService apiConfigService,
							TopUpOrderService topUpOrderService,
							PaymentService paypalService,
							PaymentService alipayTrustService,
							TopUpService thaiTopUpService) {
		this.apiConfigService = apiConfigService;
		this.topUpOrderService=topUpOrderService;
		this.thaiTopUpService=thaiTopUpService;
		this.paypalService=paypalService;
		this.alipayTrustService=alipayTrustService;
	}
	
	public void createTopUp( Request request , Response response){
		
		try {
			
			ApiConfig apiConfig = apiConfigService.findApiConfigById(Configuration.API_CONFIG_ALIPAY_TRUST);
			
			//创建订单
			TopUpOrder topUpOrder = request.getTopUpOrder();
			topUpOrderService.createTopUpOrder(topUpOrder);
			
			//获取充值事物id
			TopUpService topUpService = findTopUpService(request.getTopUpOrder().getNetworkOperator());
			topUpService.createToUpId(request.getTopUpOrder());
			
			//获取支付相关信息
			PaymentService paymentService = findPaymentService(request.getTopUpOrder().getPayMethod());
			PaymentAccountBean paymentAccountBean = new PaymentAccountBean();
			paymentAccountBean.setApiAddress(apiConfig.getApiAddress());
			paymentAccountBean.setMerchantId(apiConfig.getMerchant());
			paymentAccountBean.setMerchantUserName(apiConfig.getName());
			paymentAccountBean.setMerchantKey(apiConfig.getKey1());
			
			PaymentOrderBean paymentOrderBean = new PaymentOrderBean();
			paymentOrderBean.setPaymentOrderId(topUpOrder.getPaymentOrderId());
			paymentOrderBean.setPaymentAmount(topUpOrder.getPaymentAmount());
			paymentOrderBean.setInfo(topUpOrder.getMobileNumber());
			
			PaymentSubmitBean paymentSubmitBean  = paymentService.createPaymentSign( paymentOrderBean,paymentAccountBean );
			response.setPaymentSubmitBean(paymentSubmitBean);
			
		} catch (Exception e) {
			logger.error("request:"+request);
			logger.error("topup error",e);
			response.setCode(Code.ERROR);
			response.setMessage("topup error:"+e.getMessage());
		}
		
	}
	

	public void approveTopUp( Request request , Response response ){
		
		
		try {
			//获取配置
			ApiConfig apiConfig = apiConfigService.findApiConfigById(Configuration.API_CONFIG_ALIPAY_TRUST);
			PaymentAccountBean paymentAccountBean = new PaymentAccountBean();
			paymentAccountBean.setApiAddress(apiConfig.getApiAddress());
			paymentAccountBean.setMerchantId(apiConfig.getMerchant());
			paymentAccountBean.setMerchantUserName(apiConfig.getName());
			paymentAccountBean.setMerchantKey(apiConfig.getKey1());
			
			//检查支付是否成功
			PaymentService paymentService = findPaymentService(request.getPaymentNotificaton().getPayMethod());
			
			//验证签名
			if(!paymentService.verifySign(request.getQueryParams(), paymentAccountBean)){
				logger.error("sign error:"+request.getQueryParams());
				response.setMessage("ERROR");
				return;
			}
			
			//查询订单
			TopUpOrder topUpOrder = topUpOrderService.findTopUpOrderByPaymentOrderId(request.getPaymentNotificaton().getPaymentOrderId(),false);
			if( topUpOrder==null ){
				logger.info("can not find topUpOrder:"+request.getPaymentNotificaton().getPaymentOrderId());
				response.setCode(Code.ERROR);
				response.setMessage("can not find topUpOrder:"+request.getPaymentNotificaton().getPaymentOrderId());
				return;
			}
			
			//检查订单状态
			if( topUpOrder.getStatus()==TopUpOrder.PAID || topUpOrder.getStatus()==TopUpOrder.TOPUPED){
				logger.info("topUpOrder is paid or topuped status:"+topUpOrder.getStatus());
				response.setMessage("SUCCESS");
				return;
			}
			
			
			//如果支付最终成功
			if(paymentService.handlePaymentStatus(request.getPaymentNotificaton())){
				
				//String status = paymentService.checkPaymentStatus(topUpOrder.getPaymentTransactionId());
				//if( status.equals("SUCCESS")){
					
					TopUpService topUpService = findTopUpService(topUpOrder.getNetworkOperator());
					
					topUpOrderService.updateTopUpOrderStatus(topUpOrder.getId() ,TopUpOrder.PAID);
					
					topUpService.topUp(topUpOrder);
					
					//发货
					if( request.getPaymentNotificaton().getPayMethod().equals(Configuration.NAME)){
						Map<String, String> params = paymentService.createSendGoodsParams(request.getPaymentNotificaton(), paymentAccountBean);
						String r = HttpClientUtil.httpPostFormForString(paymentAccountBean.getApiAddress(), params,  null, null);
						logger.info(" send goos result:"+r);
					}
					
				//}
			}
			
			response.setMessage(request.getPaymentNotificaton().getResponseStatus());
			
		} catch (Exception e) {
			logger.error("topup error",e);
			response.setCode(Code.ERROR);
			response.setMessage("pay error:"+e.getMessage());
		}
	}
	
	
	private TopUpService findTopUpService(String networkOperator) {
		
		TopUpService topUpService = null;
		switch (networkOperator) {
		case TopUpOrder.NETWORK_OPERATOR_THAI_AIS:
			topUpService=thaiTopUpService;
			break;
		case TopUpOrder.NETWORK_OPERATOR_THAI_DTAC:
			topUpService=thaiTopUpService;
			break;
		case TopUpOrder.NETWORK_OPERATOR_THAI_TRUE:
			topUpService=thaiTopUpService;
			break;
		}
		if(topUpService==null){
			throw new RuntimeException(" cannot find payment service for:"+networkOperator);
		}
		return topUpService;
	}

	private PaymentService findPaymentService(String payMethod) {
		PaymentService paymentService=null;
				
				switch (payMethod) {
					case PaypalServiceImpl.NAME:
						paymentService=paypalService;
						break;
					case Configuration.NAME:
						paymentService=alipayTrustService;
						break;
				}
		if(paymentService==null){
			throw new RuntimeException(" cannot find payment service for:"+payMethod);
		}
		return paymentService;
	}

	
}
