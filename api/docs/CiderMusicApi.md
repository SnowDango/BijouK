# CiderMusicApi

All URIs are relative to *http://localhost:8080/api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**amapiRunV3Post**](CiderMusicApi.md#amapiRunV3Post) | **POST** /amapi/run-v3 | Call Apple Music Api between Cider App |


<a id="amapiRunV3Post"></a>
# **amapiRunV3Post**
> AmapiRunV3Post200Response amapiRunV3Post(amapiRunV3PostRequest)

Call Apple Music Api between Cider App



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.snowdango.bijouk.api.model.*

val apiInstance = CiderMusicApi()
val amapiRunV3PostRequest : AmapiRunV3PostRequest =  // AmapiRunV3PostRequest | 
try {
    val result : AmapiRunV3Post200Response = apiInstance.amapiRunV3Post(amapiRunV3PostRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CiderMusicApi#amapiRunV3Post")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CiderMusicApi#amapiRunV3Post")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **amapiRunV3PostRequest** | [**AmapiRunV3PostRequest**](AmapiRunV3PostRequest.md)|  | [optional] |

### Return type

[**AmapiRunV3Post200Response**](AmapiRunV3Post200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

