package com.genenakagaki.sample.solid.service

import com.genenakagaki.sample.solid.repository.UserRepository
import org.springframework.stereotype.Service

interface UserService {

}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

}
