/**
 * 初始化激活码详情对话框
 */
var VssCodeInfoDlg = {
    vssCodeInfoData : {}
};

/**
 * 清除数据
 */
VssCodeInfoDlg.clearData = function() {
    this.vssCodeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VssCodeInfoDlg.set = function(key, val) {
    this.vssCodeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VssCodeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
VssCodeInfoDlg.close = function() {
    parent.layer.close(window.parent.VssCode.layerIndex);
}

/**
 * 收集数据
 */
VssCodeInfoDlg.collectData = function() {
    this
    .set('uuid')
    .set('code')
    .set('downUrl')
    .set('filePath')
    .set('coin')
    .set('status')
    .set('ip')
    .set('name')
    .set('tel')
    .set('wx')
    .set('email')
    .set('source')
    .set('type')
}

/**
 * 提交添加
 */
VssCodeInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/vssCode/add", function (data) {
        Feng.success("添加成功!");
        window.parent.VssCode.table.refresh();
        VssCodeInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set("source",this.vssCodeInfoData.source);
    ajax.set("type",this.vssCodeInfoData.type);
    ajax.set("num",this.vssCodeInfoData.num);
    ajax.start();
}

/**
 * 提交修改
 */
VssCodeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/vssCode/update", function(data){
        Feng.success("修改成功!");
        window.parent.VssCode.table.refresh();
        VssCodeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.vssCodeInfoData);
    ajax.start();
}

$(function() {
    if($("#statusValue").val() == undefined){
        $("#status").val(1);
    }else{
        $("#status").val($("#statusValue").val());
    }
    if($("#sourceValue").val() == undefined){
        $("#source").val(1);
    }else{
        $("#source").val($("#sourceValue").val());
    }
    if($("#typeValue").val() == undefined){
        $("#type").val(1);
    }else{
        $("#type").val($("#typeValue").val());
    }

});
