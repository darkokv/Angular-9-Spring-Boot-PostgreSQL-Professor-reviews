package com.example.professorreviews.dao;

import com.example.professorreviews.model.Review;
import com.example.professorreviews.model.ReviewLike;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("ReviewLikeDAO")
public class ReviewLikeDAOImpl implements ReviewLikeDAO {

  public static final String SQL_SELECT_REVIEW_LIKES_BY_REVIEW_ID = "select sum(like_::int) as likes,sum(dislike_ ::int) as dislikes "
      + "from  pr.review_like where review_id = :review_id";

  public static final String SQL_SELECT_REVIEW_LIKE_BY_USER_ID_AND_REVIEW_ID =
      "select * from pr.review_like where user_id = :user_id and review_id = :review_id";

  private static final String SQL_SELECT_REVIEW_LIKES_BY_USER_ID =
      "select * from pr.review_like where user_id = :user_id";

  public static final String SQL_INSERT_REVIEW_LIKE = "insert into pr.review_like "
      + "values (:user_id, :review_id, :like, :dislike)";

  public static final String SQL_UPDATE_REVIEW_LIKE =
      "update pr.review_like" +
          " set like_ = :like, dislike_ = :dislike "
          + "where user_id = :user_id and review_id = :review_id";

  public static final String SQL_DELETE_REVIEW_LIKE =
      "delete from pr.review_like where user_id = :user_id and review_id = :review_id";


  private static final Logger logger = LoggerFactory.getLogger(ReviewLikeDAOImpl.class);

  private final NamedParameterJdbcTemplate njdbcTemplate;



  public static final RowMapper<ReviewLike> MAPPER_SELECT_REVIEW_LIKE = new RowMapper<ReviewLike>() {
    @Override
    public ReviewLike mapRow(ResultSet rs, int rowNum) throws SQLException {
      ReviewLike reviewLike = new ReviewLike(
          null,
          null,
          null,
          rs.getInt("likes"),
          null,
          rs.getInt("dislikes")
      );
      return reviewLike;
    }
  };

  public static final RowMapper<ReviewLike> MAPPER_SELECT_REVIEW_LIKE_FULL = new RowMapper<ReviewLike>() {
    @Override
    public ReviewLike mapRow(ResultSet rs, int rowNum) throws SQLException {
      ReviewLike reviewLike = new ReviewLike(
          rs.getLong("user_id"),
          rs.getLong("review_id"),
          rs.getBoolean("like_"),
          null,
          rs.getBoolean("dislike_"),
          null
      );
      return reviewLike;
    }
  };




  public ReviewLikeDAOImpl(
      NamedParameterJdbcTemplate njdbcTemplate) {
    this.njdbcTemplate = njdbcTemplate;
  }


  @Override
  public int insertReviewLike(ReviewLike reviewLike) {
    MapSqlParameterSource params = mapParams(reviewLike);

    int rowCnt = njdbcTemplate.update(SQL_INSERT_REVIEW_LIKE, params);

    return rowCnt;
  }


  @Transactional
  @Override
  public ReviewLike getAllReviewLikesByReviewId(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("review_id", id);

    List<ReviewLike> reviewLikes = njdbcTemplate.query(SQL_SELECT_REVIEW_LIKES_BY_REVIEW_ID, params, MAPPER_SELECT_REVIEW_LIKE);

    if (reviewLikes==null || reviewLikes.isEmpty()) {
      logger.debug("Could not find reivew likes with id {}.", id);
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (reviewLikes.size()>1) {
        logger.debug("Got more than 1 result for id {}.", id);
      }
    }

    ReviewLike reviewLike = reviewLikes.get(0);

    return reviewLike;
  }

  @Transactional
  @Override
  public ReviewLike selectReviewLike(Long user_id, Long review_id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("user_id", user_id);
    params.addValue("review_id", review_id);

    List<ReviewLike> allReviewLikes = njdbcTemplate.query(SQL_SELECT_REVIEW_LIKE_BY_USER_ID_AND_REVIEW_ID, params, MAPPER_SELECT_REVIEW_LIKE_FULL);

    if (allReviewLikes==null || allReviewLikes.isEmpty()) {
      logger.debug("Could not find reivew with ids {}.");
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (allReviewLikes.size()>1) {
        logger.debug("Got more than 1 result for ids {}.");
      }
    }

    ReviewLike reviewLike = allReviewLikes.get(0);

    return reviewLike;
  }

  @Transactional
  @Override
  public List<ReviewLike> selectReviewLikesByUserId(Long user_id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("user_id", user_id);

    List<ReviewLike> allReviewLikes = njdbcTemplate.query(SQL_SELECT_REVIEW_LIKES_BY_USER_ID, params, MAPPER_SELECT_REVIEW_LIKE_FULL);

    if (allReviewLikes==null || allReviewLikes.isEmpty()) {
      logger.debug("Could not find reivew with id {}.");
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (allReviewLikes.size()>1) {
        logger.debug("Got more than 1 result for id {}.");
      }
    }

    return allReviewLikes;
  }

  @Transactional
  @Override
  public int updateReviewLike(ReviewLike reviewLike) {
    MapSqlParameterSource params = mapParams(reviewLike);

    int rowCnt = njdbcTemplate.update(SQL_UPDATE_REVIEW_LIKE, params);

    return rowCnt;
  }

  @Transactional
  @Override
  public int deleteReviewLike(Long user_id, Long review_id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("user_id", user_id);
    params.addValue("review_id", review_id);

    logger.debug("Deleting review with ids {}.", user_id, user_id);

    return njdbcTemplate.update(SQL_DELETE_REVIEW_LIKE, params);
  }

  private static MapSqlParameterSource mapParams(ReviewLike reviewLike) {
    MapSqlParameterSource params = new MapSqlParameterSource();

    params.addValue("user_id", reviewLike.getUser_id());
    params.addValue("review_id", reviewLike.getReview_id());
    params.addValue("like", reviewLike.getLikeInput());
    params.addValue("dislike", reviewLike.getDislikeInput());

    return params;
  }
}
