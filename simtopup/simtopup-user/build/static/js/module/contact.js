$('head').append( $('<link rel="stylesheet" type="text/css" />').attr('href', 'css/lib/zzsc.css') );

$(function(){
	
	$(".yb_conct").hover(function() {
		$(".yb_conct").css("right", "5px");
		$(".yb_bar .yb_ercode").css('height', '200px');
	}, function() {
		$(".yb_conct").css("right", "-147px");
		$(".yb_bar .yb_ercode").css('height', '53px');
	});
	
	$(".yb_top").click(function() {
		$("html,body").animate({
			'scrollTop': '0px'
		}, 300)
	});
	
});