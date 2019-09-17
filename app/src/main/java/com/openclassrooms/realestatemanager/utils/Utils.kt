/*
 * *
 *  * Created by Lionel Joffray on 17/09/19 23:02
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 17/09/19 17:36
 *
 */

package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.extensions.backSlashRemover
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.models.places.nearby_search.Result
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import com.openclassrooms.realpicturemanager.activities.viewmodels.PictureViewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


/**
 * Created by Philippe on 21/02/2018.
 */

object Utils {

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    val todayDate: String
        get() {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            return dateFormat.format(Date())
        }

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    fun convertDollarToEuro(dollars: Int): Int {
        return (dollars * 0.903).roundToInt()
    }

    /**
     * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param euros
     * @return
     */
    fun convertEuroToDollar(euros: Int): Int {
        return (euros * 1.109).roundToInt()
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    fun isInternetAvailable(context: Context): Boolean {
        var activeNetwork: Boolean
        val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val network = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        activeNetwork = wifi.isWifiEnabled || network.activeNetworkInfo != null && network.activeNetworkInfo.isConnected
        return activeNetwork
    }

    fun snackBarPreset(view: View, message: String): Unit {
        val snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackView = snack.view
        val txtView = snackView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackView.setPadding(0, 0, 0, 0)
        txtView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        return snack.show()
    }

    fun dateToMillis(year: Int, monthOfYear: Int, dayOfMonth: Int): Long {
        var cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        return cal.timeInMillis
    }

    fun millisToDate(millis: Long): String {
        val dateFormat = SimpleDateFormat("ddMMyyyy")
        val result = Date(millis)

        return dateFormat.format(result)
    }

    fun dateWithBSToMillis(dateString: String): Long {
        val string = String().backSlashRemover(dateString)

        return dateToMillis(string.substring(4, 8).toInt(), string.substring(2, 4).toInt(), string.substring(0, 2).toInt())
    }

    /**
     * copy contents from source file to mappPath file
     *
     * @param sourceFilePath  Source file path address
     * @param destinationFilePath Destination file path address
     */
    fun copyFile(sourceFilePath: File, destinationFilePath: File) {

        try {

            if (!sourceFilePath.exists()) {
                return
            }

            val source: FileChannel = FileInputStream(sourceFilePath).channel
            val destination: FileChannel = FileOutputStream(destinationFilePath).channel
            destination.transferFrom(source, 0, source.size())
            source.close()
            destination.close()

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun savePictureToCustomPath(eid: Long, mPicturePathArray: ArrayList<String>, mEstatePhotosDir: File, displayName: String?, mPictureViewModel: PictureViewModel) {

        var i = 0
        lateinit var fileDest: File
        while (i < mPicturePathArray.size) {
            if (mPicturePathArray[i] != "") {
                val file = File(mPicturePathArray[i])
                val pictureName = eid.toString() + "_" + displayName + "_" + "$i"
                if (i == 0) {
                    fileDest = File(mEstatePhotosDir.path + "/" + pictureName + "_main.jpg")
                } else {
                    fileDest = File(mEstatePhotosDir.path + "/" + pictureName + ".jpg")
                }
                if (!fileDest.exists()) {
                    copyFile(file, fileDest)
                }
                savePicture(pictureName, fileDest.toString(), eid, mPictureViewModel)
            }
            i++
        }
    }

    fun savePicture(pictureName: String, fileDest: String, eid: Long, mPictureViewModel: PictureViewModel) {
        mPictureViewModel.createPicture(Picture(null, eid, pictureName, fileDest))
    }

    fun createNearby(eid: Long, mSchool: ArrayList<String>, mPolice: List<Result>?, mHospital: List<Result>?, mEstateViewModel: EstateViewModel) {
        when {
            mSchool.size > 0 -> {
                var i = 0
                while (i < mSchool.size) {
                    val nearby = com.openclassrooms.realestatemanager.models.Nearby(null, eid, "School", mSchool[i])
                    mEstateViewModel.createNearby(nearby)
                    i++
                }
            }
            mPolice!!.isNotEmpty() -> {
                var i = 0
                while (i < mPolice.size) {
                    val nearby = com.openclassrooms.realestatemanager.models.Nearby(null, eid, "Police Station", mPolice[i].name!!)
                    mEstateViewModel.createNearby(nearby)
                    i++
                }
            }
            mHospital!!.isNotEmpty() -> {
                var i = 0
                while (i < mHospital.size) {
                    val nearby = com.openclassrooms.realestatemanager.models.Nearby(null, eid, "Hospital", mHospital[i].name!!)
                    mEstateViewModel.createNearby(nearby)
                    i++
                }
            }
        }
    }

    fun setFadeAnimation(view: View, context: Context) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        view.startAnimation(anim)
    }
    class ListOfString {

        companion object {
            fun listOfType(): Array<String> {
                return listOf("Apartment", "Castle", "Chalet / Cottage", "Country House", "Hotel",
                        "House", "Island", "Land", "Loft", "Mansion", "Office", "Penthouse",
                        "Residential complex", "Townhouse", "Villa").toTypedArray().sortedArray()
            }

            fun listOfNeighborhood(): Array<String> {

                return listOf("Albany", "Allegany", "Bronx", "Broome", "Cattaraugus", "Cayuga",
                        "Chautauqua", "Chemung", "Chenango", "Clinton", "Columbia", "Cortland",
                        "Delaware", "Dutchess", "Erie", "Essex", "Franklin", "Fulton", "Genesee",
                        "Greene", "Hamilton", "Herkimer", "Jefferson", "Kings (Brooklyn)",
                        "Lewis", "Livingston", "Madison", "Monroe", "Montgomery", "Nassau",
                        "New York (Manhattan)", "Niagara", "Oneida", "Onondaga", "Ontario",
                        "Orange", "Orleans", "Oswego", "Otsego", "Putnam", "Queens", "Rensselaer",
                        "Richmond (Staten Island)", "Rockland", "Saint Lawrence", "Saratoga",
                        "Schenectady", "Schoharie", "Schuyler", "Seneca", "Steuben", "Suffolk",
                        "Sullivan", "Tioga", "Tompkins", "Ulster", "Warren", "Washington",
                        "Wayne", "Westchester", "Wyoming", "Yates").toTypedArray().sortedArray()
            }

            fun listOfAvailable(): Array<String> {
                return listOf("For Sale", "Sold").toTypedArray()
            }

        }

    }
}