package com.example.professorreviews.dao;

import com.example.professorreviews.model.AverageRates;
import com.example.professorreviews.model.Institute;
import com.example.professorreviews.model.Lecturer;
import com.example.professorreviews.model.Review;
import com.example.professorreviews.model.ReviewLike;
import com.example.professorreviews.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.InternalServerErrorException;
import org.decimal4j.util.DoubleRounder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("ReviewDAO")
public class ReviewDAOImpl implements ReviewDAO {

  public static final String SQL_SELECT_REVIEW_BY_ID =
      "select rev.*, usr.id, usr.fullname as u_fullname, usr.email as u_email, usr.username,"
          + " usr.password, usr.role, lec.* from pr.review rev, pr.user usr, pr.lecturer lec "
          + "where rev.user_id = usr.id and rev.lecturer_id = lec.id and rev.id = :id";

  public static final String SQL_SELECT_REVIEW_BY_LECTURER_ID =
      "select pr.review.* from review where lecturer_id = :lecturer_id";

  public static final String SQL_SELECT_AVERAGE_RATES_BY_INSTITUTE_ID =
      "select avg(rev.useful_l) as useful_l , avg(rev.interesting_l) as interesting_l, avg(rev.material) as material, "
          + "avg(rev.commitment) as commitment, avg(rev.communication) as communication "
          + "from pr.review rev "
          + "join pr.lecturer lec on lec.id = rev.lecturer_id join "
          + "pr.institute ins on ins.id = lec.institute_id and ins.id = :id group by lec.id";

  public static final String SQL_SELECT_NUMBER_OF_RATES_BY_INSTITUTE_ID =
      "select count(*) "
          + "from pr.review rev "
          + "join pr.lecturer lec on lec.id = rev.lecturer_id "
          + "join pr.institute ins on ins.id = lec.institute_id "
          + "and ins.id = :id";

  public static final String SQL_INSERT_REVIEW = "insert into pr.review values"
      + " (nextval('pr.pr_review_id_seq'), :user_id, :lecturer_id, :useful_l, :interesting_l, :material, "
      + ":commitment, :communication, :comment, :date )";

  public static final String SQL_SELECT_AVERAGE_RATE_BY_LECTURER_ID  =
      "select avg(useful_l) as useful_l , avg(interesting_l) as interesting_l, avg(material) as material,"
          + "  avg(commitment) as commitment, avg(communication) as communication, count(*) "
          + "from pr.review where lecturer_id = :id";

  public static final String SQL_UPDATE_REVIEW_BY_ID =
      "update pr.review" +
          " set user_id = :user_id, lecturer_id = :lecturer_id, useful_l = :useful_l, "
          + "interesting_l = :interesting_l, material = :material, commitment = :commitment, "
          + "communication = :communication, comment = :comment, date = :date" +
          " where id = :id";

  public static final String SQL_DELETE_REVIEW_BY_ID =
      "delete from pr.review where id = :id";







  private static final Logger logger = LoggerFactory.getLogger(ReviewDAOImpl.class);

  private final NamedParameterJdbcTemplate njdbcTemplate;

  public static final RowMapper<Review> MAPPER_SELECT_REVIEW = new RowMapper<Review>() {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User(
          rs.getLong("id"),
          rs.getString("u_fullname"),
          rs.getString("u_email"),
          rs.getString("username"),
          rs.getString("password"),
          rs.getString("role")
      );

      Lecturer lecturer = new Lecturer(
          rs.getLong("id"),
          rs.getString("fullname"),
          rs.getString("email"),
          rs.getString("img_src"),
          null,
          null,
          rs.getLong("institute_id"),
          null,
          null
      );

      Review review = new Review(
          rs.getLong("id"),
          rs.getLong("user_id"),
          user,
          rs.getLong("lecturer_id"),
          lecturer,
          rs.getInt("useful_l"),
          rs.getInt("interesting_l"),
          rs.getInt("material"),
          rs.getInt("commitment"),
          rs.getInt("communication"),
          rs.getString("comment"),
          rs.getString("date"),
          null,
          null
      );
      return review;
    }
  };

  public static final RowMapper<Review> MAPPER_SELECT_REVIEW_SIMPLE = new RowMapper<Review>() {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
      Review review = new Review(
          rs.getLong("id"),
          rs.getLong("user_id"),
          null,
          rs.getLong("lecturer_id"),
          null,
          rs.getInt("useful_l"),
          rs.getInt("interesting_l"),
          rs.getInt("material"),
          rs.getInt("commitment"),
          rs.getInt("communication"),
          rs.getString("comment"),
          rs.getString("date"),
          null,
          null
      );
      return review;
    }
  };

  public static final RowMapper<Double> MAPEER_SELECT_AVERAGE_RATE = new RowMapper<Double>() {

    @Override
    public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
      ArrayList<Double> averageRates = new ArrayList<>();
      averageRates.add(rs.getDouble("useful_l"));
      averageRates.add(rs.getDouble("interesting_l"));
      averageRates.add(rs.getDouble("material"));
      averageRates.add(rs.getDouble("commitment"));
      averageRates.add(rs.getDouble("communication"));

      Double sum = 0.0;
      for (Double avg: averageRates) {
        sum = sum + avg;
      }

      sum = sum / averageRates.size();

      return DoubleRounder.round(sum, 1);
    }
  };

  public static final RowMapper<AverageRates> MAPEER_SELECT_AVERAGE_RATES = new RowMapper<AverageRates>() {

    @Override
    public AverageRates mapRow(ResultSet rs, int rowNum) throws SQLException {
      AverageRates averageRates = new AverageRates(
          DoubleRounder.round(rs.getDouble("useful_l"), 1),
          DoubleRounder.round(rs.getDouble("interesting_l"), 1),
          DoubleRounder.round(rs.getDouble("material"), 1),
          DoubleRounder.round(rs.getDouble("commitment"), 1),
          DoubleRounder.round(rs.getDouble("communication"), 1),
          rs.getInt("count")
      );

      return averageRates;
    }
  };

  public static final RowMapper<Integer> MAPPER_SELECT_NUMBER_OF_REVIEWS = new RowMapper<Integer>() {

    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
      Integer sum = rs.getInt("count");

      return sum;
    }
  };

  @Autowired
  public ReviewDAOImpl(
      NamedParameterJdbcTemplate njdbcTemplate) {
    this.njdbcTemplate = njdbcTemplate;
  }

  @Override
  public int insertReview(Long id, Review review) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    List<Review> allReviewsByLecturerId = getAllReviewsByLecturerId(review.getLecturer_id());



    //Check if review exists
    if(!allReviewsByLecturerId.isEmpty() || allReviewsByLecturerId != null) {
      for (Review rev : allReviewsByLecturerId) {
        if (rev.getUser_id().equals(review.getUser_id())) {
          logger.debug("You already review a lecturer ", review.getLecturer_id());
          throw new InternalServerErrorException("409");
        }
      }
    }

    MapSqlParameterSource params = mapParams(review);
    int rowCnt = njdbcTemplate.update(SQL_INSERT_REVIEW, params, keyHolder, new String[] {"id"});

    return rowCnt;
  }

  @Override
  public List<Review> getAllReviewsByLecturerId(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("lecturer_id", id);

    List<Review> allReviewsbyLecturerId = njdbcTemplate.query(SQL_SELECT_REVIEW_BY_LECTURER_ID, params, MAPPER_SELECT_REVIEW_SIMPLE);

    if (allReviewsbyLecturerId==null || allReviewsbyLecturerId.isEmpty()) {
      logger.debug("Could not find reivew with id {}.", id);
      //return null;
    }

    if (logger.isDebugEnabled()) {
      if (allReviewsbyLecturerId.size()>1) {
        logger.debug("Got more than 1 result for id {}.", id);
      }
    }

    return allReviewsbyLecturerId;
  }

  @Override
  public Review selectReviewById(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    List<Review> allReviews = njdbcTemplate.query(SQL_SELECT_REVIEW_BY_ID, params, MAPPER_SELECT_REVIEW);

    if (allReviews==null || allReviews.isEmpty()) {
      logger.debug("Could not find reivew with id {}.", id);
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (allReviews.size()>1) {
        logger.debug("Got more than 1 result for id {}.", id);
      }
    }

    Review reivew = allReviews.get(0);

    return reivew;
  }

  @Transactional
  @Override
  public Double getAverageRate(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    List<Double> averageRates = njdbcTemplate.query(SQL_SELECT_AVERAGE_RATE_BY_LECTURER_ID, params, MAPEER_SELECT_AVERAGE_RATE);

    if (averageRates == null || averageRates.isEmpty()) {
      return null;
    }

    Double averageRate = averageRates.get(0);

    return averageRate;
  }

  @Override
  public AverageRates getAverageRates(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    List<AverageRates> averageRates = njdbcTemplate.query(SQL_SELECT_AVERAGE_RATE_BY_LECTURER_ID, params, MAPEER_SELECT_AVERAGE_RATES);

    AverageRates averageRates_ = averageRates.get(0);

    return averageRates_;
  }

  @Override
  public Double getAverageRateByInstituteId(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    List<Double> averageRates = njdbcTemplate.query(SQL_SELECT_AVERAGE_RATES_BY_INSTITUTE_ID, params, MAPEER_SELECT_AVERAGE_RATE);

    Double avg = 0.0;
    for (Double avgRate: averageRates) {
      avg = avg + avgRate;
    }

    Double sum = avg / averageRates.size();

    return DoubleRounder.round(sum, 1);
  }

  @Override
  public Integer getNumberOfReviewsByInstituteId(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    List<Integer> numbers = njdbcTemplate.query(SQL_SELECT_NUMBER_OF_RATES_BY_INSTITUTE_ID, params, MAPPER_SELECT_NUMBER_OF_REVIEWS);

    Integer number = numbers.get(0);

    return number;
  }

  @Override
  public Review updateReviewById(Long id, Review review) {
    Review existingReview = selectReviewById(id);

    //block update if user id or lecturer id are different
    if(!existingReview.getUser_id().equals(review.getUser_id()) ||
       !existingReview.getLecturer_id().equals(review.getLecturer_id())) {
      logger.debug("Could not update review with different user id or lecturer id");
      return null;
    }

    MapSqlParameterSource params = mapParams(review);
    params.addValue("id", id);

    njdbcTemplate.update(SQL_UPDATE_REVIEW_BY_ID, params);

    return selectReviewById(id);
  }

  @Override
  public int deleteReviewByid(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    logger.debug("Deleting review with id {}.", id);

    return njdbcTemplate.update(SQL_DELETE_REVIEW_BY_ID, params);
  }


  private static MapSqlParameterSource mapParams(Review review) {
    MapSqlParameterSource params = new MapSqlParameterSource();

    // id is disregarded for inserts and can also be missing from object
    if (review.getId()!=null) {
      params.addValue("id", review.getId());
    }

    params.addValue("user_id", review.getUser_id());
    params.addValue("lecturer_id", review.getLecturer_id());
    params.addValue("useful_l", review.getUseful_l());
    params.addValue("interesting_l", review.getInteresting_l());
    params.addValue("material", review.getMaterial());
    params.addValue("commitment", review.getCommitment());
    params.addValue("communication", review.getCommunication());
    params.addValue("comment", review.getComment());
    params.addValue("date", review.getDate());

    return params;
  }
}
