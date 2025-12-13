# CiderRpcApi

All URIs are relative to *http://localhost:8080/api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**playbackActiveGet**](CiderRpcApi.md#playbackActiveGet) | **GET** /playback/active | server is active |
| [**playbackNextPost**](CiderRpcApi.md#playbackNextPost) | **POST** /playback/next | Control Song next |
| [**playbackNowPlayingGet**](CiderRpcApi.md#playbackNowPlayingGet) | **GET** /playback/now-playing | Get Now Playing Song |
| [**playbackPlayItemPost**](CiderRpcApi.md#playbackPlayItemPost) | **POST** /playback/play-item | Play Item |
| [**playbackPlayLaterPost**](CiderRpcApi.md#playbackPlayLaterPost) | **POST** /playback/play-later | Play Item Later |
| [**playbackPlayNextPost**](CiderRpcApi.md#playbackPlayNextPost) | **POST** /playback/play-next | Play Item Next |
| [**playbackPlaypausePost**](CiderRpcApi.md#playbackPlaypausePost) | **POST** /playback/playpause | Control Play Pause State |
| [**playbackPreviousPost**](CiderRpcApi.md#playbackPreviousPost) | **POST** /playback/previous | Control Song previous |
| [**playbackQueueChangeToIndexPost**](CiderRpcApi.md#playbackQueueChangeToIndexPost) | **POST** /playback/queue/change-to-index | Change Queue Index |
| [**playbackQueueClearQueuePost**](CiderRpcApi.md#playbackQueueClearQueuePost) | **POST** /playback/queue/clear-queue | Clear Queue |
| [**playbackQueueGet**](CiderRpcApi.md#playbackQueueGet) | **GET** /playback/queue | Get Queue State |
| [**playbackQueueMoveToPositionPost**](CiderRpcApi.md#playbackQueueMoveToPositionPost) | **POST** /playback/queue/move-to-position | Move Queue |
| [**playbackSeekPost**](CiderRpcApi.md#playbackSeekPost) | **POST** /playback/seek | Control Song seek |
| [**playbackShuffleModeGet**](CiderRpcApi.md#playbackShuffleModeGet) | **GET** /playback/shuffle-mode | Get Shuffle Mode |
| [**playbackToggleShufflePost**](CiderRpcApi.md#playbackToggleShufflePost) | **POST** /playback/toggle-shuffle | Shuffle Mode toggle change |


<a id="playbackActiveGet"></a>
# **playbackActiveGet**
> BasicResponse playbackActiveGet(body)

server is active



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val body : kotlin.Any =  // kotlin.Any | 
try {
    val result : BasicResponse = apiInstance.playbackActiveGet(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackActiveGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackActiveGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.Any**|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackNextPost"></a>
# **playbackNextPost**
> BasicResponse playbackNextPost(body)

Control Song next



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val body : kotlin.Any =  // kotlin.Any | 
try {
    val result : BasicResponse = apiInstance.playbackNextPost(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackNextPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackNextPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.Any**|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackNowPlayingGet"></a>
# **playbackNowPlayingGet**
> NowPlayingResponse playbackNowPlayingGet(body)

Get Now Playing Song



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val body : kotlin.Any =  // kotlin.Any | 
try {
    val result : NowPlayingResponse = apiInstance.playbackNowPlayingGet(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackNowPlayingGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackNowPlayingGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.Any**|  | [optional] |

### Return type

[**NowPlayingResponse**](NowPlayingResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackPlayItemPost"></a>
# **playbackPlayItemPost**
> BasicResponse playbackPlayItemPost(playRequestBody)

Play Item



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val playRequestBody : PlayRequestBody =  // PlayRequestBody | 
try {
    val result : BasicResponse = apiInstance.playbackPlayItemPost(playRequestBody)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackPlayItemPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackPlayItemPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **playRequestBody** | [**PlayRequestBody**](PlayRequestBody.md)|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackPlayLaterPost"></a>
# **playbackPlayLaterPost**
> BasicResponse playbackPlayLaterPost(playRequestBody)

Play Item Later



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val playRequestBody : PlayRequestBody =  // PlayRequestBody | 
try {
    val result : BasicResponse = apiInstance.playbackPlayLaterPost(playRequestBody)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackPlayLaterPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackPlayLaterPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **playRequestBody** | [**PlayRequestBody**](PlayRequestBody.md)|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackPlayNextPost"></a>
# **playbackPlayNextPost**
> BasicResponse playbackPlayNextPost(playRequestBody)

Play Item Next



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val playRequestBody : PlayRequestBody =  // PlayRequestBody | 
try {
    val result : BasicResponse = apiInstance.playbackPlayNextPost(playRequestBody)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackPlayNextPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackPlayNextPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **playRequestBody** | [**PlayRequestBody**](PlayRequestBody.md)|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackPlaypausePost"></a>
# **playbackPlaypausePost**
> BasicResponse playbackPlaypausePost(body)

Control Play Pause State



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val body : kotlin.Any =  // kotlin.Any | 
try {
    val result : BasicResponse = apiInstance.playbackPlaypausePost(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackPlaypausePost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackPlaypausePost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.Any**|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackPreviousPost"></a>
# **playbackPreviousPost**
> BasicResponse playbackPreviousPost(body)

Control Song previous



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val body : kotlin.Any =  // kotlin.Any | 
try {
    val result : BasicResponse = apiInstance.playbackPreviousPost(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackPreviousPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackPreviousPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.Any**|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackQueueChangeToIndexPost"></a>
# **playbackQueueChangeToIndexPost**
> BasicResponse playbackQueueChangeToIndexPost(playbackQueueChangeToIndexPostRequest)

Change Queue Index



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val playbackQueueChangeToIndexPostRequest : PlaybackQueueChangeToIndexPostRequest =  // PlaybackQueueChangeToIndexPostRequest | 
try {
    val result : BasicResponse = apiInstance.playbackQueueChangeToIndexPost(playbackQueueChangeToIndexPostRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackQueueChangeToIndexPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackQueueChangeToIndexPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **playbackQueueChangeToIndexPostRequest** | [**PlaybackQueueChangeToIndexPostRequest**](PlaybackQueueChangeToIndexPostRequest.md)|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackQueueClearQueuePost"></a>
# **playbackQueueClearQueuePost**
> BasicResponse playbackQueueClearQueuePost(body)

Clear Queue



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val body : kotlin.Any =  // kotlin.Any | 
try {
    val result : BasicResponse = apiInstance.playbackQueueClearQueuePost(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackQueueClearQueuePost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackQueueClearQueuePost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.Any**|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackQueueGet"></a>
# **playbackQueueGet**
> kotlin.collections.List&lt;QueueResponseData&gt; playbackQueueGet(body)

Get Queue State



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val body : kotlin.Any =  // kotlin.Any | 
try {
    val result : kotlin.collections.List<QueueResponseData> = apiInstance.playbackQueueGet(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackQueueGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackQueueGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.Any**|  | [optional] |

### Return type

[**kotlin.collections.List&lt;QueueResponseData&gt;**](QueueResponseData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackQueueMoveToPositionPost"></a>
# **playbackQueueMoveToPositionPost**
> BasicResponse playbackQueueMoveToPositionPost(playbackQueueMoveToPositionPostRequest)

Move Queue



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val playbackQueueMoveToPositionPostRequest : PlaybackQueueMoveToPositionPostRequest =  // PlaybackQueueMoveToPositionPostRequest | 
try {
    val result : BasicResponse = apiInstance.playbackQueueMoveToPositionPost(playbackQueueMoveToPositionPostRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackQueueMoveToPositionPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackQueueMoveToPositionPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **playbackQueueMoveToPositionPostRequest** | [**PlaybackQueueMoveToPositionPostRequest**](PlaybackQueueMoveToPositionPostRequest.md)|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackSeekPost"></a>
# **playbackSeekPost**
> BasicResponse playbackSeekPost(playbackSeekPostRequest)

Control Song seek



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val playbackSeekPostRequest : PlaybackSeekPostRequest =  // PlaybackSeekPostRequest | 
try {
    val result : BasicResponse = apiInstance.playbackSeekPost(playbackSeekPostRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackSeekPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackSeekPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **playbackSeekPostRequest** | [**PlaybackSeekPostRequest**](PlaybackSeekPostRequest.md)|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackShuffleModeGet"></a>
# **playbackShuffleModeGet**
> PlaybackShuffleModeGet200Response playbackShuffleModeGet(body)

Get Shuffle Mode



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val body : kotlin.Any =  // kotlin.Any | 
try {
    val result : PlaybackShuffleModeGet200Response = apiInstance.playbackShuffleModeGet(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackShuffleModeGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackShuffleModeGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.Any**|  | [optional] |

### Return type

[**PlaybackShuffleModeGet200Response**](PlaybackShuffleModeGet200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="playbackToggleShufflePost"></a>
# **playbackToggleShufflePost**
> BasicResponse playbackToggleShufflePost(body)

Shuffle Mode toggle change



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderRpcApi()
val body : kotlin.Any =  // kotlin.Any | 
try {
    val result : BasicResponse = apiInstance.playbackToggleShufflePost(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderRpcApi#playbackToggleShufflePost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderRpcApi#playbackToggleShufflePost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.Any**|  | [optional] |

### Return type

[**BasicResponse**](BasicResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

