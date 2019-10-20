'use strict';

var GLOBAL_OBJ = {};
({
    
    init: function() {
        this.setting();     //文件路径解析
        this.config();      //模块化管理文件
        this.theme();       //设置主题色
        this.fundebug();    //客户端异常监控
    },

    /* 文件路径解析 */
    setting: function() {
        for(var i=0,links=document.getElementsByTagName("link"); i<links.length; i++) {
            links[i].rel == 'dns-prefetch' && links[i].id == 'static-path' && (GLOBAL_OBJ.staticPath = links[i].href);      //静态资源路径
            links[i].rel == 'dns-prefetch' && links[i].id == 'dynamic-path' && (GLOBAL_OBJ.dynamicPath = links[i].href);    //工程路径
            links[i].rel == 'dns-prefetch' && links[i].id == 'cache-path' && (GLOBAL_OBJ.cachePath = links[i].href);        //Redis缓存路径
        }
    },

    /* 模块化管理文件 */
    config: function() {
        var sPath = GLOBAL_OBJ.staticPath + 'js',
            sVer = 'ver.' + new Date().getFullYear() + '.' + (new Date().getMonth() + 1) + '.' + new Date().getDate() + '.' + new Date().getHours(),
            sUrl = 'https://common.kaixinbao.com/release/www/';

        require.config({
            baseUrl: sPath,
            urlArgs: sVer,
            waitSeconds: 0,
            deps: ['main'],
            paths: {
                /* ========== libs file ========== */
                'css': sUrl + './libs/css.min',
                'jquery': sUrl + './libs/jquery-1.12.4.min',
                'fundebug': sUrl + './libs/fundebug.min',
                'clipboard': sUrl + './libs/clipboard.min',

                'swiper': './lib/swiper-2.7.6.min',
                'lazyload': './lib/jquery.lazyload.min',
                'ajaxsubmit': './lib/jquery.form.min',
                'lcalendar': './lib/lcalendar.min',
                'pagination': './lib/pagination.min',
                'layer': './lib/layer.min',
                'rotateplug':'./lib/jqueryRotate',
                /* ========== /libs file ========== */

                /* ========== plugin file ========== */
                'DataVerify': './plugin/dataverify',
                'NavBar': './plugin/navbar',
                'PopDialog': './plugin/popdialog',
                'LayDate': './plugin/laydate',
                'ReSelect': './plugin/reselect',
                'PageSplit': './plugin/pagesplit',
                'SendMessage': './plugin/sendmessage',
                'Vitcker': './plugin/vticker',
                'Toolbar': './plugin/toolbar',
                /* ========== /plugin file ========== */

                /* ========== component file ========== */
                '__DataVerify': sUrl + './DataVerify/v1.0/index',
                '__ReSelect': sUrl + './ReSelect/v1.0/index',
                '__LayDate': sUrl + './LayDate/v1.0/index',
                '__PopDialog': sUrl + './PopDialog/v1.0/index',
                '__PageSplit': sUrl + './PageSplit/v1.0/index',
                '__Swiper': sUrl + './Swiper/v1.0/index',
                /* ========== /component file ========== */

                /* ========== module file ========== */
                'main': './common/mod_main',
                'home': './home/mod_home',
                'tribe': './tribe/mod_tribe',
                'integral': './integral/mod_integral',
                'active': './active/mod_active',
                'claim': './claim/mod_claim',
                'search': './search/mod_search',
                'register': './login/mod_register',
                'bindtel': './login/mod_bindtel',
                'fpassword': './login/mod_fpassword',
                'adddata': './login/mod_adddata',
                'bindemail': './login/mod_bindemail',
                'newslist': './news/mod_newslist',
                'question': './news/mod_question',
                'newdetails': './news/mod_newdetails',
                'questionlist': './news/mod_questionlist',
                'quicksel': './quicksel/mod_quicksel',
                'shopcar': './shopcar/mod_shopcar',
                'cashvalue': './shop/mod_cashvalue',
                'freeins': './shop/mod_freeins',
                'rotate': './tribe/mod_rotate',
                'insurefile': './tribe/mod_insurefile',
                'secretbox': './tribe/mod_secretbox',
                'content': './content/mod_content',
                'conmain': './content/mod_conmain',
                'conlist': './content/mod_conlist',
                'poplogin': './login/mod_poplogin',
                'renew':'./shop/mod_renew',
                'renewInfor':'./shop/mod_renewInfor'
                /* ========== /module file ========== */
            },
            shim: {
                'swiper': ['css!../css/lib/swiper.min.css'],
                'PopDialog': ['css!../css/lib/layer/layer.css'],
                'PageSplit': ['css!../css/lib/pagination.min.css'],
                'LayDate': ['css!../css/lib/laydate/laydate.min.css'],
                'ReSelect': ['css!../css/lib/reselect.min.css'],
                'Toolbar': ['css!../css/lib/toolbar.css']
            }
        });
    },

    /* 设置主题色 */
    theme: function() {
        window['release__theme'] = '#fd8824';
    },

    /* 客户端异常监控 */
    fundebug: function() {
        var sPath = GLOBAL_OBJ.dynamicPath;

        sPath.indexOf('//www.kaixinbao.com') != -1 && require(['fundebug'], function(fundebug) {
            fundebug.apikey = 'a9f9d6aa9e26df437ee816d75a804a18c8872dce106fbfe030209866d9302f49';
            fundebug.silentResource = true;
            fundebug.silentVideo = false;
            fundebug.releasestage = 'PC';
        });
    }

}).init();
