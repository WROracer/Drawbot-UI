package de.wrolu.drawbot.ui.domain;

public class Config {
    private String host = "ws://localhost";
    private String port = "25600";
    private boolean skipConfig = false;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isSkipConfig() {
        return skipConfig;
    }

    public void setSkipConfig(boolean skipConfig) {
        this.skipConfig = skipConfig;
    }
}
