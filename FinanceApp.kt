package com.shubh.finance

import androidx.room.*

enum class AccountType { ASSET, LIABILITY, EQUITY, INCOME, EXPENSE }
enum class SubCategory { BANK, CASH, INVESTMENT, LOAN_GIVEN, LOAN_TAKEN, INCOME_CATEGORY, EXPENSE_CATEGORY, CAPITAL }

@Entity(tableName = "ledgers")
data class LedgerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val accountType: AccountType,
    val subCategory: SubCategory,
    val openingBalance: Double = 0.0,
    val notes: String? = null
)

@Entity(tableName = "vouchers")
data class VoucherEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val voucherNumber: String,
    val date: Long,
    val narration: String
)

@Entity(tableName = "voucher_lines")
data class VoucherLineEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val voucherId: Long,
    val ledgerId: Long,
    val debitAmount: Double = 0.0,
    val creditAmount: Double = 0.0
)
