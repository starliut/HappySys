$(function(){
    var jBtn = $('.quicksel-nav-ul li');
    var jCont = $('.quicksel-case-des');
    var jcase = $('.quicksel-case-des li');
    var jcollection = '.quick-con-shop  .active .collection';
    var jdepthcollection = $('.quick-con-shop  .active .btns span');
    var jclosed = '.quicksel-case-des  .icon-guanbi';
    var jswitch = $('.quick-shop-hot  .switch');
    var index = 0;

    jBtn.mouseover(function (e) {
        e.preventDefault();

        var nIndex = $(this).index();

        jBtn.removeClass('sel')
            .eq(nIndex)
            .addClass('sel');
        jCont.hide()
            .eq(nIndex)
            .show();
    })

    //点击二级分类 加载产品
    jcase.click(function (e) {
        e.preventDefault();
        $(this).addClass('sel').siblings().removeClass('sel')
        var obj = {
            _this: this
        }
        res.caseshopFun(obj);
    })


    //收藏回调
    $('#quicksel-box').delegate(jcollection,'click',function () {
        res.collectionFun(this);
    })

    //深度评测
    jdepthcollection.hover(
      function (event) {
          if(event.type == "mouseover"){
              $(this).children('img').show();
          }else if(event.type == "mouseout"){
              $(this).children('img').hide();
          }
      }
    )
  /*  jdepthcollection.on('mouseover mouseout',function(event){
        if(event.type == "mouseover"){
            $(this).children('img').show();
        }else if(event.type == "mouseout"){
            $(this).children('img').hide();
        }
    })*/

    //换一换
    jswitch.click(function () {
        var pages = $(".quick-shop-hot .list ul").length-1;

        if(index < pages){
            index++;
        }else{
            index = 0;
        }
        $(".quick-shop-hot .list ul").eq(index).show().siblings().hide();
    })

    //关闭提示
    $(document).delegate(jclosed,'click',function(){
        $(this).parents('.tip').hide();
    })
})

/*
function(){

            var jBtn = $('.quicksel-nav-ul li');
            var jCont = $('.quicksel-case-des');
            var jcase = $('.quicksel-case-des li');
            var jcollection = '.quick-con-shop  .active .collection';
            var jdepthcollection = $('.quick-con-shop  .active .btns span');
            var jclosed = '.quicksel-case-des  .icon-guanbi';
            var jswitch = $('.quick-shop-hot  .switch');
            var index = 0;


            jBtn.on('mouseover', function(e) {
                e.preventDefault();

                var nIndex = $(this).index();

                jBtn.removeClass('sel')
                    .eq(nIndex)
                    .addClass('sel');
                jCont.hide()
                    .eq(nIndex)
                    .show();
            });

            //点击二级分类 加载产品
            jcase.on('click',function (e) {
                e.preventDefault();
                $(this).addClass('sel').siblings().removeClass('sel')
                var obj = {
                    _this: this
                }
                res.caseshopFun(obj);
            })

            //收藏回调
            $('#quicksel-box').delegate(jcollection,'click',function () {
                res.collectionFun(this);
            })

            //深度评测
            jdepthcollection.on('mouseover mouseout',function(event){
                if(event.type == "mouseover"){
                    $(this).children('img').show();
                }else if(event.type == "mouseout"){
                    $(this).children('img').hide();
                }
            })

            //换一换
            jswitch.on('click',function(){
                var pages = $(".quick-shop-hot .list ul").length-1;

                if(index < pages){
                    index++;
                }else{
                    index = 0;
                }
                $(".quick-shop-hot .list ul").eq(index).show().siblings().hide();
            });
            //关闭提示
            $(document).delegate(jclosed,'click',function(){
                $(this).parents('.tip').hide();
            })



        }


    }.init();


});*/
