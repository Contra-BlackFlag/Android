package com.example.musicapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.BottomSheetValue
// Material 2 imports are prioritized
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
// import androidx.compose.ui.tooling.preview.Preview // Unused, can be removed for minimal change
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
// import androidx.navigation.Navigation // Unused, can be removed for minimal change
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
// import com.example.musicapp.Screen.DrawerScreen.Accounts.title // Unused, can be removed for minimal change
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Removed @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(){
    val scaffoldState : ScaffoldState = rememberScaffoldState()
    val scope : CoroutineScope = rememberCoroutineScope()
    val controller : NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val viewModel : MainViewModel = viewModel()
    val currentScreen = remember { viewModel.currentScreen.value }
    val title  = remember { mutableStateOf(currentScreen.title) }

    val isSheetFullScreen by remember {mutableStateOf(false)}
    val modifier = if(isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()

    val modalSheetState = rememberModalBottomSheetState (
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            it != ModalBottomSheetValue.HalfExpanded }
    )

    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp

    val dialogOpen = remember { mutableStateOf(false) }
    val bottomBar : @Composable () -> Unit = {
        if (currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home){
            BottomNavigation(
                modifier = Modifier.wrapContentSize()
            ) {
                    screensInBottom.forEach {
                        BottomNavigationItem(selected = currentRoute == it.broute,
                            onClick ={controller.navigate(it.broute)
                                     title.value = it.btitle},
                            icon = {
                                Icon(painter = painterResource(id = it.icon), contentDescription = it.btitle)
                            },
                            label = { Text(it.btitle) },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.Black
                        )
                    }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = {
            MoreBottomSheet(modifier = modifier)
    }
    ) {

        Scaffold (
            bottomBar = bottomBar ,
            topBar = {
                TopAppBar(title = { Text(title.value) },
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (modalSheetState.isVisible) {
                                        modalSheetState.hide()
                                        }
                                    else modalSheetState.show()
                                    }
                                }

                        ){
                            Icon(imageVector = Icons.Default.MoreVert,
                                contentDescription = "More Option")
                        }
                    },// Text will use androidx.compose.material.Text
                    navigationIcon = { IconButton(onClick = { // IconButton will use androidx.compose.material.IconButton
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    })
                    {
                        Icon(imageVector = Icons.Default.AccountCircle, // Icon will use androidx.compose.material.Icon
                            contentDescription = "Account Button")
                    }
                    }
                )
            },
            scaffoldState = scaffoldState,
            drawerContent = {
                LazyColumn(Modifier.padding(16.dp)) {
                    items(screensInDrawer){
                            item ->
                        DrawerItem(selected = currentRoute == item.dRoute, item = item){
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                            if (item.dRoute == "add_account"){
                                dialogOpen.value = true
                            }
                            else {
                                controller.navigate(item.dRoute)
                                title.value = item.dtitle
                            }
                        }
                    }
                }
            }
        ){ paddingValues ->
            Navigation(navController = controller, viewModel = viewModel, pd = paddingValues)
            AccountDialog(dialogOpen = dialogOpen)
        }
    }
    }




@Composable
fun DrawerItem(
    selected : Boolean,
    item : Screen.DrawerScreen,
    onDrawerItemClicked : () -> Unit,
){
    val background = if (selected) Color.DarkGray else Color.White
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 16.dp).background(background)
            .clickable{
                onDrawerItemClicked()
            }
    ) {
        Icon( // Icon will use androidx.compose.material.Icon
            painter = painterResource(id = item.icon),
            contentDescription = item.dtitle,
            modifier = Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text( // Text will use androidx.compose.material.Text
            text = item.dtitle,
            style = MaterialTheme.typography.h5
        )
    }
}
@Composable
fun MoreBottomSheet(modifier: Modifier){
    Box(modifier.fillMaxSize().height(300.dp).background(
        MaterialTheme.colors.primarySurface
    )){
        Column(Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            Row(Modifier.padding(16.dp)) {
                Icon(modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(R.drawable.setting),
                    contentDescription = "" )
                Text("Settings", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}
@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd : PaddingValues){
    NavHost(navController = navController as NavHostController,
        startDestination = Screen.BottomScreen.Home.route, // This is "add_account"
        modifier = Modifier.padding(pd) ){

            composable(Screen.DrawerScreen.Accounts.route) { // This is "account"
                 AccountView()
            }
            composable(Screen.DrawerScreen.Subscription.route) { // This is "subscription"
                // Content for Subscription screen
                SubscriptionView()
            }
            composable(Screen.BottomScreen.Home.broute) {
                    HomeView()
            }
            composable(Screen.BottomScreen.Browse.broute) {
                    Browse()
            }
            composable(Screen.BottomScreen.Library.broute) {
                Library()
            }
    }
}


@Preview
@Composable
fun MoreBottomSheetPreview(){
    com.example.musicapp.MoreBottomSheet(Modifier.fillMaxSize())
}