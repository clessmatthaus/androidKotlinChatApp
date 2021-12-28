package fr.clesspavy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import fr.clesspavy.thisismeapp.R

class DialogueAdapter(val context: Context, val dialogueList: ArrayList<Dialogue>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val MSG_RECEIVE = 1
    val MSG_SENT = 2

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentDialogue = itemView.findViewById<TextView>(R.id.sent_msg)
    }
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveDialogue = itemView.findViewById<TextView>(R.id.receive_msg)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if(viewType == 1){
           val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
           return ReceiveViewHolder(view)
       }else{
           val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
           return SentViewHolder(view)
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentDialogue = dialogueList[position]

        if(holder.javaClass == SentViewHolder::class.java){

           val viewHolder = holder as SentViewHolder
           holder.sentDialogue.text = currentDialogue.dialogue
       }else{
           val viewHolder = holder as ReceiveViewHolder
           holder.receiveDialogue.text = currentDialogue.dialogue
       }
    }

    override fun getItemViewType(position: Int): Int {
        val currentDialogue = dialogueList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentDialogue.senderId)){
            return  MSG_SENT
        }else{
            return MSG_RECEIVE
        }
    }

    override fun getItemCount(): Int {
       return  dialogueList.size
    }
}