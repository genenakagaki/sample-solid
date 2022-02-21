package com.genenakagaki.sample.solid.controller

data class RentBookFormModel(
    val username: String,
    val bookId: Int,
) {

}

data class ReturnBookFormModel(
    val username: String,
    val bookCopyId: Int,
) {
}
