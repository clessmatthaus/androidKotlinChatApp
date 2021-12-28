package fr.clesspavy

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import fr.clesspavy.thisismeapp.R



class DialActivity : AppCompatActivity() {

    private lateinit var dialogueRecyclerView: RecyclerView
    private lateinit var chatBox: EditText
    private lateinit var sendButton: LottieAnimationView
    private lateinit var dialogueAdapter: DialogueAdapter
    private lateinit var dialogueList: ArrayList<Dialogue>
    private lateinit var  dbRef: DatabaseReference

    var receiverBox: String? = null
    var senderBox: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dial)
            //val intent = Intent()

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        dbRef = FirebaseDatabase.getInstance().getReference()

        senderBox = receiverUid + senderUid
        receiverBox = senderUid + receiverUid

        supportActionBar?.title = name


        dialogueRecyclerView = findViewById(R.id.dialrecyclerview)
        chatBox = findViewById(R.id.chatbox)
        sendButton = findViewById(R.id.send_btn)
        dialogueList = ArrayList()
        dialogueAdapter = DialogueAdapter(this, dialogueList)

        dialogueRecyclerView.layoutManager = LinearLayoutManager(this)
        dialogueRecyclerView.adapter = dialogueAdapter

        dbRef.child("dials").child(senderBox!!).child("messages")
            .addValueEventListener(object: ValueEventListener {
                /**
                 * This method will be called with a snapshot of the data at this location. It will also be called
                 * each time that data changes.
                 *
                 * @param snapshot The current data at the location
                 */
                override fun onDataChange(snapshot: DataSnapshot) {

                    dialogueList.clear()

                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Dialogue::class.java)
                        dialogueList.add(message!!)
                    }
                    dialogueAdapter.notifyDataSetChanged()
                }

                /**
                 * This method will be triggered in the event that this listener either failed at the server, or
                 * is removed as a result of the security and Firebase Database rules. For more information on
                 * securing your data, see: [ Security
 * Quickstart](https://firebase.google.com/docs/database/security/quickstart)
                 *
                 * @param error A description of the error that occurred
                 */
                override fun onCancelled(error: DatabaseError) {

                }


            })
        sendButton.setOnClickListener {
            val dialogue = chatBox.text.toString()
            val dialogueObject = Dialogue(dialogue, senderUid)

            dbRef.child("dials").child(senderBox!!).child("messages").push()
                .setValue(dialogueObject).addOnSuccessListener {
                  dbRef.child("dials").child(receiverBox!!).child("messages").push()
                      .setValue(dialogueObject)
                }
            chatBox.setText("")
       }
    }
}