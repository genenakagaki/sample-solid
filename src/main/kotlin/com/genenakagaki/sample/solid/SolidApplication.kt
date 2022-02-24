package com.genenakagaki.sample.solid

import org.jooq.conf.RenderQuotedNames
import org.jooq.impl.DefaultConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

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

	@Bean
	fun getJavaMailSender(): JavaMailSender? {
		val mailSender = JavaMailSenderImpl()
		mailSender.host = "smtp.gmail.com"
		mailSender.port = 587
		mailSender.username = "my.gmail@gmail.com"
		mailSender.password = "password"
		val props: Properties = mailSender.javaMailProperties
		props.put("mail.transport.protocol", "smtp")
		props.put("mail.smtp.auth", "true")
		props.put("mail.smtp.starttls.enable", "true")
		props.put("mail.debug", "true")
		return mailSender
	}
}


fun Any?.int(): Int {
	return this as Int
}

fun Any?.str(): String {
	return this as String
}

fun Any?.add(item: Any) {
	(this as MutableList<*>).add(item)
}

fun Any?.list(): List<Map<String, Any>> {
	return this as List<Map<String, Any>>
}

fun main(args: Array<String>) {
	runApplication<SolidApplication>(*args)
}

