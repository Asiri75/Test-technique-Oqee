package com.asiri.traxtest.repository.helper

import android.content.Context
import android.provider.MediaStore
import androidx.annotation.NonNull
import com.asiri.traxtest.model.Movie
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import java.io.IOException
import java.lang.Exception
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object HTTPHelper {

    private lateinit var x509TrustManager: X509TrustManager
    private const val CA_FILENAME = "trax_ca"




    fun initSSL(@NonNull context: Context, httpClientBuilder: OkHttpClient.Builder) {
        val sslContext : SSLContext
        try {
            sslContext = getSSLConfig(context)
            httpClientBuilder.sslSocketFactory(sslContext.socketFactory, x509TrustManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(
        CertificateException::class,
        IOException::class,
        KeyStoreException::class,
        NoSuchAlgorithmException::class,
        KeyManagementException::class
    )
    private fun getSSLConfig(context: Context): SSLContext {

        // Loading CAs from an InputStream
        var cf: CertificateFactory? = null
        cf = CertificateFactory.getInstance("X.509")


        val caInputs = context.resources.assets.open(CA_FILENAME)
        val ca: X509Certificate = caInputs.use {
            cf.generateCertificate(it) as X509Certificate
        }

        // Creating a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)

        // Creating a TrustManager that trusts the CAs in our KeyStore.
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)

        x509TrustManager = tmf.trustManagers[0] as X509TrustManager

        // Creating an SSLSocketFactory that uses our TrustManager
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, tmf.trustManagers, null)

        return sslContext
    }

}