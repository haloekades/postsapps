package com.ekades.poststest.features.users.presentation.viewModels.state

import com.ekades.poststest.features.users.presentation.model.User

sealed class UsersVS {
    class AddUsers(val usersVM: List<User>): UsersVS()
    class Error(val message:String?): UsersVS()
    class ShowLoader(val showLoader:Boolean): UsersVS()
}