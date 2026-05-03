package network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://gist.githubusercontent.com/WisnuWirWin/21f1508138c338840c65174a45959363/raw/c4830f37bf3fc654bde7abe21824b489ed8dacef/list_activity.json"

    val instant : APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }
}