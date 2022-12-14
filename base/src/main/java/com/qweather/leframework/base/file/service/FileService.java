package com.qweather.leframework.base.file.service;

import com.qweather.leframework.base.file.service.po.FileEntity;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
public interface FileService {

    FileQuery createFileQuery();

    FileEntity createFile();

    void saveFile(FileEntity entity);

    void deleteFile(Long id);

    void deleteFileForever(Long id);

}
