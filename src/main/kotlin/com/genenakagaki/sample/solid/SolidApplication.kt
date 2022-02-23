package com.genenakagaki.sample.solid

import org.jooq.conf.RenderQuotedNames
import org.jooq.impl.DefaultConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class SolidApplication

@Configuration
@ComponentScan("com.genenakagaki")
class AppConfiguration {

	@Bean
	fun configurationCustomizer(): DefaultConfigurationCustomizer? {
		return DefaultConfigurationCustomizer { c: DefaultConfiguration ->
			c.settings()
				.withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED)
		}
	}
}


fun Any?.int(): Int {
	return this as Int
}

fun Any?.str(): String {
	return this as String
}

fun main(args: Array<String>) {
	runApplication<SolidApplication>(*args)
}

