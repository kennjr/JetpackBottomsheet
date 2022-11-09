package com.ramanie.jetpackbottomsheet

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeFragment(){
    // since we'll need the state of the bottomSheet for us to alter it when the user clicks
    // a btn, we'll use the var below to keep track of the state
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
        // the line below allows us to animate how the sheet is expanded or collapsed
        animationSpec = spring( dampingRatio = Spring.DampingRatioMediumBouncy )
        )
    val scaffoldState = rememberBottomSheetScaffoldState(
        // the remember creates it's own bottomSheetState, but since we wanna access and modify it we'll pass our custom sheetState
        bottomSheetState = sheetState
    )
    // the coroutine scope below is what we use to toggle the sheet's state
    val coroutineScope = rememberCoroutineScope()
    // the BottomSheetScaffold is a wrapper arnd our BottomSheet that allows us to do a lot more with it(BottomSheet) easily
    BottomSheetScaffold(
        // we're passing our custom scaffold state here so that we can use it to toggle the sheet's state
        scaffoldState = scaffoldState,
        sheetContent = {
            //This is where we put the content that should appear in teh bottomSheet
            Box(modifier = Modifier
                .fillMaxWidth()
                // NOTE : we don't need the height if we've got a lotta content, bc the BottomSheet will wrap all the content in it
                .fillMaxHeight(0.4f), contentAlignment = Alignment.Center){
                Text(text = "Some bottom sheet content", fontSize = 14.sp, )
            }
        },
        // the peekHeight defines the height of the part of the sheet that'll be visible
        sheetPeekHeight = 15.dp,
        // the sheetShape will allow us to modify the shape of the bottomSheet to our liking
        sheetShape = RoundedCornerShape(topEnd = 0.1f, topStart = 0.1f),
        sheetElevation = 10.dp,
        sheetBackgroundColor = Color.LightGray
    ) {
        //This is where we put the content that should appear in the screen
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                // the text below will tell us how far the use is when swiping the sheet,
                // we can use this info to update another composable or run an animation
                Text(text = "${sheetState.progress.fraction}")
                Button(onClick = {
                    coroutineScope.launch {
                        if (sheetState.isCollapsed){
                            sheetState.expand()
                        }else{
                            sheetState.collapse()
                        }
                    }
                }) {
                    Text(text = "Toggle Sheet")
                }
            }
        }
    }
}