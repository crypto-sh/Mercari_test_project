# Mercari Test Project

This project created base on [Mercari](https://mercari.com) assignment for hiring process.


It is base on Kotlin with most the new Android architecture components and another useful library on android such as
  - viewModel
  - liveData
  - data Binding
  - MVVM
  - Glide
  - Koin
  - androidTest with espresso

# Description
 
- BaseActivity is a generic class extended from AppCompatActivity which manage basic requirement are common in most Activity. 
 
- MainActivity is the launcher activity, It's shows layout with tabBar and ViewPager. every viewPager contain a Fragment. this activity extended from BaseActivity

    - MainActivityViewMode is a viewModel for MainActivity, handle Data Request and action. this ViewModel extended from BaseViewModel
    

- BaseViewModel is a generic class which manage basic requirement for all ViewModel

    - BaseView is a sealed class for manage action network Status and implemented to use by SingleLiveEvent.
    
    - SingleLiveEvent use instead of callback in MVVM. you can find more about it in this [Link](https://medium.com/@abhishektiwari_51145/how-to-use-singleliveevent-in-mvvm-architecture-component-b7c04ed8705)


- PageFragment is a Fragment extended from BaseFragment which used as page in ViewPager Main fragment.
 
    - BaseFragment is a generic class extended from Fragment which manage basic requirement are common in most Fragment.

    - PageFragmentViewModel is a viewModel for PageFragment, handle Data Request and action.(It does n't extend from BaseViewModel, I did n't have enough time solve it, so I should spend time to read about it and learn new things, thanks to u)
          

- [Koin](https://beta.insert-koin.io) used as dependencies injection. Module implemented in BaseApp and they are inject in BaseActivity and BaseFragment.

- BaseApp is Application class and the link between Kotlin with [JNI](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&cad=rja&uact=8&ved=2ahUKEwjJ9Z_W3ebhAhVLSN8KHbX6BmQQFjABegQIDBAE&url=https%3A%2F%2Fdeveloper.android.com%2Ftraining%2Farticles%2Fperf-jni&usg=AOvVaw0dRMAFHNpjKhf-EJZ3A3gX).

- ServiceRepository is used for implementation RestApi service. I've used [RestClient](https://github.com/alishatergholi/rest-client) api for call restApi which developed by my self base on OkHttp

- BindingAdapter class used for custom attribute we need for dataBinding.

- AppExecutor is responsible for execute all task in the project on Network, IO and Main Thread.

- LogHelper is a class for writing all Log in the hole project.

- Test Class
    - androidTest
            - webServiceTest for mocking web Service and test result.(It need to be complete)
            - ArtistDaoTest  for test database and Artist Table.
            - ListPageInstrumentedTest for test ListWithoutPaging and adapter.


Prerequisites
=============

    - Android Studio 3.3.2
    - Gradle version 4.10.2+
    - Kotlin 1.3.21


## Author

[Ali Shatergholi](https://github.com/alishatergholi)