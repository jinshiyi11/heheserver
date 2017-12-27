package com.shuai.hehe.server.api;


import com.shuai.hehe.server.entity.FavFeed;
import com.shuai.hehe.server.entity.Feed;
import com.shuai.hehe.server.entity.FeedType;
import com.shuai.hehe.server.mapper.FavMapper;
import com.shuai.hehe.server.mapper.FeedMapper;
import com.shuai.hehe.server.response.ErrorCode;
import com.shuai.hehe.server.response.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
public class FavController {
    @Autowired
    private FavMapper mMapper;

    @Autowired
    private FeedMapper mFeedMapper;

    @PostMapping("/api/addFav")
    @ResponseBody
    public ResponseInfo<Integer> addFav(int feedId, int feedType) {
        long userId = Utils.getUserId();
        if (mMapper.exists(userId, feedId)) {
            return new ResponseInfo(ErrorCode.ERROR_FAV_EXIST);
        }

        Feed feed = null;
        if (feedType == FeedType.TYPE_ALBUM) {
            feed = mFeedMapper.getAlbumFeed(feedId);
        } else if (feedType == FeedType.TYPE_VIDEO) {
            feed = mFeedMapper.getVideoFeed(feedId);
        }
        FavFeed favFeed = new FavFeed(feed);
        favFeed.setUserId(userId);
        mMapper.addFav(favFeed);
        ResponseInfo<Integer> result = new ResponseInfo<Integer>(ErrorCode.ERROR_SUCCESS);
        result.setData(favFeed.getFavId());
        return result;
    }

    @RequestMapping("/api/removeFav")
    @ResponseBody
    public ResponseInfo removeFav(int feedId) {
        long userId = Utils.getUserId();
        mMapper.removeFav(userId, feedId);
        return new ResponseInfo(ErrorCode.ERROR_SUCCESS);
    }

    @RequestMapping("api/getFavList")
    public ResponseInfo<List<FavFeed>> getFavList(
            @RequestParam(value = "favId", required = false, defaultValue = "" + Integer.MAX_VALUE) int favId,
            @RequestParam(value = "count", required = false, defaultValue = "-20") int count) {
        long userId = Utils.getUserId();
        if (count > 200)
            count = 200;
        else if (count < -200)
            count = -200;
        List<FavFeed> data = mMapper.getFavList(userId, favId, count);

        ResponseInfo<List<FavFeed>> result = new ResponseInfo<>();
        result.setData(data);
        return result;
    }
}
