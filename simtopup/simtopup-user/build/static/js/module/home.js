$(function(){
	
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
	
});
