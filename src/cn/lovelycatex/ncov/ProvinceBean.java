package cn.lovelycatex.ncov;

public class ProvinceBean {
    private String confirmed,suspected,cured,dead,tags,comment,provinceName,provinceALLName;

    public String getSuspected() {
        return suspected;
    }

    public String getDead() {
        return dead;
    }

    public String getCured() {
        return cured;
    }

    public String getComment() {
        return comment;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getProvinceALLName() {
        return provinceALLName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public String getTags() {
        return tags;
    }

    public void setSuspected(String suspected) {
        this.suspected = suspected;
    }

    public void setDead(String dead) {
        this.dead = dead;
    }

    public void setCured(String cured) {
        this.cured = cured;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public void setProvinceALLName(String provinceALLName) {
        this.provinceALLName = provinceALLName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
