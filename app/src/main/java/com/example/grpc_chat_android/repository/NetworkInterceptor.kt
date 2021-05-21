package com.example.grpc_chat_android.repository

import com.example.grpc_chat_android.di.AuthToken
import io.grpc.ClientInterceptor
import io.grpc.MethodDescriptor
import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ForwardingClientCall
import io.grpc.ClientCall
import io.grpc.Metadata
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(@AuthToken val token : String):ClientInterceptor {
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
                val key: Metadata.Key<String> =
                    Metadata.Key.of("Auth", Metadata.ASCII_STRING_MARSHALLER)
                metadata.put(key, token)
                headers?.merge(metadata)
                super.start(responseListener, headers)
            }
        }
    }
}