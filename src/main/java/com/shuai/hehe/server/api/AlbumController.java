package com.shuai.hehe.server.api;

import com.shuai.hehe.server.entity.Feed;
import com.shuai.hehe.server.entity.PicInfo;
import com.shuai.hehe.server.mapper.FeedMapper;
import com.shuai.hehe.server.response.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 */
@RestController
public class AlbumController {
    @Autowired
    private FeedMapper mMapper;

    private static final int MAX_PAGE_COUNT = 50;

    @GetMapping("api/getAlbumList")
    public ResponseInfo<List<Feed>> getAlbumList(
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
        List<Feed> data = mMapper.getAlbumList(simpleDateFormat.format(id), count);
        if (data != null && count > 0) {
            //todo:test
            Collections.reverse(data);
        }

        ResponseInfo<List<Feed>> result = new ResponseInfo<>();
        result.setData(data);
        return result;
    }

    @GetMapping("api/getAlbumPics")
    public ResponseInfo<List<PicInfo>> getAlbumPics(@RequestParam(value = "feedId") int feedId) {
        ResponseInfo<List<PicInfo>> result = new ResponseInfo<>();
        List<PicInfo> data = mMapper.getAlbumPics(feedId);
        result.setData(data);
        return result;
    }
}
