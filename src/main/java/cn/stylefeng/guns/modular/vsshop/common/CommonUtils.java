package cn.stylefeng.guns.modular.vsshop.common;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class CommonUtils {
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static UrlSource getSource(String url){
        if(StrUtil.isNotEmpty(url)){
            try {
                URL uri= new URL(url);
                if(StrUtil.startWith(StrUtil.subAfter(uri.getHost(),".",false),Constants.CSDN_DOMAIN_URL)){
                    return UrlSource.CSDN;
                }
            } catch (MalformedURLException e) {

            }
        }

        return UrlSource.UNKNOW;
    }
}
