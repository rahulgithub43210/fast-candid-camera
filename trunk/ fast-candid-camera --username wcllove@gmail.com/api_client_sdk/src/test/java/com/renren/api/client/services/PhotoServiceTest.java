package com.renren.api.client.services;

import java.util.Date;

import junit.framework.Assert;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.renren.api.client.AbstractServiceTest;
import com.renren.api.client.utils.HttpURLUtils;

/**
 * @author 李勇(yong.li@opi-corp.com) 2011-2-21
 */
public class PhotoServiceTest extends AbstractServiceTest {

    @Test
    public void testUpload() {
        long albumId = 431994435;
        String fileName = "http://www.baidu.com/img/baidu_sylogo1.gif";
        String desc = "renren by " + new Date();
        byte[] photo = HttpURLUtils.getBytes(fileName, null);
        JSONObject ret = this.getRenrenApiClient().getPhotoService().upload(albumId, photo,
                fileName, desc);
        System.out.println("photo:" + ret);
        Assert.assertEquals(albumId, ret.get("aid"));
    }
}
