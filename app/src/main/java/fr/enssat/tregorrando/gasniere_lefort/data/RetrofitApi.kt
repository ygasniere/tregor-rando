package fr.enssat.tregorrando.gasniere_lefort.data

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.enssat.tregorrando.gasniere_lefort.data.json.Types
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.cert.X509Certificate
import java.util.concurrent.Executors
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface DataApi {
    companion object {
        val fields = listOf("iti_nom", "iti_long")
    }

    @GET("itineraires_randonnees_ltc?\$format=geojson&\$top=20000")
    fun getRandos(@Query("\$select") select: String = fields.joinToString(separator = ",")): Call<fr.enssat.tregorrando.gasniere_lefort.data.json.ListResponse>
}

class DataFetcher {
    var trustAnyCerts: Array<TrustManager> = arrayOf(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    )

    val sslContext = SSLContext.getInstance("SSL").apply {
        init(null, trustAnyCerts, java.security.SecureRandom())
    }

    private val logger: HttpLoggingInterceptor.Logger = object: HttpLoggingInterceptor.Logger {
        override fun log(message: String) { Log.d(TAG, message) }
    }

    private val loggingInterceptor = HttpLoggingInterceptor(logger).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .sslSocketFactory(sslContext.socketFactory, trustAnyCerts[0] as X509TrustManager)
        .build()

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val dataApi = Retrofit.Builder()
        .baseUrl("https://datarmor.cotesdarmor.fr/dataserver/data/")
        .client(httpClient)
        .callbackExecutor(Executors.newSingleThreadExecutor())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build().create(DataApi::class.java)


    fun fetchRandos(callback: (List<Types>) -> Unit) {
        dataApi.getRandos().enqueue(object: Callback<fr.enssat.tregorrando.gasniere_lefort.data.json.ListResponse> {
            override fun onResponse(call: Call<fr.enssat.tregorrando.gasniere_lefort.data.json.ListResponse>, response: Response<fr.enssat.tregorrando.gasniere_lefort.data.json.ListResponse>) {
                val data = response.body()
                if (data == null) {
                    Log.e(TAG, "no data fetched $response")
                    return
                }
                Log.d(TAG, "found ${data.features.size} randos")
                callback(data.features)
            }

            override fun onFailure(call: Call<fr.enssat.tregorrando.gasniere_lefort.data.json.ListResponse>, t: Throwable) {
                Log.e(TAG, "Error fetching data", t)
            }
        })
    }

    companion object {
        val TAG = "DATA_API_DEBUG"
    }
}