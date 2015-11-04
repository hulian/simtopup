package com.simtopup.user.service.impl.paypal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.simtopup.user.boundary.PaymentNotificaton;
import com.simtopup.user.service.PaymentService;
import com.simtopup.user.service.bean.PaymentAccountBean;
import com.simtopup.user.service.bean.PaymentOrderBean;
import com.simtopup.user.service.bean.PaymentSubmitBean;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;

public class PaypalServiceImpl implements PaymentService{

	public static final String NAME = "PAYPAL";

	@Override
	public PaymentSubmitBean createPaymentSign(PaymentOrderBean paymentOrderBean,
			PaymentAccountBean paymentAccountBean) {
		
		SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
		SetExpressCheckoutResponseType txnresponse = null;

		Map<String, String> customConfigurationMap = new HashMap<String, String>();
		customConfigurationMap.put("mode", "sandbox"); // Load the map with all mandatory parameters
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(customConfigurationMap);
		
		try {
			 txnresponse = service.setExpressCheckout(setExpressCheckoutReq);
		} catch (SSLConfigurationException | InvalidCredentialException | HttpErrorException
				| InvalidResponseDataException | ClientActionRequiredException | MissingCredentialException
				| OAuthException | IOException | InterruptedException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PaymentSubmitBean paymentSubmitBean = new PaymentSubmitBean();
		paymentAccountBean.setMerchantKey(txnresponse.getToken());
		
		return paymentSubmitBean;
	}

	@Override
	public boolean verifySign(Map<String, String> params, PaymentAccountBean paymentAccountBean) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String checkPaymentStatus(String paymentId) {
		return "SUCCESS";
	}

	@Override
	public boolean handlePaymentStatus(PaymentNotificaton paymentNotificaton) {
		return true;
	}

	@Override
	public Map<String, String> createSendGoodsParams(PaymentNotificaton paymentNotificaton,
			PaymentAccountBean paymentAccountBean) {
		// TODO Auto-generated method stub
		System.out.println("paypal");
		return null;
	}

}
