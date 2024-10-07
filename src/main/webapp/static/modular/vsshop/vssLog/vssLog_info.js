/**
 * 初始化下载日志详情对话框
 */
var VssLogInfoDlg = {
    vssLogInfoData : {}
};

/**
 * 清除数据
 */
VssLogInfoDlg.clearData = function() {
    this.vssLogInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VssLogInfoDlg.set = function(key, val) {
    this.vssLogInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VssLogInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
VssLogInfoDlg.close = function() {
    parent.layer.close(window.parent.VssLog.layerIndex);
}

/**
 * 收集数据
 */
VssLogInfoDlg.collectData = function() {
    this
    .set('uuid')
    .set('reuse')
    .set('code')
    .set('coin')
    .set('source')
    .set('vssuser')
    .set('downUrl')
    .set('filePath')
    .set('insertTime');
}

/**
 * 提交添加
 */
VssLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/vssLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.VssLog.table.refresh();
        VssLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.vssLogInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
VssLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/vssLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.VssLog.table.refresh();
        VssLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.vssLogInfoData);
    ajax.start();
}

$(function() {

});
