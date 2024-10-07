package cn.stylefeng.guns.modular.vsshop.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.modular.vsshop.model.HttpVssUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HttpVssUserHelper {
    public static List<HttpVssUser> httpVssUserList = Collections.synchronizedList(new ArrayList<>());

    /**
     * 随机获取下载用户
     *
     * @param urlSource
     * @param vssUserType
     * @return
     */
    public static HttpVssUser getHttpVssUser(UrlSource urlSource, String vssUserType) {
        //筛选同时满足来源及用户类型的可用用户

        List<HttpVssUser> tempList = httpVssUserList.stream().filter(httpVssUser ->vssUserType.equals(httpVssUser.getVssUser().getType()) && String.valueOf(urlSource.ordinal()).equals(httpVssUser.getVssUser().getSource()) && "1".equals(httpVssUser.getVssUser().getLoginStatus()) && "1".equals(httpVssUser.getVssUser().getStatus())).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(tempList)) {
            int random = RandomUtil.randomInt(tempList.size());
            return tempList.get(random);
        }
        return null;
    }

    /**
     * 随机获取下载用户
     *
     * @param urlSource
     * @param vssUserType
     * @return
     */
    public static HttpVssUser getHttpVssUser(UrlSource urlSource, String vssUserType,Integer coin) {
        //筛选同时满足来源及用户类型的可用用户

        List<HttpVssUser> tempList = httpVssUserList.stream().filter(httpVssUser ->vssUserType.equals(httpVssUser.getVssUser().getType()) && String.valueOf(urlSource.ordinal()).equals(httpVssUser.getVssUser().getSource()) && "1".equals(httpVssUser.getVssUser().getLoginStatus()) && "1".equals(httpVssUser.getVssUser().getStatus()) && Integer.parseInt(httpVssUser.getVssUser().getCoin())>=coin).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(tempList)) {
            int random = RandomUtil.randomInt(tempList.size());
            return tempList.get(random);
        }
        return null;
    }


}
