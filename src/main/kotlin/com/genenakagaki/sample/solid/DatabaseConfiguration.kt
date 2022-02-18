package com.genenakagaki.sample.solid

import org.jooq.conf.RenderQuotedNames
import org.jooq.impl.DefaultConfiguration
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.genenakagaki")
class DatabaseConfiguration {

    @Bean
    fun configurationCustomizer(): DefaultConfigurationCustomizer? {
        return DefaultConfigurationCustomizer { c: DefaultConfiguration ->
            c.settings()
                .withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED)
        }
    }
}
