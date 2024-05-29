layui.config({
    base: '/static/js/'
}).extend({
    vip_nav: 'vip_nav'
    , vip_tab: 'vip_tab'
    , vip_table: 'vip_table'
});


layui.use(['layer', 'element', 'util','vip_nav'], function () {


    var device = layui.device()
        , element = layui.element
        , layer = layui.layer
        , util = layui.util
        , $ = layui.jquery
        , cardIdx = 0
        , cardLayId = 0
        , side = $('.my-side')
        , body = $('.my-body')
        ,vipNav     = layui.vip_nav
        , footer = $('.my-footer');


    if (device.ie && device.ie < 8) {
        layer.alert('If you have to use IE to browse the VIP-admin background template, then please use IE8+');
    }


    function navHide(t, st) {
        var time = t ? t : 50;
        st ? localStorage.log = 1 : localStorage.log = 0;
        side.animate({'left': -200}, time);
        body.animate({'left': 0}, time);
        footer.animate({'left': 0}, time);
    }


    function navShow(t, st) {
        var time = t ? t : 50;
        st ? localStorage.log = 0 : localStorage.log = 1;
        side.animate({'left': 0}, time);
        body.animate({'left': 200}, time);
        footer.animate({'left': 200}, time);
    }


    $('.btn-nav').on('click', function () {
        if (localStorage.log == 0) {
            navShow(50);
        } else {
            navHide(50);
        }
    });


    function getTitleId(card, title) {
        var id = -1;
        $(document).find(".layui-tab[lay-filter=" + card + "] ul li").each(function () {
            if (title === $(this).find('span').html()) {
                id = $(this).attr('lay-id');
            }
        });
        return id;
    }


    window.addTab = function (elem, tit, url) {
        var card = 'card';
        var title = tit ? tit : elem.children('a').html();
        var src = url ? url : elem.children('a').attr('href-url');
        var id = new Date().getTime();
        var flag = getTitleId(card, title);

        if (flag > 0) {
            id = flag;
        } else {
            if (src) {

                element.tabAdd(card, {
                    title: '<span>' + title + '</span>'
                    , content: '<iframe src="' + src + '" frameborder="0"></iframe>'
                    , id: id
                });

                layer.closeAll();
            }
        }

        element.tabChange(card, id);

        // layer.msg(title);
    };


    element.on('nav(side-top-left)', function (elem) {

        //window.addTab(elem);
        var id=elem.children("a").attr("id");
        var url = '/system/res/menuLeft?pid='+id;
        vipNav.main(url,'side-main',true);
    });


    element.on('nav(side-top-right)', function (elem) {

        if ($(this).attr('data-skin')) {
            localStorage.skin = $(this).attr('data-skin');
            skin();
        } else {

            window.addTab(elem);
        }
    });


    element.on('nav(side-main)', function (elem) {

        window.addTab(elem);
    });


    window.delTab = function (layId) {

        element.tabDelete('card', layId);
    };


    window.delAllTab = function () {

        layui.each($('.my-body .layui-tab-title > li'), function (k, v) {
            var layId = $(v).attr('lay-id');
            if (layId > 1) {

                element.tabDelete('card', layId);
            }
        });
    };


    window.getThisTabID = function () {

        return $(document).find('body .my-body .layui-tab-card > .layui-tab-title .layui-this').attr('lay-id');
    };


    $(document).on('dblclick', '.my-body .layui-tab-card > .layui-tab-title li', function () {

        if ($(this).index() > 0) {
            element.tabDelete('card', $(this).attr('lay-id'));
        } else {
            layer.msg('The welcome page cannot be closed')
        }
    });


    $(document).on("contextmenu", '.my-body .layui-tab-card > .layui-tab-title li', function () {
        return false;
    });


    $(document).on("mousedown", '.my-body .layui-tab-card > .layui-tab-title li', function (e) {

        if (3 == e.which && $(this).index() > 0) {

            cardIdx = $(this).index();
            cardLayId = $(this).attr('lay-id');
            console.log('lay-id:' + cardLayId);

            layer.tips($('.my-dblclick-box').html(), $(this), {
                skin: 'dblclick-tips-box',
                tips: 3,
                time: false
            });
        }
    });


    $(document).on('click', 'html', function () {
        layer.closeAll('tips');
    });


    $(document).on('click', '.card-refresh', function () {

        var ifr = $(document).find('.my-body .layui-tab-content .layui-tab-item iframe').eq(cardIdx);

        ifr.attr('src', ifr.attr('src'));

        element.tabChange('card', cardLayId);
    });


    $(document).on('click', '.card-close', function () {

        window.delTab(cardLayId);
    });


    $(document).on('click', '.card-close-all', function () {

        window.delAllTab();
    });


    $('.pay').on('click', function () {
        layer.open({
            type: 1,
            title: false,
            closeBtn: false,
            shadeClose: true,
            area: ['auto', 'auto'],
            content: $('.my-pay-box')
        });
    });


    function skin() {
        var skin = localStorage.skin ? localStorage.skin : 0;
        var body = $('body');
        body.removeClass('skin-0');
        body.removeClass('skin-1');
        body.removeClass('skin-2');
        body.addClass('skin-' + skin);
    }


    function _util() {
        var bar = $('.layui-fixbar');

        if ($(window).width() < 1023) {
            util.fixbar({
                bar1: '&#xe602;'
                , css: {left: 10, bottom: 54}
                , click: function (type) {
                    if (type === 'bar1') {
                        //iframe layer
                        layer.open({
                            type: 1,
                            title: false,
                            offset: 'l',
                            closeBtn: 0,
                            anim: 0,
                            shadeClose: true,
                            shade: 0.8,
                            area: ['150px', '100%'],
                            skin: 'my-mobile',
                            content: $('body .my-side').html()
                        });
                    }
                    element.init();
                }
            });
            bar.removeClass('layui-hide');
            bar.addClass('layui-show');
        } else {
            bar.removeClass('layui-show');
            bar.addClass('layui-hide');
        }
    };


    $(window).on('resize', function () {
        if ($(window).width() > 1023) {
            navShow(10);
        } else {
            navHide(10);
        }

        cardTitleHeight = $(document).find(".layui-tab[lay-filter='card'] ul.layui-tab-title").height();

        height = $(window).height() - $('.layui-header').height() - cardTitleHeight - $('.layui-footer').height();

        $(document).find(".layui-tab[lay-filter='card'] div.layui-tab-content").height(height - 2);
        _util();
    });


    function init() {

        if (!localStorage.log) {
            if ($(window).width() > 1023) {
                if (localStorage.log == 0) {
                    navHide(10);
                } else {
                    navShow(10);
                }
            } else {
                navHide(10);
            }
        } else {
            if (localStorage.log == 0) {
                navHide(10);
            } else {
                navShow(10);
            }
        }

        _util();
        // skin
        skin();

        cardTitleHeight = $(document).find(".layui-tab[lay-filter='card'] ul.layui-tab-title").height();

        height = $(window).height() - $('.layui-header').height() - cardTitleHeight - $('.layui-footer').height();

        $(document).find(".layui-tab[lay-filter='card'] div.layui-tab-content").height(height - 2);
    }


    init();
});