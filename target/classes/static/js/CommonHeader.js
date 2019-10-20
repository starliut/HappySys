
var happyuser=$("#aab").text();

if(happyuser.length>0){
    $("#notLogin").css("display","none");
    $("#yesLogin").css("display","block");
}

var sel = $('.header-nav-link > dl'),
    nav = $('.main-nav-more'),
    jsign = $('#home');

sel.hover(
    function () {
        $(this).addClass('active');
        $(this)
            .find('dt')
            .addClass('sel')
            .end()
            .find('.child')
            .show();
        $(this)
            .siblings('dl')
            .removeClass('hover');
    },
    function () {
        $(this).removeClass('active');
        $(this)
            .find('dt')
            .removeClass('sel')
            .end()
            .find('.child')
            .hide();
    }
);

if (jsign.length == 0) {
    nav.hover(
        function () {
            $(this)
                .find('.main-nav-box')
                .show();
        },
        function () {
            $(this)
                .find('.main-nav-box')
                .hide();
        }
    );
}


var jNoLogin = $('#loginMemberState .login_out'); //未登录模块
var jYesLogin = $('#loginMemberState .login_in'); //已登录模块

jNoLogin.hover(
    function () {
        $(this)
            .find('.user-box')
            .show();
    },
    function () {
        $(this)
            .find('.user-box')
            .hide();
    }
);

jYesLogin.hover(
    function () {
        $(this)
            .find('.user-box')
            .show();
        $(this)
            .find('.user-head .icon-xiangxia')
            .addClass('active');
    },
    function () {
        $(this)
            .find('.user-box')
            .hide();
        $(this)
            .find('.user-head .icon-xiangxia')
            .removeClass('active');
    }
)


$('.main-nav-search .hot a').on('click', function () {
    var key = $(this).text();
    $('#searchContent').val(key);
    $('#mainSearch').trigger('click');
});

$('#mainSearch').on('click', function () {
    res.resSearchSbt();
})


var jnav = $('.main-nav-list'),
    jli = jnav.find('li'),
    jChild = jli.find('.child-link'),
    jlia = jnav.find('a'),
    currenturl = document.location.href,
    nlast = 0;

for (var i = 0; i < jli.length; i++) {
    var linkurl = jlia.eq(i).attr('href');
    if (currenturl.indexOf(linkurl) != -1) nlast = i;
}

jli.eq(nlast).addClass('sel');
jChild.parent().hover(
    function () {
        $(this)
            .find('.iconfont')
            .addClass('up');
        jChild.show();
    },
    function () {
        $(this)
            .find('.iconfont')
            .removeClass('up');
        jChild.hide();
    }
)

var jchild = $(".toorbar-box-top,.toorbar-box-wechart");

//显示二维码
jchild.on('mouseover mouseout', function(event) {
    if (event.type == 'mouseover') {
        $(this)
            .addClass('sel')
            .children('.child')
            .show();
    } else if (event.type == 'mouseout') {
        $(this)
            .removeClass('sel')
            .children('.child')
            .hide();
    }
});

var jtop = $(".toorbar-box-top");

//返回顶部
jtop.on('click', function() {
    $('body, html').animate(
        {
            scrollTop: 0
        },
        400
    );
});