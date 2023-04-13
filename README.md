# 📱 Android MVVM App with Retrofit, RecyclerView, Coil, RxJava, Safe Args and Navigation Component

This is a sample Android app built with Kotlin that showcases the Model-View-ViewModel (MVVM) architecture pattern using Retrofit for network requests, RecyclerView for displaying a list of data, Coil for efficient image loading, RxJava for reactive programming, Safe Args for passing parameters between fragments, and the Navigation Component for navigating between fragments.

The app retrieves data from the `products` endpoint from https://dummyjson.com and displays it in a list using a RecyclerView. The product images are loaded using the Coil library, which provides efficient image loading and caching.

## 🚀 Features

- Utilizes the MVVM architecture pattern for clear separation of concerns and maintainability
- Makes network requests using Retrofit for easy and efficient API calls
- Uses RxJava for reactive programming to handle asynchronous operations and event handling
- Displays a list of data using RecyclerView with efficient data binding and view recycling
- Efficiently loads and caches images using the Coil library
- Includes loading and error states to handle network connectivity issues
- Uses Safe Args to pass parameters between fragments
- Navigates between fragments using the Navigation Component
- Built with Kotlin and follows modern Android development best practices

## 🏁 Getting Started

To get started with the app, clone the repository and open it using Android Studio.

## 🔧 Dependencies

- Retrofit 2 for network requests
- RxJava 3 for reactive programming
- RecyclerView for displaying a list of data
- ViewModel and LiveData for handling data and state changes
- Gson for JSON parsing
- Coil for efficient image loading and caching
- Safe Args for passing parameters between fragments
- Navigation Component for navigating between fragments

## 🧩 Brief RxJava Tutorial
- Add the following dependencies to your app-level build.gradle file:

<pre>
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.9.1'
implementation 'io.reactivex.rxjava3:rxandroid:3.0.0'
implementation 'io.reactivex.rxjava3:rxjava:3.0.13'
implementation 'com.jakewharton.retrofit:retrofit2-rxjava3-adapter:1.0.0'
</pre>

- Create an interface to define the API endpoints you want to call. In this example, we will be calling an endpoint to retrieve a list of products. While creating the API interface, it would be nice to explain what is Single and Observable in RxJava. In RxJava, Single and Observable are different types of reactive streams that represent different **ways of emitting data and handling errors**.
<pre>
interface ProductApi {
    @GET(GET_ALL_PRODUCTS)
    //"Single" is useful for scenarios where you only need a single value
    fun getAllProducts(): <strong>Single</strong>&lt;ProductResponse&gt;
    //"Observable" would also work just fine.
    //fun getAllProducts(): <strong>Observable</strong>&lt;ProductResponse&gt;
}
</pre>

**Single** represents a stream that will emit a **single value**, or an error if something goes wrong. This is useful for scenarios where you only need a single value, such as fetching a user profile from a remote server. You can subscribe to a Single stream using the subscribe() method, which takes two callbacks: onSuccess() for handling the emitted value, and onError() for handling any errors that occur. If you expect to receive only one response from the server and you don't need to cancel the network call, then Single is a good choice. Single is more lightweight than Observable, as it only emits a single value or error.

**Observable** represents a stream that can emit **zero or more values**, or an error if something goes wrong. This is the most common type of stream in RxJava, and is used for most data processing scenarios. You can subscribe to an Observable stream using the subscribe() method, which takes three callbacks: onNext() for handling each emitted value, onComplete() for handling the stream completion, and onError() for handling any errors that occur. If you want to receive multiple responses from the server, or if you need to cancel the network call at any point, then Observable is a better choice. Observable is more flexible and powerful than Single, as it can emit multiple values, errors, and completion events. Keep in mind that in our example Single Or Observable will both work just fine but we only expect to get single response in our example, so using Single would be enough.

- Create a class that will handle the creation of the Retrofit client. In this example, we will create a class called ProductApiClient.

<pre>
class ProductApiClient {

    companion object {

        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
        }.build()
    }

    fun getProductApi(): ProductApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // Add RxJava adapter
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ProductApi::class.java)
    }
}
</pre>

- In the getAllProducts() function of your viewmodel, use the getProductApi() function to get the instance of the ProductApi interface and make a network call using getAllProducts() function.

<pre>
    fun getAllProducts() {
        _responseLoading.postValue(true)
        try {
            disposable.add(
                productApi.getAllProducts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { response ->
                            _responseLoading.postValue(false)
                            _response.postValue(response.products)
                        },
                        { error ->
                            _responseLoading.postValue(false)
                            Log.d(TAG, error.toString())
                        }
                    )
            )
        }
        catch(e : Exception) {
            Log.d(TAG, e.toString())
        }
    }
</pre>

- **disposable.add()** - This creates a new disposable object to hold the subscription. This is used to ensure that the subscription is properly disposed of when it is no longer needed, to avoid memory leaks.
- **subscribeOn(Schedulers.io())** - This specifies which thread to run the network request on. In this case, it uses the I/O thread pool to make the request asynchronously.
- **observeOn(AndroidSchedulers.mainThread())** - This specifies which thread to observe the results on. In this case, it uses the main thread to update the UI with the response.
- **subscribe()** - This sets up the observer to handle the response or any errors that occur during the network request.

## 📷 Screenshots

<p align="center">
  <img src="screenshots/screenshot_1.png" width="25%" alt="Screenshot 1" />
  <img src="screenshots/screenshot_2.png" width="25%" alt="Screenshot 2" />
</p>

## 🎨 Navigation Graph

The app uses the Navigation Component to navigate between fragments. The `nav_graph.xml` file defines the app's navigation graph, which includes the following fragments:

- `ProductListFragment`: Displays a list of products retrieved from the server using Retrofit and RxJava.
- `ProductDetailFragment`: Displays details for a selected product, including the product name, image, and description. The fragment uses Safe Args to receive the product data from the `ProductListFragment`.


## 📝 License

This project is free to use and is licensed under the MIT License.
