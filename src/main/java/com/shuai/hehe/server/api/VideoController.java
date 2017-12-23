package com.shuai.hehe.server.api;

import com.shuai.hehe.server.entity.Feed;
import com.shuai.hehe.server.mapper.FeedMapper;
import com.shuai.hehe.server.response.ResponseInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;

/**
 *
 */
@RestController
public class VideoController {
    @Autowired
    private FeedMapper mMapper;

    private final OkHttpClient mClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private static final int MAX_PAGE_COUNT = 50;

    /**
     * 获取分页视频信息
     *
     * @param id
     * @param count
     * @return
     */
    @GetMapping("api/getVideoList")
    public ResponseInfo<List<Feed>> getVideoList(
            @RequestParam(value = "id", required = false, defaultValue = "-1") long id,
            @RequestParam(value = "count", required = false, defaultValue = "-20") int count) {
        if (id <= 0) {
            id = new Date().getTime();
        }

        //检查count
        if (count > MAX_PAGE_COUNT)
            count = MAX_PAGE_COUNT;
        else if (count < MAX_PAGE_COUNT * -1)
            count = MAX_PAGE_COUNT * -1;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Feed> data = mMapper.getVideoList(simpleDateFormat.format(id), count);
        if (data != null && count > 0) {
            //todo:test
            Collections.reverse(data);
        }

        ResponseInfo<List<Feed>> result = new ResponseInfo<>();
        result.setData(data);
        return result;
    }

    /**
     * 获取视频详细信息，如视频地址
     * @param videoId
     * @return
     * @throws IOException
     */
    @GetMapping("api/getVideoDetail")
    public String getVideoDetail(@RequestParam(value = "videoId") String videoId) throws IOException {
        //todo:
        Request httpRequest = new Request.Builder()
                .url(getUrl(videoId))
                .build();
        Response httpResponse = mClient.newCall(httpRequest).execute();
        return httpResponse.body().string();
    }

    private static String getUrl(String videoId) {
        String result = "/video/urls/v/1/toutiao/mp4/" + videoId;
        result = result + "?r=" + String.valueOf(Math.random()).substring(2);
//		result="http://ib.365yg.com"+result+"s="+getCrc32(result, getTable());
        CRC32 crc32 = new CRC32();
        crc32.update(result.getBytes());
        result = "http://ib.365yg.com" + result + "&s=" + String.valueOf(crc32.getValue());
        return result;
    }
}
