package com.example.professorreviews.dao;

import com.example.professorreviews.model.Institute;
import com.example.professorreviews.model.Lecturer;
import com.example.professorreviews.model.Review;
import com.example.professorreviews.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.decimal4j.util.DoubleRounder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("LecturerDAO")
public class LecturerDAOImpl implements LecturerDAO {

  public static final String SQL_SELECT_LECTURER_AND_INSTITUTE_BY_LECTURER_ID =
      "select lec.*, ins.id as ins_id, ins.name, ins.adress, ins.city, ins.img_cover_src, count(rev.id) as cntreviews "
          + "from pr.lecturer lec "
          + "left join pr.review rev on rev.lecturer_id = lec.id "
          + "join pr.institute ins on lec.institute_id = ins.id "
          + "and lec.id = :id group by lec.id, ins.id";

  public static final String SQL_SELECT_REVIEW_BY_LECTURER_ID =
      "select rev.*, usr.id as u_id, usr.fullname, usr.email, usr.username, usr.password, usr.role, sum(revl.like_::int) as likes,sum(revl.dislike_ ::int) as dislikes"
          + " from pr.review rev "
          + "join pr.user usr on rev.user_id = usr.id "
          + "left join pr.review_like revl on revl.review_id = rev.id "
          + "where rev.lecturer_id = :lecturer_id "
          + "group by rev.id, usr.id order by rev.date asc";

  public static final String SQL_SELECT_LECTURER_IDS =
      "select id from pr.lecturer order by id";

  public static final String SQL_INSERT_LECTURER =
      "insert into pr.lecturer (id, fullname, email, img_src, institute_id)" +
          " values (nextval('pr.pr_lecturer_id_seq'), :fullname, :email, :img_src, :institute_id)";

  public static final String SQL_UPDATE_LECTURER_BY_ID =
      "update pr.lecturer" +
          " set fullname = :fullname, email = :email, img_src = :img_src, institute_id = :institute_id" +
          " where id = :id";

  public static final String SQL_DELETE_LECTURER_BY_ID =
      "delete from pr.lecturer where id = :id";

  public static final String SQL_SEARCH_LECTURER =
      "select lec.*, ins.id as ins_id, ins.name, ins.adress, ins.city, ins.img_cover_src, count(rev.id) as cntreviews "
          + "from pr.lecturer lec "
          + "left join pr.review rev on rev.lecturer_id = lec.id "
          + "join pr.institute ins on lec.institute_id = ins.id "
          + "and lower(lec.fullname) like :keyword group by lec.id, ins.id;";

  public static final String SQL_SELECT_TOP_10_LECTURERS =
      "select lec.*, ins.id as ins_id, ins.name, ins.adress, ins.city, ins.img_cover_src, "
          + "avg(rev.useful_l) as useful_l, avg(rev.interesting_l) as interesting_l, "
          + "avg(rev.material) as material, avg(rev.commitment) as commitment, "
          + "avg(rev.communication) as communication, count(rev.id) "
          + "from pr.lecturer lec "
          + "join pr.institute ins on ins.id = lec.institute_id "
          + "left join pr.review rev on lec.id = rev.lecturer_id "
          + "group by lec.id, ins.id";

  private static final Logger logger = LoggerFactory.getLogger(LecturerDAOImpl.class);

  private final NamedParameterJdbcTemplate njdbcTemplate;

  public static final RowMapper<Lecturer> MAPPER_SELECT_LECTURER = new RowMapper<Lecturer>() {
    @Override
    public Lecturer mapRow(ResultSet rs, int rowNum) throws SQLException {
      Institute institute = new Institute(
          rs.getLong("institute_id"),
          rs.getString("name"),
          rs.getString("adress"),
          rs.getString("city"),
          rs.getString("img_cover_src"),
          null,
          null,
          null
      );

      Lecturer lecturer = new Lecturer(
          rs.getLong("id"),
          rs.getString("fullname"),
          rs.getString("email"),
          rs.getString("img_src"),
          null,
          rs.getInt("cntreviews"),
          rs.getLong("institute_id"),
          institute,
          new ArrayList<>()
      );
      return lecturer;
    }
  };

  public static final RowMapper<Lecturer> MAPPER_SELECT_LECTURER_TOP_10 = new RowMapper<Lecturer>() {
    @Override
    public Lecturer mapRow(ResultSet rs, int rowNum) throws SQLException {

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

      Double averageRate = DoubleRounder.round(sum, 1);

      Institute institute = new Institute(
          rs.getLong("institute_id"),
          rs.getString("name"),
          rs.getString("adress"),
          rs.getString("city"),
          rs.getString("img_cover_src"),
          null,
          null,
          null
      );

      Lecturer lecturer = new Lecturer(
          rs.getLong("id"),
          rs.getString("fullname"),
          rs.getString("email"),
          rs.getString("img_src"),
          averageRate,
          rs.getInt("count"),
          rs.getLong("institute_id"),
          institute,
          new ArrayList<>()
      );
      return lecturer;
    }
  };


  public static final RowMapper<Review> MAPPER_SELECT_REVIEW = new RowMapper<Review>() {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User(
          rs.getLong("u_id"),
          rs.getString("fullname"),
          rs.getString("email"),
          rs.getString("username"),
          rs.getString("password"),
          rs.getString("role")
      );

      Review review = new Review(
          rs.getLong("id"),
          rs.getLong("user_id"),
          user,
          rs.getLong("lecturer_id"),
          null,
          rs.getInt("useful_l"),
          rs.getInt("interesting_l"),
          rs.getInt("material"),
          rs.getInt("commitment"),
          rs.getInt("communication"),
          rs.getString("comment"),
          rs.getString("date"),
          rs.getInt("likes"),
          rs.getInt("dislikes")
      );
      return review;
    }
  };

  public static final RowMapper<Long> MAPPER_SELECT_IDS = new RowMapper<Long>() {

    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
      return rs.getLong("id");
    }
  };

  @Autowired
  public LecturerDAOImpl(
      NamedParameterJdbcTemplate njdbcTemplate) {
    this.njdbcTemplate = njdbcTemplate;
  }



  @Transactional
  @Override
  public int insertLecturer(Long id, Lecturer lecturer) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    List<Lecturer> allLecturers = getAllLecturers();

    //Check if user exists
    for (Lecturer lec : allLecturers) {
      if (lec.getFullname().equals(lecturer.getFullname())) {
        logger.debug("Lecturer already exists ", lecturer.getFullname());
        return 0;
      }
    }

    MapSqlParameterSource params = mapParams(lecturer);
    int rowCnt = njdbcTemplate.update(SQL_INSERT_LECTURER, params, keyHolder, new String[] {"id"});

    return rowCnt;
  }

  @Transactional
  @Override
  public List<Lecturer> getAllLecturers() {
    List<Long> lecturerIds = njdbcTemplate.query(SQL_SELECT_LECTURER_IDS, MAPPER_SELECT_IDS);

    List<Lecturer> res = new ArrayList<Lecturer>();

    if (lecturerIds==null || lecturerIds.isEmpty()) {
      logger.debug("Could not find any lecturer.");
      return res;
    }

    for (Long lecturerId: lecturerIds) {
      Lecturer lecturer = selectLecturerById(lecturerId);
      if (lecturerId!=null) {
        res.add(lecturer);
      }
    }

    return res;
  }

  @Transactional
  @Override
  public Lecturer selectLecturerById(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    List<Lecturer> lecturers = njdbcTemplate.query(SQL_SELECT_LECTURER_AND_INSTITUTE_BY_LECTURER_ID, params, MAPPER_SELECT_LECTURER);

    if(lecturers == null || lecturers.isEmpty()) {
      logger.debug("Could not find lecturer with id {}.", id);
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (lecturers.size()>1) {
        logger.debug("Got more than 1 result for id {}.", id);
      }
    }

    Lecturer lecturer = lecturers.get(0);

    return lecturer;
  }

  @Transactional
  @Override
  public Lecturer selectLecturerWithReviewsById(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    List<Lecturer> lecturers = njdbcTemplate.query(SQL_SELECT_LECTURER_AND_INSTITUTE_BY_LECTURER_ID, params, MAPPER_SELECT_LECTURER);

    if(lecturers == null) {
      logger.debug("Could not find lecturer with id {}.", id);
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (lecturers.size()>1) {
        logger.debug("Got more than 1 result for id {}.", id);
      }
    }

    Lecturer lecturer = lecturers.get(0);

    params = new MapSqlParameterSource();
    params.addValue("lecturer_id", id);

    List<Review> reviews = njdbcTemplate.query(SQL_SELECT_REVIEW_BY_LECTURER_ID, params, MAPPER_SELECT_REVIEW);
    lecturer = new Lecturer(lecturer.getId(), lecturer.getFullname(), lecturer.getEmail(), lecturer.getImg_src(), null, lecturer.getRatesCount(), lecturer.getInstitute_id(), lecturer.getInstitute(), reviews);

    return lecturer;
  }

  @Transactional
  @Override
  public List<Lecturer> searchLecturer(String keyword) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    keyword = keyword.toLowerCase().trim();
    params.addValue("keyword", '%'+keyword+'%');

    List<Lecturer> lecturers = njdbcTemplate.query(SQL_SEARCH_LECTURER, params, MAPPER_SELECT_LECTURER);

    if(lecturers == null || lecturers.isEmpty()) {
      logger.debug("Could not find lecturer with keyword {}.", keyword);
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (lecturers.size()>1) {
        logger.debug("Got more than 1 result for id {}.", keyword);
      }
    }

    return lecturers;
  }

  @Override
  public List<Lecturer> getTop10Lecturers() {
    List<Lecturer> lecturers = njdbcTemplate.query(SQL_SELECT_TOP_10_LECTURERS, MAPPER_SELECT_LECTURER_TOP_10);

    if (lecturers==null || lecturers.isEmpty()) {
      logger.debug("Could not find any lecturers.");
      return null;
    }

    return lecturers;
  }

  @Transactional
  @Override
  public Lecturer updateLecturerById(Long id, Lecturer lecturer) {
    Lecturer existingLecturer = selectLecturerById(id);

    List<Lecturer> allLecturers = getAllLecturers();

    //Check if lecturer exists
    if(!existingLecturer.getFullname().equals(lecturer.getFullname())) {
      for (Lecturer lec : allLecturers) {
        if (lec.getFullname().equals(lecturer.getFullname())) {
          logger.debug("Lecturer already exists", lecturer.getFullname());
          return null;
        }
      }
    }

    MapSqlParameterSource params = mapParams(lecturer);
    params.addValue("id", id);

    njdbcTemplate.update(SQL_UPDATE_LECTURER_BY_ID, params);

    return selectLecturerById(id);
  }

  @Transactional
  @Override
  public int deleteLecturerById(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    logger.debug("Deleting lecturer with id {}.", id);

    return njdbcTemplate.update(SQL_DELETE_LECTURER_BY_ID, params);
  }

  private static MapSqlParameterSource mapParams(Lecturer lecturer) {
    MapSqlParameterSource params = new MapSqlParameterSource();

    // id is disregarded for inserts and can also be missing from object
    if (lecturer.getId()!=null) {
      params.addValue("id", lecturer.getId());
    }

    params.addValue("fullname", lecturer.getFullname());
    params.addValue("email", lecturer.getEmail());
    params.addValue("img_src", lecturer.getImg_src());
    params.addValue("institute_id", lecturer.getInstitute_id());

    return params;
  }
}
