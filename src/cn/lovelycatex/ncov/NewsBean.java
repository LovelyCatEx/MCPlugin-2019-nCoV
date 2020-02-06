package cn.lovelycatex.ncov;

public class NewsBean {
    private String summary,title,body,infoSource,sourceURL,provinceName,date;

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getInfoSource() {
        return infoSource;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public String getSummary() {
        return summary;
    }
}
