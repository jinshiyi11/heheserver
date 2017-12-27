package com.shuai.hehe.server.mapper;

import com.shuai.hehe.server.entity.FavFeed;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *
 */
@Mapper
public interface FavMapper {
    /**
     * 是否已收藏
     *
     * @param userId
     * @param feedId
     * @return
     */
    @Select("SELECT EXISTS(SELECT 1 FROM fav WHERE uid=#{userId} AND feed_id=#{feedId})")
    boolean exists(@Param("userId") long userId, @Param("feedId") int feedId);

    @Insert("INSERT INTO fav(uid,feed_id,type,title,content,`from`) VALUES(" +
            "#{favFeed.mUserId},#{favFeed.mId},#{favFeed.mType},#{favFeed.mTitle},#{favFeed.mContent},#{favFeed.mFrom})")
    @Options(useGeneratedKeys=true, keyProperty="mFavId",keyColumn="id")
    void addFav(@Param("favFeed") FavFeed favFeed);

    @Delete("DELETE FROM fav WHERE uid=#{userId} AND feed_id=#{feedId}")
    void removeFav(@Param("userId") long userId, @Param("feedId") int feedId);

    @SelectProvider(type = FavMapper.SqlBuilder.class, method = "buildSelectSql")
    @Results({
            @Result(id = true, property = "mFavId", column = "id"),
            @Result(property = "mUserId", column = "uid"),
            @Result(property = "mId", column = "feed_id"),
            @Result(property = "mType", column = "type"),
            @Result(property = "mTitle", column = "title"),
            @Result(property = "mContent", column = "content"),
            @Result(property = "mFrom", column = "from"),
    })
    List<FavFeed> getFavList(
            @Param("userId") long userId,
            @Param("favId") int favId,
            @Param("count") int count);

    class SqlBuilder {
        public String buildSelectSql(@Param("userId") long userId, @Param("favId") int favId, @Param("count") int count) {
            String sql = "SELECT * FROM fav WHERE uid=" + userId;

            if (count > 0) {
                sql += " AND id>" + favId + "";
            } else {
                sql += " AND id<" + favId + "";
            }

            sql = sql + " ORDER BY id " + (count < 0 ? " DESC " : " ASC ") + " LIMIT " + Math.abs(count);
            return sql;
        }
    }
}
