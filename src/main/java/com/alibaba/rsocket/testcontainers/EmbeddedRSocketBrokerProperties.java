package com.alibaba.rsocket.testcontainers;

import com.playtika.test.common.properties.CommonContainerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Embedded RSocket Broker properties
 *
 * @author leijuan
 */
@ConfigurationProperties("embedded.rsocket")
public class EmbeddedRSocketBrokerProperties extends CommonContainerProperties {
    static final String BEAN_NAME_EMBEDDED_RSOCKET = "embeddedRSocketBrokerContainer";
    String dockerImage = "linuxchina/alibaba-rsocket-broker:1.0.1";

    public String getDockerImage() {
        return dockerImage;
    }

    public void setDockerImage(String dockerImage) {
        this.dockerImage = dockerImage;
    }
}
