
layui.define(['layer', 'element'], function (exports) {

    var $ = layui.jquery;


    var mod = {

        deleteAll: function (ids, url, sUrl, eUrl) {

            if (ids == null || ids == '') {
                layer.msg('Please select the data you want to delete', {time: 2000});
                return false;
            } else {
                layer.confirm('Confirm that the selected data is deleted?', {
                    title: 'delete',
                    btn: ['confirm', 'cancel']
                }, function (index, layero) {

                    $.post(url, {ids: ids}, function (res) {

                        if (res.status > 0) {

                            layer.msg(res.msg, {time: 1500}, function () {
                                location.href = sUrl;
                            })
                        } else {

                            layer.msg(res.msg, {time: 1500}, function () {
                                location.href = eUrl;
                            })
                        }
                    });
                }, function (index) {

                    layer.close(index);
                });
            }
        }

        ,unixToDate: function (unixTime, isFull, timeZone) {
            if (unixTime == '' || unixTime == null) {
                return '';
            }
            if (typeof (timeZone) == 'number') {
                unixTime = parseInt(unixTime) + parseInt(timeZone) * 60 * 60;
            }
            var time = new Date(unixTime * 1000);
            var ymdhis = "";
            var year, month, date, hours, minutes, seconds;
            if (time.getUTCFullYear() < 10) {
                year = '0' + time.getUTCFullYear();
            } else {
                year = time.getUTCFullYear();
            }
            if ((time.getUTCMonth() + 1) < 10) {
                month = '0' + (time.getUTCMonth() + 1);
            } else {
                month = (time.getUTCMonth() + 1);
            }
            if (time.getUTCDate() < 10) {
                date = '0' + time.getUTCDate();
            } else {
                date = time.getUTCDate();
            }
            ymdhis += year + "-";
            ymdhis += month + "-";
            ymdhis += date;
            if (isFull === true) {
                if (time.getUTCHours() < 10) {
                    hours = '0' + time.getUTCHours();
                } else {
                    hours = time.getUTCHours();
                }
                if (time.getUTCMinutes() < 10) {
                    minutes = '0' + time.getUTCMinutes();
                } else {
                    minutes = time.getUTCMinutes();
                }
                if (time.getUTCSeconds() < 10) {
                    seconds = '0' + time.getUTCSeconds();
                } else {
                    seconds = time.getUTCSeconds();
                }
                ymdhis += " " + hours + ":";
                ymdhis += minutes;
                // ymdhis += seconds;
            }
            return ymdhis;
        }

        ,getIds: function (o, str) {
            var obj = o.find('tbody tr td:first-child input[type="checkbox"]:checked');
            var list = '';
            obj.each(function (index, elem) {
                list += $(elem).attr(str) + ',';
            });

            list = list.substr(0, (list.length - 1));
            return list;
        }

        ,getFullHeight: function(){
            return $(window).height() - ( $('.my-btn-box').outerHeight(true) ? $('.my-btn-box').outerHeight(true) + 35 :  40 );
        }
    };


    exports('vip_table', mod);
});

