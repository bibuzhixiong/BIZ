package com.woosii.biz.base.bean.json;

/**
 * Created by Administrator on 2017/11/4.
 */

public class VersionBean {
    private String versioncode;
    private String versionname;
    private String url;

    public String getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "versioncode='" + versioncode + '\'' +
                ", versionname='" + versionname + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
