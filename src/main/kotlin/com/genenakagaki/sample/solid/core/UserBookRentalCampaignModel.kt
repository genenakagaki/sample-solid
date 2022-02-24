package com.genenakagaki.sample.solid.core

class UserBookRentalCampaignModel(
    model: UserBookRentalModel
) : UserBookRentalModel(
    model.username, model.userCredit, model.currentRentalList, model.rentalHistory
) {

    private val campaignDiscount = 100

    override fun rentBook(bookId: Int, bookRentalPrice: Int) {
        var campaignPrice = bookRentalPrice - campaignDiscount
        if (bookRentalPrice < 100) {
            campaignPrice = 0
        }

        super.rentBook(bookId, campaignPrice)
    }
}
