/*
 * *
 *  * Created by Lionel Joffray on 20/09/19 18:13
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 20/09/19 09:35
 *  
 */

package com.openclassrooms.realestatemanager.api

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.openclassrooms.realestatemanager.models.User

/**
 * Created by Lionel JOFFRAY - on 02/05/2019.
 *
 *
 * User Helper class contains all CRUD request to Firestore User (private) Collection.
 */
object UserHelper {

    private val COLLECTION_NAME = "users"

    // --- COLLECTION REFERENCE ---

    /**
     * Get User Collection
     *
     * @return collection
     */
    val usersCollection: CollectionReference
        get() = FirebaseFirestore.getInstance().collection(COLLECTION_NAME)

    // --- CREATE ---

    /**
     * Create new user (private).
     *
     * @param uid        user estateId_fk
     * @param username   user pictureName
     * @param urlPicture picturePath url
     * @param email      email
     * @return new user
     */
    fun createUser(uid: String, username: String, urlPicture: String, email: String, date: String): Task<Void> {
        val userToCreate = User(uid, username, urlPicture, email, date)
        return UserHelper.usersCollection.document(uid).set(userToCreate)
    }

    // --- GET ---

    /**
     * Get actual user details.
     *
     * @param uid user estateId_fk
     * @return user details
     */
    fun getUser(uid: String): Task<DocumentSnapshot> {
        return UserHelper.usersCollection.document(uid).get()
    }

    // --- UPDATE ---

    /**
     * Update username.
     *
     * @param username new username
     * @param uid      user estateId_fk
     * @return update
     */
    fun updateUsername(username: String, uid: String): Task<Void> {
        return UserHelper.usersCollection.document(uid).update("username", username)
    }

    /**
     * Update Email.
     *
     * @param email new email
     * @param uid   user estateId_fk
     * @return update
     */
    fun updateEmail(email: String, uid: String): Task<Void> {
        return UserHelper.usersCollection.document(uid).update("email", email)
    }
    // --- DELETE ---

    /**
     * Delete user account.
     *
     * @param uid user estateId_fk
     * @return delete account
     */
    fun deleteUser(uid: String): Task<Void> {
        return UserHelper.usersCollection.document(uid).delete()
    }

}
