
var payAmountMap = new Object();
var topUpOrderRequest={eventId:10,topUpOrder:{countryCode:66}};
var alipayFormData=new Object();;
var response;

$(document).ready(function() {
	
	initPayAmountMap();

	$("#thaiTopupMoblieNumber").inputmask("(qp-9999-9999)|(a{+})", {
		definitions : {
			"q" : {
				validator : "[0]",
				cardinality : 1
			},
			"p" : {
				validator : "[6,8,9]",
				cardinality : 1
			}
		},
		autoUnmask : true,
		onUnMask : function(maskedValue, unmaskedValue) {
			return maskedValue.replace(/_/g, "").replace(/[-]+$/g, "");
		}
	});
	
	$(".btn.network").click(function(){
		
		$(".btn.network").removeClass('btn-primary');
		$(this).addClass('btn-primary');
		topUpOrderRequest.topUpOrder.networkOperator=$(this).val();
		
	});
	
	$(".btn.topUpAmount").click(function(){
		
		$(".btn.topUpAmount").removeClass('btn-primary');
		$(this).addClass('btn-primary');
		topUpOrderRequest.topUpOrder.topUpAmount=$(this).val();
		$('#payAmount').val(payAmountMap['p'+$(this).val()]);
		topUpOrderRequest.topUpOrder.paymentAmount=payAmountMap['p'+$(this).val()];
		
	});
	
	$(".btn.payMethod").click(function(){
		
		$(".btn.payMethod").removeClass('btn-primary');
		$(this).addClass('btn-primary');
		topUpOrderRequest.topUpOrder.payMethod=$(this).val();
		
	});
	
	$("#thaiTopUpCheckOut").click(function(){
		
		var m = $("#thaiTopupMoblieNumber").val();
		if( m!=null){
			topUpOrderRequest.topUpOrder.mobileNumber=m.replace(/-/g, "");
		}
		
		var constraints = {
				mobileNumber:{
					 presence: {
						 message:"{手机号码不能为空！}"
					 },
					  format: {
					      pattern: /^(08|09)[0-9]{8}$/,
					      message: function(value, attribute, validatorOptions, attributes, globalOptions) {
					        return validate.format("^%{num} 手机号码格式错误！ 泰国号码以08或09开头", {
					          num: value
					        });
					      }
					    }
				},
				networkOperator:{
					presence: {
						 message:"{请选择网络运营商！}"
					 },
				},
				topUpAmount:{
					presence: {
						 message:"{请选择充值金额！}"
					 },
				},
				payMethod:{
					presence: {
						 message:"{请选支付方式！}"
					 },
				}
				
		};
		
		var valid = validate(topUpOrderRequest.topUpOrder, constraints);
		if( valid!=null ){
			var msg = valid[Object.keys(valid)[0]].toString();
			if(msg.indexOf('{')>-1){
				msg=msg.substring(msg.indexOf('{')+1,msg.length-1);
			}
			$('#errorMessage').text(msg);
			$('#errorModal').modal('show');
			return false;
		}
		
		//获取订单id和加密字符串
		var host = document.domain;
		var port = window.location.port || '80';
		var namespace = "/uc"
		
		
		$('#goto-pay-modal').modal('show'); 
		
		//进入挑战到支付页面
		 $.ajax({
             url: 'http://'+host+':'+port+namespace,
             type: 'post',
             dataType: 'json',
             data: JSON.stringify(topUpOrderRequest),
             success: function (data) {
            	 
            	 if(data.code==0){
            		 $('#loading').hide();
            		 return false;
            	 }
            	 
            	 response=data;
            	 $('#loading').hide();
            	 $('td#orderId').text(response.paymentSubmitBean.orderId)
            	 $('td#mobileNumber').text(topUpOrderRequest.topUpOrder.mobileNumber)
            	 $('td#topupAmount').text(topUpOrderRequest.topUpOrder.topUpAmount)
            	 $('td#payAmount').text(topUpOrderRequest.topUpOrder.paymentAmount)
            	 $("#orderDetail").show();
            	 $('#gotopay').show();
             },
             error:function(data){
            	 alert('请求超时:'+JSON.stringify(data));
             }
         });
		
	});
	
	$('#goto-pay-modal').on('hidden.bs.modal', function () {
		 $('#loading').show();
    	 $("#orderDetail").hide();
    	 $('#gotopay').hide();
    	 $('#gotopay').hide();
	})
	
	$('#gotopay').click(function(){
		if(topUpOrderRequest.topUpOrder.payMethod=='ALIPAY_TRUST'){
			 window.open('view/alipaySubmit.html?sign='+response.paymentSubmitBean.sign+'&merchant='+response.paymentSubmitBean.merchant+'&orderId='+response.paymentSubmitBean.orderId+'&amount='+topUpOrderRequest.topUpOrder.paymentAmount+'&mobileNumber='+topUpOrderRequest.topUpOrder.mobileNumber+'&apiAddress='+response.paymentSubmitBean.apiAddress,'_blank');
		 }else if(topUpOrderRequest.topUpOrder.payMethod=='PAYPAL'){
			 window.open('view/paypalSubmit.html','_blank');
 		}
	});
	

})

function initPayAmountMap(){
	payAmountMap.p10=10/5;
	payAmountMap.p20=20/5;
	payAmountMap.p30=30/5;
	payAmountMap.p50=50/5;
	payAmountMap.p100=100/5;
	payAmountMap.p200=200/5;
	payAmountMap.p300=300/5;
	payAmountMap.p500=500/5;
	payAmountMap.p800=800/5;
}
