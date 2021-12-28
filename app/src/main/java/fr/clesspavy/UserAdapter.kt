package fr.clesspavy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.clesspavy.thisismeapp.R
import java.util.*

class UserAdapter(val context: Context, val usersList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
     val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = usersList[position]
        holder.textName.text = currentUser.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DialActivity::class.java)

            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid",currentUser.uid)

            context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int {
       return usersList.size
    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.user_name)


    }
}