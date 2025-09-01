package com.ruoyi.volunteer.mapper;

import java.util.List;

import com.ruoyi.volunteer.domain.File;

/**
 * 文件Mapper接口
 *
 * @author ruoyi
 * @date 2025-01-17
 */
public interface FileMapper {
    /**
     * 查询文件
     *
     * @param fileId 文件主键
     * @return 文件
     */
    public File selectFileById(Long fileId);

    /**
     * 查询文件列表
     *
     * @param file 文件
     * @return 文件集合
     */
    public List<File> selectFileList(File file);

    /**
     * 新增文件
     *
     * @param file 文件
     * @return 结果
     */
    public int insertFile(File file);

    /**
     * 修改文件
     *
     * @param file 文件
     * @return 结果
     */
    public int updateFile(File file);

    /**
     * 删除文件
     *
     * @param fileId 文件主键
     * @return 结果
     */
    public int deleteFileById(Long fileId);

    /**
     * 批量删除文件
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFileByIds(Long[] ids);
}
