package com.example.projet;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Inscrit_Tchat_Click extends Fragment {

    private String userId;
    private ArrayAdapter<String> adapter;
    private List messageList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_inscrit_tchat_click, container, false);

        Bundle bundle = getArguments();
        String userName = bundle.getString("userName");
        String name = bundle.getString("name");
        String conversationId = bundle.getString("conversationId");

        TextView textViewName = view.findViewById(R.id.name);
        textViewName.setText(name);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("tchat").child(conversationId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);

                    if ((message.sender).equals(currentUserId)) {
                        messageList.add("\n" + userName + " : " + "\n\n" + message.text + "\n");
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        messageList.add("\n" + name + " : " + "\n\n" + message.text + "\n");
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Failed to read value.", databaseError.toException());
            }
        });


        ListView listView = view.findViewById(R.id.tchatList);
        listView.setSelector(android.R.color.transparent);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, messageList);
        listView.setAdapter(adapter);

        TextInputLayout textLayout = view.findViewById(R.id.text);
        EditText editText = textLayout.getEditText();

        ImageView send = view.findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = editText.getText().toString();

                if (TextUtils.isEmpty(text)) {
                    textLayout.setError("Obligatoire");
                    textLayout.requestFocus();
                    return;
                } else {
                    textLayout.setError(null);

                    Message message = new Message(text, currentUserId);
                    ref.push().setValue(message);
                    editText.setText("");

                }

            }
        });

        return view;
}
}