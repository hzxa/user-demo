package org.example.user.core.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.example.user.core.domain.UserModel;
import org.example.user.core.domain.UserModelExample;

public interface UserModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    long countByExample(UserModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int deleteByExample(UserModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int insert(UserModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int insertSelective(UserModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    List<UserModel> selectByExampleWithRowbounds(UserModelExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    List<UserModel> selectByExample(UserModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    UserModel selectByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int updateByExampleSelective(@Param("row") UserModel row, @Param("example") UserModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int updateByExample(@Param("row") UserModel row, @Param("example") UserModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int updateByPrimaryKeySelective(UserModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_model
     *
     * @mbg.generated Sun Jul 02 16:40:50 CST 2023
     */
    int updateByPrimaryKey(UserModel row);
}