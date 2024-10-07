/**
 * 第三方用户管理初始化
 */
var VssUser = {
    id: "VssUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
VssUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'uuid', field: 'uuid', visible: true, align: 'center', valign: 'middle'},
            {title: '来源', field: 'source', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                    if (value == '1') {
                        return "CSDN";
                    } else if (value == '0'){
                        return "无";
                    }
                }
            },
            {title: '类型', field: 'type', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                    if (value == '1') {
                        return "通用";
                    } else if(value == '2'){
                        return "大积分";
                    }
                }
            },
            {title: '积分', field: 'coin', visible: true, align: 'center', valign: 'middle'},
            {title: '用户名', field: 'username', visible: true, align: 'center', valign: 'middle'},
            {title: '密码', field: 'password', visible: true, align: 'center', valign: 'middle'},
            {title: 'cookie', field: 'cookie', visible: true, align: 'center', valign: 'middle'},
            {title: '登录状态', field: 'loginStatus', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                    if (value == '1') {
                        return "登陆成功";
                    } else if (value == '2'){
                        return "登陆失败";
                    }else{
                        return "未知";
                    }
                }
            },
            {title: '用户状态', field: 'status', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                    if (value == '1') {
                        return "正常";
                    } else if (value == '2'){
                        return "禁用";
                    }
                }
            },
            {title: '最后登录', field: 'lastLoginTime', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
VssUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        VssUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加第三方用户
 */
VssUser.openAddVssUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加第三方用户',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/vssUser/vssUser_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看第三方用户详情
 */
VssUser.openVssUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '第三方用户详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/vssUser/vssUser_update/' + VssUser.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除第三方用户
 */
VssUser.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/vssUser/delete", function (data) {
            Feng.success("删除成功!");
            VssUser.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("vssUserId",this.seItem.uuid);
        ajax.start();
    }
};

/**
 * 查询第三方用户列表
 */
VssUser.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    VssUser.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = VssUser.initColumn();
    var table = new BSTable(VssUser.id, "/vssUser/list", defaultColunms);
    table.setPaginationType("client");
    VssUser.table = table.init();
});
