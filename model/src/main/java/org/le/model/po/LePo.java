package org.le.model.po;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.le.model.BasePo;

/**
 * @author xiaole
 * @date 2018-07-25
 */
public class LePo extends BasePo {

    private static final long serialVersionUID = 1024L;

    private String remark;
    private Long authorId;
    private String authorTime;
    private Long modifyerId;
    private String modifyTime;
    private short status = 0;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean display = true;

    public LePo() {
        super();
        status = 0;
        display = true;
    }

    public LePo(Long id) {
        super(id);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorTime() {
        return authorTime;
    }

    public void setAuthorTime(String authorTime) {
        this.authorTime = authorTime;
    }

    public Long getModifyerId() {
        return modifyerId;
    }

    public void setModifyerId(Long modifyerId) {
        this.modifyerId = modifyerId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public boolean getDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }


}
