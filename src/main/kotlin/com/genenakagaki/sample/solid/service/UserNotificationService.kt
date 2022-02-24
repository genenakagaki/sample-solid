package com.genenakagaki.sample.solid.service

import com.genenakagaki.sample.solid.repository.UserRepository
import com.genenakagaki.sample.solid.str
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

enum class EmailTemplateType(
    val from: String,
    val subject: String,
    val body: String
) {
    RENTAL_COMPLETE(
        "noreply@sample-solid.com",
        "本のレンタルが完了しました。",
        """
            ###USERNAME### 様
            本のレンタルが完了したので読めますよ。
        """
    )
}

@Service
class UserNotificationService(
    private val mailSender: JavaMailSender,
    private val userRepository: UserRepository,
) {

    fun notifyUser(username: String, emailTemplate: EmailTemplateType) {
        val user = userRepository.findByUsername(username)
        if (user == null) {
            throw RuntimeException("ユーザーがみつかりませんでした。")
        }

        val message = SimpleMailMessage()
        message.setFrom(emailTemplate.from)
        message.setTo(user.email)
        message.setSubject(emailTemplate.subject)
        val body = emailTemplate.body.replace("###USERNAME###", username)
        message.setText(body)

        mailSender.send(message)
    }
}

