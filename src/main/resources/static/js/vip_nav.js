
layui.define(['layer', 'element'], function (exports) {

    var layer = layui.layer
        , element = layui.element
        , $ = layui.jquery;
    var firstId;


    var mod = {

        addHtml:  function (addr, obj, treeStatus, data) {

            $.ajax({
                type : "get",
                url : addr,
                async : false,
                success : function(res){
                    var view = "";
                    if (res.data) {
                        $(res.data).each(function (k, v) {
                            v.subset && treeStatus ? view += '<li class="layui-nav-item layui-nav-itemed">' : view += '<li class="layui-nav-item" >';
                            if (v.subset) {
                                view += '<a href="javascript:;"><i class="layui-icon">' + v.icon + '</i>' + v.text + '</a><dl class="layui-nav-child">';
                                $(v.subset).each(function (ko, vo) {
                                    view += '<dd>';
                                    if(vo.target){
                                        view += '<a href="' + vo.href + '" target="_blank">';
                                    }else{
                                        view += '<a href="javascript:;" href-url="' + vo.href + '">';
                                    }
                                    view += '<i class="iconfont">' + vo.icon + '</i>' + vo.text + '</a></dd>';
                                });
                                view += '<dl>';
                            } else {
                                if (v.target) {
                                    view += '<a href="' + v.href + '" target="_blank">';
                                } else {
                                    view += '<a href="javascript:;" id='+v.mark+'  href-url="' + v.href + '" >';
                                    if(k==0 && typeof(firstId) == "undefined"){
                                        firstId = v.mark;
                                    }
                                }
                                view += '<i class="iconfont" >' + v.icon + '</i>' + v.text + '</a>';
                            }
                            view += '</li>';
                        });
                    } else {
                        layer.msg('Accepted menu data is not up to specification and cannot be parsed');
                    }

                    $(document).find(".layui-nav[lay-filter=" + obj + "]").html(view);


                    element.init();
                }
            });
        }

        , main: function (addr, obj, treeStatus, data) {

            this.addHtml(addr, obj, treeStatus, data);
        }

        , top_left: function (addr, obj, treeStatus, data) {

            this.addHtml(addr, obj, treeStatus, data);
            this.main('/system/res/menuLeft?pid='+firstId,'side-main',true);
        }

    };


    exports('vip_nav', mod);
});


