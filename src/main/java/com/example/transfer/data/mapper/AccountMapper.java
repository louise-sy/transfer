package com.example.transfer.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.transfer.data.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    @Select("select point from Account where id= #{id}")
    Integer findPoint(@Param("id") Integer id);

    @Update("update Account set point = point-#{point} where id=#{id} and point>=#{point}")
    Integer outPoint(@Param("id")Integer id, @Param("point")Integer point);

    @Update("update Account set point = point+#{point} where id=#{id}")
    Integer inPoint(@Param("id")Integer id, @Param("point")Integer point);

//    Integer out2(@Param("id")Integer id, @Param("point")Integer point);

    Integer out2(@Param("id")Integer id, @Param("point")Integer point);

    TradeResult out();

    Integer in2(@Param("id")Integer id, @Param("point")Integer point);


}
