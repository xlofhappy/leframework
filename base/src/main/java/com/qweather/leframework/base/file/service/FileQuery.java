package com.qweather.leframework.base.file.service;

import com.qweather.leframework.base.file.service.po.FileEntity;
import com.qweather.leframework.model.IdQuery;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
public interface FileQuery extends IdQuery<FileQuery, FileEntity> {

    FileQuery sourceName(String sourceName);

    FileQuery name(String name);

    FileQuery path(String path);

    FileQuery ext(String ext);

    FileQuery data(String data);

    FileQuery orderBySourceName(Direction direction);

    FileQuery orderByName(Direction direction);

    FileQuery orderByPath(Direction direction);

    FileQuery orderBySize(Direction direction);

    FileQuery orderByExt(Direction direction);

}
