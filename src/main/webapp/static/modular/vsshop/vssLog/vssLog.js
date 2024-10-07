/**
 * 下载日志管理初始化
 */
var VssLog = {
    id: "VssLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
VssLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '激活码', field: 'code', visible: true, align: 'center', valign: 'middle',width:'250px'},
            {title: '是否复用', field: 'reuse', visible: true, align: 'center', valign: 'middle'},
            {title: '需要积分', field: 'coin', visible: true, align: 'center', valign: 'middle'},
            {title: '下载地址', field: 'downUrl', visible: true, align: 'center', valign: 'middle',width:'550px'},
            {title: '文件地址', field: 'filePath', visible: true, align: 'center', valign: 'middle',width:'300px'},
            {title: '记录时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle',width:'150px'},
            {title: '网站用户', field: 'vssuser', visible: true, align: 'center', valign: 'middle'},
            {title: '网站', field: 'source', visible: true, align: 'center', valign: 'middle'},
            {title: 'uuid', field: 'uuid', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
VssLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        VssLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加下载日志
 */
VssLog.openAddVssLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加下载日志',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/vssLog/vssLog_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看下载日志详情
 */
VssLog.openVssLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '下载日志详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/vssLog/vssLog_update/' + VssLog.seItem.uuid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除下载日志
 */
VssLog.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/vssLog/delete", function (data) {
            Feng.success("删除成功!");
            VssLog.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("vssLogId",this.seItem.uuid);
        ajax.start();
    }
};

/**
 * 查询下载日志列表
 */
VssLog.search = function () {
    var queryData = {};
    queryData['code'] = $("#code").val();
    VssLog.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = VssLog.initColumn();
    var table = new BSTable(VssLog.id, "/vssLog/list", defaultColunms);
    table.setPaginationType("server");
    VssLog.table = table.init();
});
