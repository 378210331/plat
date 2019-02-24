package com.hsy.platform.entity;

import java.sql.Date;

/**
 * 岗位
 */
public class TSysPos {
    private String posCode;
    private String posType;
    private String posName;
    private String posDesc;
    private String xgrCode;
    private Date xgrq;
    private String lrrCode;
    private Date lrrq;
    private String yxbz;

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getPosDesc() {
        return posDesc;
    }

    public void setPosDesc(String posDesc) {
        this.posDesc = posDesc;
    }

    public String getXgrCode() {
        return xgrCode;
    }

    public void setXgrCode(String xgrCode) {
        this.xgrCode = xgrCode;
    }

    public Date getXgrq() {
        return xgrq;
    }

    public void setXgrq(Date xgrq) {
        this.xgrq = xgrq;
    }

    public String getLrrCode() {
        return lrrCode;
    }

    public void setLrrCode(String lrrCode) {
        this.lrrCode = lrrCode;
    }

    public Date getLrrq() {
        return lrrq;
    }

    public void setLrrq(Date lrrq) {
        this.lrrq = lrrq;
    }
    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TSysPos tSysPos = (TSysPos) o;

        if (posCode != null ? !posCode.equals(tSysPos.posCode) : tSysPos.posCode != null) return false;
        if (posType != null ? !posType.equals(tSysPos.posType) : tSysPos.posType != null) return false;
        if (posName != null ? !posName.equals(tSysPos.posName) : tSysPos.posName != null) return false;
        if (posDesc != null ? !posDesc.equals(tSysPos.posDesc) : tSysPos.posDesc != null) return false;
        if (xgrCode != null ? !xgrCode.equals(tSysPos.xgrCode) : tSysPos.xgrCode != null) return false;
        if (xgrq != null ? !xgrq.equals(tSysPos.xgrq) : tSysPos.xgrq != null) return false;
        if (lrrCode != null ? !lrrCode.equals(tSysPos.lrrCode) : tSysPos.lrrCode != null) return false;
        if (lrrq != null ? !lrrq.equals(tSysPos.lrrq) : tSysPos.lrrq != null) return false;
        if (yxbz != null ? !yxbz.equals(tSysPos.yxbz) : tSysPos.yxbz != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = posCode != null ? posCode.hashCode() : 0;
        result = 31 * result + (posType != null ? posType.hashCode() : 0);
        result = 31 * result + (posName != null ? posName.hashCode() : 0);
        result = 31 * result + (posDesc != null ? posDesc.hashCode() : 0);
        result = 31 * result + (xgrCode != null ? xgrCode.hashCode() : 0);
        result = 31 * result + (xgrq != null ? xgrq.hashCode() : 0);
        result = 31 * result + (lrrCode != null ? lrrCode.hashCode() : 0);
        result = 31 * result + (lrrq != null ? lrrq.hashCode() : 0);
        result = 31 * result + (yxbz != null ? yxbz.hashCode() : 0);
        return result;
    }
}
