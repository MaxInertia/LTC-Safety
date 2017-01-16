// JavaScript File
$(".remember_me").click(function(){ 
    if($( "input:checkbox"  ).prop( 'checked' )==true){
        $( "input:checkbox" ).prop('checked', false);
    }else{
        $( "input:checkbox" ).prop('checked', true);
    }
});