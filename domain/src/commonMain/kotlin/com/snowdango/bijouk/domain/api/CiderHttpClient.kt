package com.snowdango.bijouk.domain.api

import io.ktor.client.HttpClient

expect fun getCiderHttpClient(baseUrl: String, token: String): HttpClient