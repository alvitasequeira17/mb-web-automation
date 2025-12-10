package org.example.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * test configuration
 */
public class TestConfig {

    @JsonProperty("application")
    private Application application;

    @JsonProperty("browser")
    private Browser browser;

    @JsonProperty("timeouts")
    private Timeouts timeouts;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public Timeouts getTimeouts() {
        return timeouts;
    }

    public void setTimeouts(Timeouts timeouts) {
        this.timeouts = timeouts;
    }

    public static class Application {
        @JsonProperty("base_url")
        private String baseUrl;

        @JsonProperty("name")
        private String name;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Browser {
        @JsonProperty("default_browser")
        private String defaultBrowser;

        @JsonProperty("headless")
        private boolean headless;

        @JsonProperty("window_size")
        private String windowSize;

        @JsonProperty("supported_browsers")
        private String[] supportedBrowsers;

        public String getDefaultBrowser() {
            return defaultBrowser;
        }

        public void setDefaultBrowser(String defaultBrowser) {
            this.defaultBrowser = defaultBrowser;
        }

        public boolean isHeadless() {
            return headless;
        }

        public void setHeadless(boolean headless) {
            this.headless = headless;
        }

        public String getWindowSize() {
            return windowSize;
        }

        public void setWindowSize(String windowSize) {
            this.windowSize = windowSize;
        }

        public String[] getSupportedBrowsers() {
            return supportedBrowsers;
        }

        public void setSupportedBrowsers(String[] supportedBrowsers) {
            this.supportedBrowsers = supportedBrowsers;
        }
    }

    public static class Timeouts {
        @JsonProperty("implicit_wait")
        private int implicitWait;

        @JsonProperty("explicit_wait")
        private int explicitWait;

        @JsonProperty("page_load")
        private int pageLoad;

        @JsonProperty("script_timeout")
        private int scriptTimeout;

        public int getImplicitWait() {
            return implicitWait;
        }

        public void setImplicitWait(int implicitWait) {
            this.implicitWait = implicitWait;
        }

        public int getExplicitWait() {
            return explicitWait;
        }

        public void setExplicitWait(int explicitWait) {
            this.explicitWait = explicitWait;
        }

        public int getPageLoad() {
            return pageLoad;
        }

        public void setPageLoad(int pageLoad) {
            this.pageLoad = pageLoad;
        }

        public int getScriptTimeout() {
            return scriptTimeout;
        }

        public void setScriptTimeout(int scriptTimeout) {
            this.scriptTimeout = scriptTimeout;
        }
    }
}

