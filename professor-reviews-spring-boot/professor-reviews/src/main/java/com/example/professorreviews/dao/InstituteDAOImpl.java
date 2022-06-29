package com.example.professorreviews.dao;

import com.example.professorreviews.model.Institute;
import com.example.professorreviews.model.InstituteCount;
import com.example.professorreviews.model.Lecturer;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("InstituteDAO")
public class InstituteDAOImpl implements InstituteDAO{

  public static final String SQL_SELECT_INSTITUTE_BY_ID =
      "select * from pr.institute where id = :id";

  public static final String SQL_SELECT_INSTITUTE_COUNT =
      "select count(*),city from pr.institute group by city";

  public static final String SQL_SELECT_INSTITUTE_BY_CITY_NAME =
      "select * from pr.institute where city = :city";

  public static final String SQL_SELECT_LECTURER_BY_INSTITUTE_ID =
      "select * from pr.lecturer where institute_id = :institute_id";

  public static final String SQL_SELECT_INSTITUTE_IDS =
      "select id from pr.institute order by id";

  public static final String SQL_SELECT_TOP_10_INSTITUTES =
      "select ins.*, avg(rev.useful_l) as useful_l, avg(rev.interesting_l) as interesting_l, "
          + "avg(rev.material) as material, avg(rev.commitment) as commitment,"
          + " avg(rev.communication) as communication, count(rev.id) "
          + "from pr.institute ins "
          + "left join pr.lecturer lec on lec.institute_id = ins.id "
          + "left join pr.review rev on rev.lecturer_id = lec.id "
          + "group by ins.id";

  public static final String SQL_INSERT_INSTITUTE =
      "insert into pr.institute "
          + "values (nextval('pr.pr_institute_id_seq'), :adress, :city, :name, :img_cover_src)";

  public static final String SQL_UPDATE_INSTITUTE_BY_ID =
      "update pr.institute" +
          " set adress = :adress, city = :city, name = :name" +
          " where id = :id";

  public static final String SQL_DELETE_INSTITUTE_BY_ID =
      "delete from pr.institute where id = :id";



  private static final Logger logger = LoggerFactory.getLogger(InstituteDAOImpl.class);

  private final NamedParameterJdbcTemplate njdbcTemplate;


  public static final RowMapper<Institute> MAPPER_SELECT_INSTITUTE = new RowMapper<Institute>() {
    @Override
    public Institute mapRow(ResultSet rs, int rowNum) throws SQLException {

      Institute institute = new Institute(
          rs.getLong("id"),
          rs.getString("name"),
          rs.getString("Adress"),
          rs.getString("City"),
          rs.getString("img_cover_src"),
          null,
          null,
          new ArrayList<>()
      );
      return institute;
    }
  };

  public static final RowMapper<Institute> MAPPER_SELECT_INSTITUTE_TOP_10 = new RowMapper<Institute>() {
    @Override
    public Institute mapRow(ResultSet rs, int rowNum) throws SQLException {

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
          rs.getLong("id"),
          rs.getString("name"),
          rs.getString("Adress"),
          rs.getString("City"),
          rs.getString("img_cover_src"),
          averageRate,
          rs.getInt("count"),
          new ArrayList<>()
      );
      return institute;
    }
  };

  public static final RowMapper<InstituteCount> MAPPER_SELECT_INSTITUTE_COUNT = new RowMapper<InstituteCount>() {
    @Override
    public InstituteCount mapRow(ResultSet rs, int rowNum) throws SQLException {

      InstituteCount instituteCount = new InstituteCount(
          rs.getInt("count"),
          rs.getString("City")
      );
      return instituteCount;
    }
  };

  public static final RowMapper<Lecturer> MAPPER_SELECT_LECTURER = new RowMapper<Lecturer>() {
    @Override
    public Lecturer mapRow(ResultSet rs, int rowNum) throws SQLException {
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
      return lecturer;
    }
  };

  public static final RowMapper<Long> MAPPER_SELECT_IDS = new RowMapper<Long>() {

    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
      return rs.getLong("id");
    }
  };



  @Autowired
  public InstituteDAOImpl(
      NamedParameterJdbcTemplate njdbcTemplate) {
    this.njdbcTemplate = njdbcTemplate;
  }




  @Override
  public int insertInstitute(Long id, Institute institute) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    List<Institute> allInstitutes = getAllInstitutes();

    //Check if user exists
    for (Institute ins : allInstitutes) {
      if (ins.getName().equals(institute.getName())) {
        logger.debug("Institute already exists ", institute.getName());
        return 0;
      }
    }

    MapSqlParameterSource params = mapParams(institute);
    int rowCnt = njdbcTemplate.update(SQL_INSERT_INSTITUTE, params, keyHolder, new String[] {"id"});

    return rowCnt;
  }

  @Override
  public List<Institute> getAllInstitutes() {
    List<Long> instituteIds = njdbcTemplate.query(SQL_SELECT_INSTITUTE_IDS, MAPPER_SELECT_IDS);

    List<Institute> res = new ArrayList<Institute>();

    if (instituteIds==null || instituteIds.isEmpty()) {
      logger.debug("Could not find any institutes.");
      return res;
    }

    for (Long instituteId: instituteIds) {
      Institute institute = selectInstituteById(instituteId);
      if (instituteId!=null) {
        res.add(institute);
      }
    }

    return res;
  }

  @Override
  public List<InstituteCount> getInstitutesCount() {
    List<InstituteCount> instituteCounts = njdbcTemplate.query(SQL_SELECT_INSTITUTE_COUNT, MAPPER_SELECT_INSTITUTE_COUNT);

    if (instituteCounts==null || instituteCounts.isEmpty()) {
      logger.debug("Could not find any institutes.");
      return null;
    }

    return instituteCounts;
  }

  @Override
  public Institute selectInstituteById(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    List<Institute> allInstitutes = njdbcTemplate.query(SQL_SELECT_INSTITUTE_BY_ID, params, MAPPER_SELECT_INSTITUTE);

    if (allInstitutes == null || allInstitutes.isEmpty()) {
      logger.debug("Could not find institutes with id {}.", id);
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (allInstitutes.size()>1) {
        logger.debug("Got more than 1 result for id {}.", id);
      }
    }

    Institute institute = allInstitutes.get(0);

    params = new MapSqlParameterSource();
    params.addValue("institute_id", id);

    List<Lecturer> lecturers = njdbcTemplate.query(SQL_SELECT_LECTURER_BY_INSTITUTE_ID, params, MAPPER_SELECT_LECTURER);
    institute = new Institute(institute.getId(), institute.getName(), institute.getAdress(), institute.getCity(), institute.getImg_cover_src(), null, null, lecturers);

    return institute;
  }

  @Transactional
  @Override
  public List<Institute> selectInstitutesByCityName(String city) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("city", city);

    List<Institute> allInstitutes = njdbcTemplate.query(SQL_SELECT_INSTITUTE_BY_CITY_NAME, params, MAPPER_SELECT_INSTITUTE);

    if (allInstitutes == null || allInstitutes.isEmpty()) {
      logger.debug("Could not find institutes with name {}.", city);
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (allInstitutes.size()>1) {
        logger.debug("Got more than 1 result for name {}.", city);
      }
    }

    List<Institute> institutesByCity = new ArrayList<>();


    for (Institute ins :allInstitutes) {
      params = new MapSqlParameterSource();
      params.addValue("institute_id", ins.getId());

      List<Lecturer> lecturers = njdbcTemplate.query(SQL_SELECT_LECTURER_BY_INSTITUTE_ID, params, MAPPER_SELECT_LECTURER);
      Institute institute = new Institute(ins.getId(), ins.getName(), ins.getAdress(), ins.getCity(), ins.getImg_cover_src(), null, null, lecturers);

      institutesByCity.add(institute);

    }


    return institutesByCity;
  }

  @Override
  public List<Institute> getTop10Institutes() {
    List<Institute> institutes = njdbcTemplate.query(SQL_SELECT_TOP_10_INSTITUTES, MAPPER_SELECT_INSTITUTE_TOP_10);

    if (institutes==null || institutes.isEmpty()) {
      logger.debug("Could not find any institutes.");
      return null;
    }

    return institutes;
  }

  @Override
  public Institute updateInstituteById(Long id, Institute institute) {
    Institute existingInstitute = selectInstituteById(id);

    List<Institute> allInstitutes = getAllInstitutes();

    //Check if institute exists
    if(!existingInstitute.getName().equals(institute.getName())) {
      for (Institute ins : allInstitutes) {
        if (ins.getName().equals(institute.getName())) {
          logger.debug("Institute already exists", institute.getName());
          return null;
        }
      }
    }

    MapSqlParameterSource params = mapParams(institute);
    params.addValue("id", id);

    njdbcTemplate.update(SQL_UPDATE_INSTITUTE_BY_ID, params);

    return selectInstituteById(id);
  }

  @Override
  public int deleteInstituteByid(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    logger.debug("Deleting institute with id {}.", id);

    return njdbcTemplate.update(SQL_DELETE_INSTITUTE_BY_ID, params);
  }

  private static MapSqlParameterSource mapParams(Institute institute) {
    MapSqlParameterSource params = new MapSqlParameterSource();

    // id is disregarded for inserts and can also be missing from object
    if (institute.getId()!=null) {
      params.addValue("id", institute.getId());
    }

    params.addValue("name", institute.getName());
    params.addValue("adress", institute.getAdress());
    params.addValue("city", institute.getCity());
    params.addValue("img_cover_src", institute.getImg_cover_src());

    return params;
  }
}
