package com.example.grpc_chat_android.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.grpc_chat_android.PreferenceManager
import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.ClientInterceptor
import io.grpc.ForwardingClientCall
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class NetworkInterceptor @Inject constructor(private val preferenceManager: PreferenceManager) : ClientInterceptor {
    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        method: MethodDescriptor<ReqT, RespT>?,
        callOptions: CallOptions?,
        next: Channel?
    ): ClientCall<ReqT, RespT> {
        return object : ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next!!.newCall(
            method,
            callOptions
        )) {
            override fun start(responseListener: Listener<RespT>?, headers: Metadata?) {
                val metadata = Metadata()
                val token = runBlocking {
                    preferenceManager.getToken()
                }
                val key: Metadata.Key<String> =
                    Metadata.Key.of("Auth", Metadata.ASCII_STRING_MARSHALLER)
                metadata.put(key, token)
                headers?.merge(metadata)
                super.start(responseListener, headers)
            }
        }
    }
}
