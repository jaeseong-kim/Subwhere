var main = {
    init : function(){
        var _this = this;

        $('#btn-get-off').on('click',function(){
            _this.getOff();
        });
    },

    getOff : function(){
        var trainNo = $('input[name=trainNo]:checked').val();

        $.ajax({
            type : 'DELETE',
            url : '/user/train/'+trainNo,
        }).done(function(res){
            alert(res);
            location.reload();
        })
    }
}



main.init();