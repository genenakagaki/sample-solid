package com.genenakagaki.sample.solid.domain.book

import com.genenakagaki.sample.solid.CustomException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils

internal class UserBookRentalModelTest {

    lateinit var SUT: UserBookRentalModel;

    fun 既に借りられている場合(bookId: Int) {
        ReflectionTestUtils.setField(
            SUT, "currentRentalList", mutableListOf(
                UserBookRentalEntry.create(bookId)
            )
        )
    }

    @BeforeEach
    fun init() {
        SUT = UserBookRentalModel(
            username = "g-nakagaki",
            userCredit = 500,
            currentRentalList = mutableListOf(),
            rentalHistory = mutableListOf()
        )
    }

    @Nested
    inner class 本を借りる {

        @Test
        fun `既に借りられている場合＿エラーになること`() {
            既に借りられている場合(1)

            assertThatExceptionOfType(CustomException::class.java)
                .isThrownBy {
                    SUT.rentBook(1, BookPrice(100, 1000))
                }
                .withMessage("既に借りてます。")
        }

        @Test
        fun `ユーザーのお金が足りない場合＿エラーになること`() {
            assertThatExceptionOfType(CustomException::class.java)
                .isThrownBy {
                    SUT.rentBook(1, BookPrice(1000000000, 1000))
                }
                .withMessage("お金が足りません。")
        }

        @Test
        fun `ユーザーのお金が貸し出し金額分引かれていること`() {
            val initialCredit = 500
            val rentPrice = 100
            ReflectionTestUtils.setField(SUT, "userCredit", initialCredit)

            SUT.rentBook(1, BookPrice(rentPrice, 1000))
            assertThat(SUT.userCredit).isEqualTo(400)
        }

        @Test
        fun `現在借りている本のリストに本が追加されること`() {
            SUT.rentBook(1, BookPrice(100, 1000))
            assertThat(SUT.currentRentalList).anyMatch { r -> r.bookId == 1 }
        }

        @Test
        fun `借りた本の履歴のリストに本が追加されること`() {
            SUT.rentBook(1, BookPrice(100, 1000))
            assertThat(SUT.rentalHistory).anyMatch { r -> r.bookId == 1 }
        }

    }
}

