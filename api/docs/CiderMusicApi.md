# CiderMusicApi

All URIs are relative to *http://localhost:8080/api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiV1AmapiRunV3Post**](CiderMusicApi.md#apiV1AmapiRunV3Post) | **POST** /api/v1/amapi/run-v3 | Call Apple Music Api between Cider App |


<a id="apiV1AmapiRunV3Post"></a>
# **apiV1AmapiRunV3Post**
> SearchResponse apiV1AmapiRunV3Post(apiV1AmapiRunV3PostRequest)

Call Apple Music Api between Cider App



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderMusicApi()
val apiV1AmapiRunV3PostRequest : ApiV1AmapiRunV3PostRequest =  // ApiV1AmapiRunV3PostRequest | 
try {
    val result : SearchResponse = apiInstance.apiV1AmapiRunV3Post(apiV1AmapiRunV3PostRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderMusicApi#apiV1AmapiRunV3Post")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderMusicApi#apiV1AmapiRunV3Post")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **apiV1AmapiRunV3PostRequest** | [**ApiV1AmapiRunV3PostRequest**](ApiV1AmapiRunV3PostRequest.md)|  | [optional] |

### Return type

[**SearchResponse**](SearchResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

