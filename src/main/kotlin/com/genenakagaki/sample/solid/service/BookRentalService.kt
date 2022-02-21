package com.genenakagaki.sample.solid.service

import com.genenakagaki.sample.solid.CustomException
import com.genenakagaki.sample.solid.domain.book.BookCopyStateType
import com.genenakagaki.sample.solid.repository.BookCopyRentalRepository
import org.springframework.stereotype.Service

interface BookRentalService {

    fun rentBook(username: String, bookId: Int)
    fun returnBook(username: String, bookCopyId: Int)
}

@Service
class BookRentalServiceImpl(
    private val bookCopyRentalRepository: BookCopyRentalRepository,
): BookRentalService {

    override fun rentBook(username: String, bookId: Int) {
        val bookCopyRentalEntity = bookCopyRentalRepository.findWithCondition(bookId, BookCopyStateType.AVAILABLE)
        if (bookCopyRentalEntity == null) {
            throw CustomException("在庫が有りません。")
        }
        bookCopyRentalEntity.rentBook(username)
        bookCopyRentalRepository.save(bookCopyRentalEntity);
    }

    override fun returnBook(username: String, bookCopyId: Int) {
        val bookCopyRentalEntity = bookCopyRentalRepository.findById(bookCopyId)
        if (bookCopyRentalEntity == null) {
            throw CustomException("こんな本ありませんよ。")
        }

        bookCopyRentalEntity.returnBook(username)
        bookCopyRentalRepository.save(bookCopyRentalEntity)
    }
}
