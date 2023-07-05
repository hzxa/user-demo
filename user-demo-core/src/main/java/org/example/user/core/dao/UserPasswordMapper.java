package org.example.user.core.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.example.user.core.domain.UserPassword;
import org.example.user.core.domain.UserPasswordExample;

public interface UserPasswordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    long countByExample(UserPasswordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int deleteByExample(UserPasswordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int insert(UserPassword row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int insertSelective(UserPassword row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    List<UserPassword> selectByExampleWithRowbounds(UserPasswordExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    List<UserPassword> selectByExample(UserPasswordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    UserPassword selectByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int updateByExampleSelective(@Param("row") UserPassword row, @Param("example") UserPasswordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int updateByExample(@Param("row") UserPassword row, @Param("example") UserPasswordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int updateByPrimaryKeySelective(UserPassword row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_passwords
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int updateByPrimaryKey(UserPassword row);
}