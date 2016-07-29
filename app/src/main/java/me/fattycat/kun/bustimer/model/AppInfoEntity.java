package me.fattycat.kun.bustimer.model;

/**
 * Author: Kelvinkun
 * Date: 16/7/29
 */

public class AppInfoEntity {
    /**
     * name : 港城公交
     * version : 2
     * changelog : 修复无应用商店时的闪退。
     * updated_at : 1469608514
     * versionShort : 2.0.1
     * build : 2
     * installUrl : http://download.fir.im/v2/app/install/57986ab67e4ff056ee0003bd?download_token=33d90a4785fce828be2c6b4e7752cfba
     * install_url : http://download.fir.im/v2/app/install/57986ab67e4ff056ee0003bd?download_token=33d90a4785fce828be2c6b4e7752cfba
     * direct_install_url : http://download.fir.im/v2/app/install/57986ab67e4ff056ee0003bd?download_token=33d90a4785fce828be2c6b4e7752cfba
     * update_url : http://fir.im/bustimer
     * binary : {"fsize":2762277}
     */

    private String name;
    private String version;
    private String changelog;
    private int updated_at;
    private String versionShort;
    private String build;
    private String installUrl;
    private String install_url;
    private String direct_install_url;
    private String update_url;
    /**
     * fsize : 2762277
     */

    private BinaryEntity binary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public BinaryEntity getBinary() {
        return binary;
    }

    public void setBinary(BinaryEntity binary) {
        this.binary = binary;
    }

    public static class BinaryEntity {
        private int fsize;

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }
    }
}
