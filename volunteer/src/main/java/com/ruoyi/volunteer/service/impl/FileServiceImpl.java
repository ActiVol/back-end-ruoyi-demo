package com.ruoyi.volunteer.service.impl;

import java.io.IOException;
import java.util.List;

import com.ruoyi.common.config.AmazonS3Config;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.volunteer.mapper.FileMapper;
import com.ruoyi.volunteer.domain.File;
import com.ruoyi.volunteer.service.IFileService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件Service业务层处理
 *
 * @author ruoyi
 * @date 2025-01-17
 */
@Service
public class FileServiceImpl implements IFileService {
    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileMapper fileMapper;

    /**
     * 查询文件
     *
     * @param id 文件主键
     * @return 文件
     */
    @Override
    public File selectFileById(Long id) {
        return fileMapper.selectFileById(id);
    }

    /**
     * 查询文件列表
     *
     * @param file 文件
     * @return 文件
     */
    @Override
    public List<File> selectFileList(File file) {
        return fileMapper.selectFileList(file);
    }

    /**
     * 新增文件
     *
     * @param file 文件
     * @return 结果
     */
    @Override
    public int insertFile(File file) {
        file.setCreateTime(DateUtils.getNowDate());
        return fileMapper.insertFile(file);
    }

    /**
     * 修改文件
     *
     * @param file 文件
     * @return 结果
     */
    @Override
    public int updateFile(File file) {
        file.setUpdateTime(DateUtils.getNowDate());
        return fileMapper.updateFile(file);
    }

    /**
     * 批量删除文件
     *
     * @param ids 需要删除的文件主键
     * @return 结果
     */
    @Override
    public int deleteFileByIds(Long[] ids) {
        return fileMapper.deleteFileByIds(ids);
    }

    /**
     * 删除文件信息
     *
     * @param id 文件主键
     * @return 结果
     */
    @Override
    public int deleteFileById(Long id) {
        File file = fileMapper.selectFileById(id);
        String path = file.getPath();
        try {
            AmazonS3Config.delFile(path);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return fileMapper.deleteFileById(id);
    }

    @Override
    public AjaxResult uploadImage(MultipartFile multipartFile) {
        try {
            // 上传并返回新文件名称
            String fileNameStr = FileUploadUtils.uploadMinio(multipartFile);
            String url = fileNameStr.split(",")[0];
            String fileName = fileNameStr.split(",")[1];
            String originalFilename = multipartFile.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
            long size = multipartFile.getSize();
            File file = new File();
            file.setCreateTime(DateUtils.getNowDate());
            file.setCreateBy(SecurityUtils.getUsername());
            file.setFileName(originalFilename);
            file.setType(extension);
            file.setSize(size);
            file.setPath(fileName);
            file.setUrl(url);
            fileMapper.insertFile(file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", multipartFile.getOriginalFilename());
            ajax.put("file",file);
            return ajax;
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }
    }


}
