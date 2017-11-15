package online.himakeit.love.bean;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/14 16:53
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class AppUpdateInfo {

    /**
     * {
     * "binary": {
     * "fsize": 5217206
     * },
     * "build": "1",
     * "changelog": "修复部分不可见BUG；
     * 更改启动页动画；
     * 增加页面统计；",
     * "direct_install_url": "http://download.fir.im/v2/app/install/5a0aca0d959d69632b00004e?download_token=d730f0b690d5244aa3f8c54d0146b4c7&source=update",
     * "installUrl": "http://download.fir.im/v2/app/install/5a0aca0d959d69632b00004e?download_token=d730f0b690d5244aa3f8c54d0146b4c7&source=update",
     * "install_url": "http://download.fir.im/v2/app/install/5a0aca0d959d69632b00004e?download_token=d730f0b690d5244aa3f8c54d0146b4c7&source=update",
     * "name": "Love",
     * "update_url": "http://fir.im/loveforchan",
     * "updated_at": 1510707099,
     * "version": "1",
     * "versionShort": "520.11150847"
     * }
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
     * fsize : 5217206
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