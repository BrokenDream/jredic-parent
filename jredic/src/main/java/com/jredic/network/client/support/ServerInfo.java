package com.jredic.network.client.support;

/**
 * Some info of Redis Server.
 *
 * @author David.W
 */
public class ServerInfo {

    //redis server's version.
    private String version;

    //the os info of which redis server is running on.
    private String os;

    //the os arch...
    private String arch;

    //the process id of redis server.
    private String pid;

    //the run id of redis server.
    private String runId;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    @Override
    public String toString() {
        return "[" +
                "version='" + version + '\'' +
                ", os='" + os + '\'' +
                ", arch='" + arch + '\'' +
                ", pid='" + pid + '\'' +
                ", runId='" + runId + '\'' +
                ']';
    }
}
