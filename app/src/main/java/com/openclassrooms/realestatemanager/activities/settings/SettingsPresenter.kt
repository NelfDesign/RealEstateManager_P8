/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 18:59
 *
 */

package com.openclassrooms.realestatemanager.activities.settings


import com.openclassrooms.realestatemanager.utils.base.BasePresenter
import kotlin.math.pow
import kotlin.math.round

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The presenter for Settings Activity.
 */
class SettingsPresenter : BasePresenter(), SettingsContract.SettingsPresenterInterface {
    /**
     * Method to calculate a loan, calculate the total month, monthly amount and interest paid.
     */
    override fun calculateLoan(mIntArray: DoubleArray, mAmount: Int, mRate: Double, mYears: Int): DoubleArray {
        val duration: Double = mYears.toDouble() * 12
        val firstVal = mAmount * (mRate / 100) / 12
        val secondVal = 1 - (1 + (mRate / 100) / 12).pow((0 - duration))
        val monthly = firstVal / secondVal
        val interest = (monthly * duration) - mAmount
        mIntArray[0] = (round(monthly * 100) / 100)
        mIntArray[1] = (round(duration * 100) / 100)
        mIntArray[2] = (round(interest * 100) / 100)

        return mIntArray
    }
}
