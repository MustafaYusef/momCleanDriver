package com.croczi.mamaclean_driver



import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.chibatching.kotpref.KotprefModel
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pop_dilog.view.*


class MainActivity : AppCompatActivity()
    ,AppBarConfiguration.OnNavigateUpListener {
    object cacheObj : KotprefModel() {

        var token by stringPref("")
        var name by stringPref("")
        var photo by stringPref("")
        var state by booleanPref(false)
    }

    object constant {
        val base: String = "https://api.maamclean.com/files/"
    }
    private lateinit var navController:NavController
    var toggel: ActionBarDrawerToggle? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)



        //set the toolbar
//        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)
//
//
////        val host: NavHostFragment = supportFragmentManager
////            .findFragmentById(R.id.navHost) as NavHostFragment? ?: return
////        val navController = findNavController(R.id.navHost)
//        //configure nav controller
//         navController = findNavController(R.id.navHost)
//        findViewById<NavigationView>(R.id.nav_view)
//            .setupWithNavController(navController)
//        nav_view.menu.get(1).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {
//            this.toast("dnkdfnk")
//            navController.navigate(R.id.ordersFragment2)
//
//            true
//        })
//          appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupNavigation(navController) //setup navigation
//         setupActionBar(navController, appBarConfiguration) // setup action bar
        //hear for event changes
        setupNavigation()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val header: View = nav_view.getHeaderView(0)
            var mNameTextView = header.findViewById<View>(R.id.driverTitle) as TextView
            mNameTextView.setText(cacheObj.name)

            var photo = header.findViewById<View>(R.id.drwarImage) as CircleImageView

            this?.let {
                Glide.with(it).load(MainActivity.constant.base +cacheObj.photo)
                    .placeholder(R.drawable.logo)
                    .into(photo)
            }
           if(navController.currentDestination?.id==R.id.login
               ||navController.currentDestination?.id==R.id.activateUser2
               ||navController.currentDestination?.id==R.id.resetPassword
               ||navController.currentDestination?.id==R.id.forgetPass
               ||navController.currentDestination?.id==R.id.editeProfile2
               ||navController.currentDestination?.id==R.id.changePassword){
               toolbar?.visibility=View.GONE
           }else{
               toolbar?.visibility=View.VISIBLE
           }
            if(navController.currentDestination?.id==R.id.mapFragment){
                titleMain?.text="الرئيسية"
                if(cacheObj.token=="")
                    navController.navigate(R.id.login)
            }else if(navController.currentDestination?.id==R.id.ordersFragment2){
                titleMain?.text="في الأنتضار"
            }else if(navController.currentDestination?.id==R.id.ordersBusyFragment){
                titleMain?.text="المشغولين"
            }else if(navController.currentDestination?.id==R.id.ordersResevedFragment){
                titleMain?.text="تم أستلامها"
            }else if(navController.currentDestination?.id==R.id.orderDetailsFragment){
                titleMain?.text="تفاصيل الطلب"

            }else if(navController.currentDestination?.id==R.id.ordersCancelFragment){
                titleMain?.text="المرفوضة"
            }else if(navController.currentDestination?.id==R.id.ordersDeleverdFragment){
                titleMain?.text="تم تسليمها الى المكتب"
            }else if(navController.currentDestination?.id==R.id.profileFragment2){
                titleMain?.text="الحساب"
            }else if(navController.currentDestination?.id==R.id.login){

            }


        }
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }
//    private fun setupNavigation(navController: NavController) {
//        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
//        sideNavView?.setupWithNavController(navController)
//        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)
//
//        //fragments load from here but how ?
////        appBarConfiguration = AppBarConfiguration(
////            setOf(R.id.ordersFragment2, R.id.mapFragment),
////            drawerLayout
////        )
//    }
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val retValue = super.onCreateOptionsMenu(menu)
//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//        if (navigationView == null) {
//            //android needs to know what menu I need
//            menuInflater.inflate(R.menu.bottom_nav, menu)
//            return true
//        }
//        return retValue
//    }
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        //I need to open the drawer onClick
//        when (item!!.itemId) {
//            android.R.id.home ->
//                drawer_layout.openDrawer(GravityCompat.END)
//        }
//        return item.onNavDestinationSelected(findNavController(R.id.navHost))
//                || super.onOptionsItemSelected(item)
//    }
//
//    override fun onBackPressed() {
//        //the code is beautiful enough without comments
//        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
//            drawer_layout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }

//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

//
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//
//        var id=item.itemId
//        if(id==R.id.waiting){
//            this?.toast("waitting  ldldl")
//        }
//        return true
//    }

    var drawer:DrawerLayout?=null

    @SuppressLint("WrongConstant")
    private fun setupNavigation() {
        drawer = findViewById(R.id.drawer_layout)

        val header: View = nav_view.getHeaderView(0)
        var mNameTextView = header.findViewById<View>(R.id.driverTitle) as TextView
        mNameTextView.setText(cacheObj.name)

        var photo = header.findViewById<View>(R.id.drwarImage) as CircleImageView

        this?.let {
            Glide.with(it).load(MainActivity.constant.base +cacheObj.photo)
                .placeholder(R.drawable.logo)
                .into(photo)
        }

        val locale =
            applicationContext.resources.configuration.locale.language
       // setSupportActionBar(toolbar)
        nav_view.menu.get(7).setOnMenuItemClickListener {
            val dview: View =View.inflate(this, R.layout.pop_dilog, null)
            val builder = this?.let { AlertDialog.Builder(it).setView(dview) }
            val malert= builder?.show()
            malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dview?.descPart?.text="هل تريد تسجيل الخروج؟"
            dview.conform?.setOnClickListener {
                cacheObj.token=""
                if (drawer!!.isDrawerOpen(Gravity.END)) {
                    drawer!!.closeDrawer(Gravity.END);
                }else if(locale=="ar"){
                    drawer!!.closeDrawer(Gravity.START);
                }else{
                    drawer!!.closeDrawer(Gravity.END);
                }
                navController?.navigate(R.id.login)
                malert?.dismiss()
            }
        true}


        backBtn?.setOnClickListener {
            if (drawer!!.isDrawerOpen(Gravity.END)) {
                drawer!!.closeDrawer(Gravity.END);
            }else if(locale=="ar"){
            drawer!!.openDrawer(Gravity.START);
        }else{
            drawer!!.openDrawer(Gravity.END);
        }
        }

        navController = Navigation.findNavController(this, R.id.navHost)
         appBarConfiguration =
            AppBarConfiguration.Builder(navController.graph)
                .setDrawerLayout(drawer)
                .build()
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        val navView: NavigationView = findViewById(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)
    }

    @SuppressLint("WrongConstant")
    override fun onBackPressed() {

        if (navController.currentDestination?.id == R.id.login
            ||navController.currentDestination?.id == R.id.mapFragment) {
            finish()

            // do nothing
        }
        else {
            if (drawer!!.isDrawerOpen(Gravity.END)) {
                drawer!!.closeDrawer(Gravity.END);
            }
            super.onBackPressed()
        }
    }
}
