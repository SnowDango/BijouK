package com.snowdango.bijouk.features.now_play.component

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.gestures.Orientation

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetValue
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberModalBottomSheetState

import androidx.compose.runtime.Composable


@Composable
fun NowPlayBottomSheet() {

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded,
    )

    /*BottomSheetScaffold(

    ) { }*/

}
