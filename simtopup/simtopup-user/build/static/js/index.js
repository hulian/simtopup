$(function(){
	
	$('head').append( $('<link rel="stylesheet" type="text/css" />').attr('href', 'css/lib/style.css') );
	
	
	$('[data-toggle="tabajax"]').click(function(e) {
	    var $this = $(this),
	        loadurl = $this.attr('href'),
	        targ = $this.attr('data-target');
	    $this.tab('show');
	    $.get(loadurl, function(data) {
	        $(targ).html(data);
	    });
	    return false;
	});
	
	//load index page
	$.get('view/module/index.html',function(data){
		$('#container').html(data);
	});
	
	//load home page
	$.get('view/module/home.html',function(data){
		$('#home_table').html(data);
	});
	
	$.get('view/module/contact.html',function(data){
		$('#contact').html(data);
	});
	
});
