package com.tina.mr9.friends

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.tina.mr9.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
//class BlankFragment : Fragment() {
//
//    lateinit var mSearchText : EditText
//    lateinit var mRecyclerView : RecyclerView
//
//    lateinit var mDatabase : DatabaseReference
//
//    lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<User , UsersViewHolder>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        mSearchText =findViewById(R.id.searchText)
//        mRecyclerView = findViewById(R.id.list_view)
//
//
//        mDatabase = FirebaseDatabase.getInstance().getReference("Users")
//
//
//        mRecyclerView.setHasFixedSize(true)
//        mRecyclerView.setLayoutManager(LinearLayoutManager(this))
//
//
//
//
//        mSearchText.addTextChangedListener(object  : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                val searchText = mSearchText.getText().toString().trim()
//
//                loadFirebaseData(searchText)
//            }
//        } )
//
//    }
//
//    private fun loadFirebaseData(searchText : String) {
//
//        if(searchText.isEmpty()){
//
//            FirebaseRecyclerAdapter.cleanup()
//            mRecyclerView.adapter = FirebaseRecyclerAdapter
//
//        }else {
//
//
//            val firebaseSearchQuery = mDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff")
//
//            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<User, UsersViewHolder>(
//
//                User::class.java,
//                R.layout.layout_list,
//                UsersViewHolder::class.java,
//                firebaseSearchQuery
//
//
//            ) {
//                override fun populateViewHolder(viewHolder: UsersViewHolder, model: User?, position: Int) {
//
//
//                    viewHolder.mview.userName.setText(model?.name)
//                    viewHolder.mview.userStatus.setText(model?.status)
//                    Picasso.with(applicationContext).load(model?.image).into(viewHolder.mview.UserImageView)
//
//                }
//
//            }
//
//            mRecyclerView.adapter = FirebaseRecyclerAdapter
//
//        }
//    }
//
//
//    // // View Holder Class
//
//    class UsersViewHolder(var mview : View) : RecyclerView.ViewHolder(mview) {
//
//    }

