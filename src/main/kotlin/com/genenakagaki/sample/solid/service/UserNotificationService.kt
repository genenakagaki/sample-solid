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

    fun notifyUser(username: Any?, emailTemplate: EmailTemplateType) {
        val userData = userRepository.findByUsername(username)

        val message = SimpleMailMessage()
        message.setFrom(emailTemplate.from)
        message.setTo(userData["email"].str())
        message.setSubject(emailTemplate.subject)
        val body = emailTemplate.body.replace("###USERNAME###", username.str())
        message.setText(body)

        mailSender.send(message)
    }
}

