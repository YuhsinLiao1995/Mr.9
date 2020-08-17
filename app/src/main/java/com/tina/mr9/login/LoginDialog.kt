package com.tina.mr9.login

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.tina.mr9.MainActivity
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.databinding.DialogLoginBinding
import com.tina.mr9.ext.showToast
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class LoginDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = LoginDialog()
    }

    private lateinit var viewModel: LoginDialogViewModel

    var auth : FirebaseAuth? = null
    var googleSignInClient : GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9001
    var callbackManager : CallbackManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DialogLoginBinding.inflate(inflater, container, false)


        binding.emailLoginButton.setOnClickListener {
//            signinAndSignup()
        }
        binding.googleSignInButton.setOnClickListener {
            //First step
            googleLogin()
        }
        binding.facebookLoginButton.setOnClickListener {
            //First step
//            facebookLogin()
        }
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("973705262050-a5cbp8np9f2n73v660m2cijuiqvoeoc0.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity as MainActivity,gso)
        //printHashKey()
        callbackManager = CallbackManager.Factory.create()


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginDialogViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode,resultCode,data)
        if(requestCode == GOOGLE_LOGIN_CODE){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                var account = result.signInAccount
                //Second step
                firebaseAuthWithGoogle(account)
            }
        }
    }

    fun googleLogin(){
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent,GOOGLE_LOGIN_CODE)
    }

    fun firebaseAuthWithGoogle(account : GoogleSignInAccount?){
        var credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    //Login
//                    moveMainPage(task.result?.user)
                }else{
                    //Show the error message
                    task.exception?.message?.let { activity.showToast(it) }

                }
            }
    }

    fun moveMainPage(user: FirebaseUser?){
        if(user != null){
            startActivity(Intent(Mr9Application.appContext, MainActivity::class.java))
//            finish()
        }
    }

}