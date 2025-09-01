package com.ruoyi.volunteer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.volunteer.domain.EmailSendLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmailSendLogMapper extends BaseMapper<EmailSendLog> {

    List<SysUser> emailUsers();

}
