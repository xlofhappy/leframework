package com.qweather.leframework.base.file.service.po;

import com.qweather.leframework.model.po.LePo;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
public class FileEntity extends LePo {

    /**
     * file source name
     */
    private String sourceName;
    /**
     * file name
     */
    private String name;
    /**
     * file path
     */
    private String path;
    /**
     * file size
     */
    private Long size;
    /**
     * file extensions
     */
    private String ext;
    /**
     * 文件内容
     */
    private String data;

    public FileEntity() {
        super();
    }

    public FileEntity(Long id) {
        super(id);
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

}