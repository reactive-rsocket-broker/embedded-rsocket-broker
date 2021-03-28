package com.alibaba.rsocket.testcontainers;

import com.playtika.test.common.spring.DockerPresenceBootstrapConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.LinkedHashMap;

import static com.alibaba.rsocket.testcontainers.EmbeddedRSocketBrokerProperties.BEAN_NAME_EMBEDDED_RSOCKET;
import static com.playtika.test.common.utils.ContainerUtils.configureCommonsAndStart;


/**
 * Embedded RSocket Broker Boostrap configuration
 *
 * @author leijuan
 */
@Configuration
@ConditionalOnExpression("${embedded.containers.enabled:true}")
@AutoConfigureAfter(DockerPresenceBootstrapConfiguration.class)
@ConditionalOnProperty(name = "embedded.rsocket.enabled", matchIfMissing = true)
@EnableConfigurationProperties(EmbeddedRSocketBrokerProperties.class)
public class EmbeddedRSocketBrokerBootstrapConfiguration {
    private static final Logger log = LoggerFactory.getLogger(EmbeddedRSocketBrokerBootstrapConfiguration.class);

    @Bean(name = BEAN_NAME_EMBEDDED_RSOCKET, destroyMethod = "stop")
    public RSocketBrokerContainer embeddedRSocketBrokerContainer(ConfigurableEnvironment environment,
                                                                 EmbeddedRSocketBrokerProperties properties) throws Exception {
        log.info("Starting RSocket Broker. Docker image: {}", properties.dockerImage);
        RSocketBrokerContainer brokerContainer = new RSocketBrokerContainer(properties.getDockerImage());
        // configure and start the RSocket Broker container
        brokerContainer = (RSocketBrokerContainer) configureCommonsAndStart(brokerContainer, properties, log);
        registerRSocketEnvironment(brokerContainer, environment, properties);
        return brokerContainer;
    }

    private void registerRSocketEnvironment(RSocketBrokerContainer brokerContainer,
                                            ConfigurableEnvironment environment,
                                            EmbeddedRSocketBrokerProperties properties) {
        LinkedHashMap<String, Object> embeddedInfo = new LinkedHashMap<>();
        embeddedInfo.put("embedded.rsocket.uri", brokerContainer.getRSocketBrokerUri());
        log.info("RSocket Broker Connection URI: {}", brokerContainer.getRSocketBrokerUri());
        log.info("RSocket Broker WebConsole URL: {}", brokerContainer.getWebConsoleURL());
        log.info("RSocket Broker Actuator URL: {}", brokerContainer.getActuatorURL());
        MapPropertySource propertySource = new MapPropertySource("embeddedRSocketInfo", embeddedInfo);
        environment.getPropertySources().addFirst(propertySource);
    }

}
