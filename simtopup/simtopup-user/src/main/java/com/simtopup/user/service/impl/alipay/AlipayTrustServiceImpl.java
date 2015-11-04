package com.simtopup.user.service.impl.alipay;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.simtopup.user.boundary.PaymentNotificaton;
import com.simtopup.user.service.PaymentService;
import com.simtopup.user.service.bean.PaymentAccountBean;
import com.simtopup.user.service.bean.PaymentOrderBean;
import com.simtopup.user.service.bean.PaymentSubmitBean;

public class AlipayTrustServiceImpl implements PaymentService{
	
	private static final Logger logger = LoggerFactory.getLogger(AlipayTrustServiceImpl.class);

	public AlipayTrustServiceImpl() {
		
	}
	
	@Override
	public PaymentSubmitBean createPaymentSign( PaymentOrderBean paymentOrderBean , PaymentAccountBean paymentAccountBean ) {
		
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_partner_trade_by_buyer");
        sParaTemp.put("partner", paymentAccountBean.getMerchantId());
        sParaTemp.put("seller_email", paymentAccountBean.getMerchantUserName());
        sParaTemp.put("_input_charset", "utf-8");
		sParaTemp.put("payment_type", "1");
		sParaTemp.put("notify_url", "http://www.hwsjcz.com/payment/alipay");
		sParaTemp.put("return_url", "http://www.hwsjcz.com/view/alipayReturn.html");
		sParaTemp.put("out_trade_no",paymentOrderBean.getPaymentOrderId());
		sParaTemp.put("subject", "海外手机充值订单 您的手机号是:"+paymentOrderBean.getInfo());
		sParaTemp.put("price", String.valueOf(paymentOrderBean.getPaymentAmount().intValue()));
		sParaTemp.put("quantity", "1");
		sParaTemp.put("logistics_fee", "0");
		sParaTemp.put("logistics_type", "EXPRESS");
		sParaTemp.put("logistics_payment", "SELLER_PAY");
		//sParaTemp.put("body", "");
		//sParaTemp.put("show_url", "");
		sParaTemp.put("receive_name",  paymentOrderBean.getInfo());
		sParaTemp.put("receive_address",  paymentOrderBean.getInfo());
		sParaTemp.put("receive_zip", "000000");
		//sParaTemp.put("receive_phone", paymentOrderBean.getInfo());
		sParaTemp.put("receive_mobile", paymentOrderBean.getInfo());
		
		//Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		String link = AlipayCore.createLinkString(sParaTemp);
		logger.debug("alipay sign string:"+link);
		String sign = MD5.sign(link, paymentAccountBean.getMerchantKey() , "utf-8");
		
		PaymentSubmitBean paymentSubmitBean = new PaymentSubmitBean();
		paymentSubmitBean.setApiAddress(paymentAccountBean.getApiAddress());
		paymentSubmitBean.setSign(sign);
		paymentSubmitBean.setMerchant(paymentAccountBean.getMerchantId());
		paymentSubmitBean.setOrderId(paymentOrderBean.getPaymentOrderId());
		
		return paymentSubmitBean;
	}

	@Override
	public String checkPaymentStatus(String paymentId) {
		return "SUCCESS";
	}

	@Override
	public boolean handlePaymentStatus(PaymentNotificaton paymentNotificaton) {
		
		String status = paymentNotificaton.getPaymentStatus();
		if(status.equals("WAIT_BUYER_PAY")){
			//该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
			
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
		    			//请务必判断请求时的price、quantity、seller_id与通知时获取的price、quantity、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
				
				//out.println("success");	//请不要修改或删除
				paymentNotificaton.setResponseStatus("success");
				return false;
			} else if(status.equals("WAIT_SELLER_SEND_GOODS")){
			//该判断表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货
			
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
		    			//请务必判断请求时的price、quantity、seller_id与通知时获取的price、quantity、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
				
				//out.println("success");	//请不要修改或删除
				paymentNotificaton.setResponseStatus("success");
				return true;
			} else if(status.equals("WAIT_BUYER_CONFIRM_GOODS")){
			//该判断表示卖家已经发了货，但买家还没有做确认收货的操作
			
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
		    			//请务必判断请求时的price、quantity、seller_id与通知时获取的price、quantity、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
				
				//out.println("success");	//请不要修改或删除
				paymentNotificaton.setResponseStatus("success");
				return false;
			} else if(status.equals("TRADE_FINISHED")){
			//该判断表示买家已经确认收货，这笔交易完成
			
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
		    			//请务必判断请求时的price、quantity、seller_id与通知时获取的price、quantity、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
				
				//out.println("success");	//请不要修改或删除
				paymentNotificaton.setResponseStatus("success");
				return false;
			}
			else {
				
				paymentNotificaton.setResponseStatus("success");
				return false;
			}
		
	}

	@Override
	public boolean verifySign(Map<String, String> params,  PaymentAccountBean paymentAccountBean) {
		return AlipayCore.getSignVeryfy(params, params.get("sign"), paymentAccountBean.getMerchantKey());
	}

	@Override
	public Map<String, String> createSendGoodsParams(PaymentNotificaton paymentNotificaton  ,PaymentAccountBean paymentAccountBean) {

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "send_goods_confirm_by_platform");
        sParaTemp.put("partner",paymentAccountBean.getMerchantId());
        sParaTemp.put("_input_charset", "utf-8");
		sParaTemp.put("trade_no", paymentNotificaton.getPaymentTransactionId());
		sParaTemp.put("logistics_name", "MoblieTopUp");
		sParaTemp.put("invoice_no", "000000000");
		sParaTemp.put("transport_type", "EXPRESS");
		//Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		String link = AlipayCore.createLinkString(sParaTemp);
		logger.debug("send goods link:"+link);
		String sign = MD5.sign(link, paymentAccountBean.getMerchantKey(),"utf-8");
		sParaTemp.put("sign_type","MD5");
		sParaTemp.put("sign",sign);
		
		return sParaTemp;
	}

}
