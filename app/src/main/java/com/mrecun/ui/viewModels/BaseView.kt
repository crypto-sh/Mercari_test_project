package com.mrecun.ui.viewModels

sealed class BaseView {

    class ShowProgress : BaseView()

    class HideProgress : BaseView()

    class ShowErrorMessage(val errorMessage : String) : BaseView()
}