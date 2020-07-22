package com.tina.mr9

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.CurrentFragmentType
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.tina.mr9.databinding.ActivityMainBinding
import com.tina.mr9.login.UserManager
import com.tina.mr9.util.Logger
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    /**
     * Lazily initialize our [MainViewModel].
     */
    val viewModel by viewModels<MainViewModel> { getVmFactory() }

    private lateinit var binding: ActivityMainBinding
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalHomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalSearchFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_rate -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalRateFragment(UserManager.user))
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_friends -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalFriendsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalProfileFragment(UserManager.user))
                return@OnNavigationItemSelectedListener true


//                when (viewModel.isLoggedIn) {
//                    true -> {
//                        findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToProfileFragment(viewModel.user.value))
//                    }
//                    false -> {
//                        findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToLoginDialog())
//                        return@OnNavigationItemSelectedListener false
//                    }
//                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    // get the height of status bar from system
    private val statusBarHeight: Int
        get() {
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            return when {
                resourceId > 0 -> resources.getDimensionPixelSize(resourceId)
                else -> 0
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        startActivity(Intent(this, LoginActivity::class.java))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.myNavHostFragment)
//        NavigationUI.setupWithNavController(binding.navigation, navController)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // observe current fragment change, only for show info
        viewModel.currentFragmentType.observe(this, Observer {
            Logger.i("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
            Logger.i("[${viewModel.currentFragmentType.value}]")
            Logger.i("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
        })

//        viewModel.navigateToLoginSuccess.observe(this, Observer {
//            it?.let {
//                findNavController(R.id.myNavHostFragment).navigate(
//                        NavigationDirections.navigateToMessageDialog(MessageDialog.MessageType.LOGIN_SUCCESS)
//                )
//                viewModel.onLoginSuccessNavigated()
//
//                // navigate to profile after login success
//                when (viewModel.currentFragmentType.value) {
//                    CurrentFragmentType.PAYMENT -> {}
//                    else -> viewModel.navigateToProfileByBottomNav(it)
//                }
//            }
//        })

//        viewModel.navigateToProfileByBottomNav.observe(this, Observer {
//            it?.let {
//                binding.bottomNavView.selectedItemId = R.id.navigation_profile
//                viewModel.onProfileNavigated()
//            }
//        })
//
//        viewModel.navigateToHomeByBottomNav.observe(this, Observer {
//            it?.let {
//                binding.bottomNavView.selectedItemId = R.id.navigation_home
//                viewModel.onHomeNavigated()
//            }
//        })

        setupToolbar()
        setupBottomNav()
        setupNavController()

        UserManager.user.uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        UserManager.user.name = FirebaseAuth.getInstance().currentUser?.displayName.toString()
        UserManager.user.email = FirebaseAuth.getInstance().currentUser?.email.toString()
        UserManager.user.image = FirebaseAuth.getInstance().currentUser?.photoUrl.toString()

        viewModel.updateUser(UserManager.user)

    }

    /**
     * Set up [BottomNavigationView], add badge view through [BottomNavigationMenuView] and [BottomNavigationItemView]
     * to display the count of Cart
     */

//    fun navigateToDialog() {
//        navController.navigate(R.id.couponDialog)
//        Log.d("navi", "navigate to dialog")
//    }


    private fun setupBottomNav() {
        binding.bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val menuView = binding.bottomNavView.getChildAt(0) as BottomNavigationMenuView
        val itemView = menuView.getChildAt(2) as BottomNavigationItemView
//        val bindingBadge = BadgeBottomBinding.inflate(LayoutInflater.from(this), itemView, true)
//        bindingBadge.lifecycleOwner = this
//        bindingBadge.viewModel = viewModel
    }

    /**
     * Set up [NavController.addOnDestinationChangedListener] to record the current fragment, it better than another design
     * which is change the [CurrentFragmentType] enum value by [MainViewModel] at [onCreateView]
     */
    private fun setupNavController() {
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.homeFragment -> CurrentFragmentType.HOME
                R.id.searchFragment -> CurrentFragmentType.SEARCH
                R.id.rateFragment -> CurrentFragmentType.RATE
                R.id.profileFragment -> CurrentFragmentType.PROFILE
                R.id.friendsFragment -> CurrentFragmentType.FRIENDS
                else -> viewModel.currentFragmentType.value
            }
        }
    }

    /**
     * Set up the layout of [Toolbar], according to whether it has cutout
     */
    private fun setupToolbar() {

        binding.toolbar.setPadding(0, statusBarHeight, 0, 0)

        launch {

            val dpi = resources.displayMetrics.densityDpi.toFloat()
            val dpiMultiple = dpi / DisplayMetrics.DENSITY_DEFAULT

            val cutoutHeight = getCutoutHeight()

            Logger.i("====== ${Build.MODEL} ======")
            Logger.i("$dpi dpi (${dpiMultiple}x)")
            Logger.i("statusBarHeight: ${statusBarHeight}px/${statusBarHeight / dpiMultiple}dp")

            when {
                cutoutHeight > 0 -> {
                    Logger.i("cutoutHeight: ${cutoutHeight}px/${cutoutHeight / dpiMultiple}dp")

                    val oriStatusBarHeight = resources.getDimensionPixelSize(R.dimen.height_status_bar_origin)

                    binding.toolbar.setPadding(0, oriStatusBarHeight, 0, 0)
                    val layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
                    layoutParams.gravity = Gravity.CENTER
                    layoutParams.topMargin = statusBarHeight - oriStatusBarHeight
                    binding.textToolbarTitle.layoutParams = layoutParams
                }
            }
            Logger.i("====== ${Build.MODEL} ======")
        }
    }

    fun permission(){
        ActivityCompat.requestPermissions(this, arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE), 0)
    }

    /**
     * Set up [androidx.drawerlayout.widget.DrawerLayout] with [androidx.appcompat.widget.Toolbar]
     */
//    private fun setupDrawer() {
//
//        // set up toolbar
//        val navController = this.findNavController(R.id.myNavHostFragment)
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.title = null
//
//        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
//        NavigationUI.setupWithNavController(binding.drawerNavView, navController)
//
//        binding.drawerLayout.fitsSystemWindows = true
//        binding.drawerLayout.clipToPadding = false
//
//        actionBarDrawerToggle = object : ActionBarDrawerToggle(
//                this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
//            override fun onDrawerOpened(drawerView: View) {
//                super.onDrawerOpened(drawerView)
//
//                when (UserManager.isLoggedIn) { // check user login status when open drawer
//                    true -> {
//                        viewModel.checkUser()
//                    }
//                    else -> {
//                        findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToLoginDialog())
//                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                            binding.drawerLayout.closeDrawer(GravityCompat.START)
//                        }
//                    }
//                }
//
//            }
//        }.apply {
//            binding.drawerLayout.addDrawerListener(this)
//            syncState()
//        }
//
//        // Set up header of drawer ui using data binding
//        val bindingNavHeader = NavHeaderDrawerBinding.inflate(
//                LayoutInflater.from(this), binding.drawerNavView, false)
//
//        bindingNavHeader.lifecycleOwner = this
//        bindingNavHeader.viewModel = viewModel
//        binding.drawerNavView.addHeaderView(bindingNavHeader.root)
//
//        // Observe current drawer toggle to set the navigation icon and behavior
//        viewModel.currentDrawerToggleType.observe(this, Observer { type ->
//
//            actionBarDrawerToggle?.isDrawerIndicatorEnabled = type.indicatorEnabled
//            supportActionBar?.setDisplayHomeAsUpEnabled(!type.indicatorEnabled)
//            binding.toolbar.setNavigationIcon(
//                    when (type) {
//                        DrawerToggleType.BACK -> R.drawable.toolbar_back
//                        else -> R.drawable.toolbar_menu
//                    }
//            )
//            actionBarDrawerToggle?.setToolbarNavigationClickListener {
//                when (type) {
//                    DrawerToggleType.BACK -> onBackPressed()
//                    else -> {}
//                }
//            }
//        })
//    }

    /**
     * override back key for the drawer design
     */
    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == UserFragment.PICK_PROFILE_FROM_ALBUM && resultCode == Activity.RESULT_OK){
//            var imageUri = data?.data
//            var uid = FirebaseAuth.getInstance().currentUser?.uid
//            var storageRef = FirebaseStorage.getInstance().reference.child("userProfileImages").child(uid!!)
//            storageRef.putFile(imageUri!!).continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
//                return@continueWithTask storageRef.downloadUrl
//            }.addOnSuccessListener { uri ->
//                var map = HashMap<String,Any>()
//                map["image"] = uri.toString()
//                FirebaseFirestore.getInstance().collection("profileImages").document(uid).set(map)
//            }
//        }
//    }
}

