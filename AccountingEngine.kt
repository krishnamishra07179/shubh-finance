package com.shubh.finance

import kotlin.math.abs

data class FinancialReport(
    val totalBank: Double,
    val totalCash: Double,
    val totalInvestments: Double,
    val totalLoansGiven: Double,
    val totalLiabilities: Double,
    val netProfit: Double,
    val netWorth: Double
)

class DoubleEntryAccountingEngine {
    fun validateVoucher(lines: List<VoucherLineEntity>): Boolean {
        val totalDr = lines.sumOf { it.debitAmount }
        val totalCr = lines.sumOf { it.creditAmount }
        return abs(totalDr - totalCr) < 0.01 && lines.isNotEmpty()
    }

    fun calculateBalances(
        ledgers: List<LedgerEntity>,
        lines: List<VoucherLineEntity>
    ): FinancialReport {
        var bank = 0.0
        var cash = 0.0
        var invest = 0.0
        var loans = 0.0
        var liab = 0.0
        var income = 0.0
        var expense = 0.0

        for (l in ledgers) {
            val dr = lines.filter { it.ledgerId == l.id }.sumOf { it.debitAmount }
            val cr = lines.filter { it.ledgerId == l.id }.sumOf { it.creditAmount }
            val net = l.openingBalance + (dr - cr)

            when (l.subCategory) {
                SubCategory.BANK -> bank += net
                SubCategory.CASH -> cash += net
                SubCategory.INVESTMENT -> invest += net
                SubCategory.LOAN_GIVEN -> loans += net
                SubCategory.LOAN_TAKEN -> liab += (l.openingBalance + cr - dr)
                SubCategory.INCOME_CATEGORY -> income += (cr - dr)
                SubCategory.EXPENSE_CATEGORY -> expense += (dr - cr)
                SubCategory.CAPITAL -> {}
            }
        }

        val profit = income - expense
        val totalAssets = bank + cash + invest + loans
        val netWorth = totalAssets - liab

        return FinancialReport(
            totalBank = bank,
            totalCash = cash,
            totalInvestments = invest,
            totalLoansGiven = loans,
            totalLiabilities = liab,
            netProfit = profit,
            netWorth = netWorth
        )
    }
}
