package com.example.projet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Employeur_Tchat_Home extends Fragment {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> userList = new ArrayList<>();

    private String conversationIDS;
    String name = "";
    String typeOfUser = "";

    Map<String, String> associations;

    private String userName = null;
    private ArrayList<String> userNameList;

    public static Employeur_Tchat_Home newInstance() {
        Employeur_Tchat_Home fragment = new Employeur_Tchat_Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_inscrit_tchat_home, container, false);

        associations = new HashMap<>();
        userNameList = new ArrayList<>();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("tchat");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {

                    conversationIDS = child.getKey();

                    String[] userIds = conversationIDS.split("-");

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference usersReference = databaseReference.child("users");


                    DatabaseReference typeOfCurrentUserRef = usersReference.child(currentUserId).child("typeOfUser");

                    typeOfCurrentUserRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            typeOfUser = dataSnapshot.getValue(String.class);

                            if (typeOfUser.equals("EmploymentAgency") || typeOfUser.equals("Employer")) {
                                DatabaseReference nameRef = usersReference.child(currentUserId).child("name1");

                                nameRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        userName = dataSnapshot.getValue(String.class);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                    }
                                });
                            } else if (typeOfUser.equals("Registered")) {
                                DatabaseReference nameRef = usersReference.child(currentUserId).child("name");
                                nameRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        userName = dataSnapshot.getValue(String.class);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });


                    if (currentUserId.equals(userIds[0])) {
                        DatabaseReference typeOfUserRef = usersReference.child(userIds[1]).child("typeOfUser");

                        typeOfUserRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                typeOfUser = dataSnapshot.getValue(String.class);

                                if (typeOfUser.equals("EmploymentAgency") || typeOfUser.equals("Employer")) {
                                    DatabaseReference nameRef = usersReference.child(userIds[1]).child("name1");

                                    nameRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            name = dataSnapshot.getValue(String.class);

                                            associations.put(name, userIds[0] + "-" + userIds[1]);

                                            userList.add(name);
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }
                                    });
                                } else if (typeOfUser.equals("Registered")) {
                                    DatabaseReference nameRef = usersReference.child(userIds[1]).child("name");
                                    nameRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            name = dataSnapshot.getValue(String.class);

                                            associations.put(name, userIds[0] + "-" + userIds[1]);

                                            userList.add(name);
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                            }
                        });

                    } else if (currentUserId.equals(userIds[1])) {

                        DatabaseReference typeOfUserRef = usersReference.child(userIds[0]).child("typeOfUser");
                        typeOfUserRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                typeOfUser = dataSnapshot.getValue(String.class);

                                if (typeOfUser.equals("EmploymentAgency") || typeOfUser.equals("Employer")) {
                                    DatabaseReference nameRef = usersReference.child(userIds[0]).child("name1");
                                    nameRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            name = dataSnapshot.getValue(String.class);

                                            associations.put(name, userIds[0] + "-" + userIds[1]);

                                            userList.add(name);
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }
                                    });
                                } else if (typeOfUser.equals("Registered")) {
                                    DatabaseReference nameRef = usersReference.child(userIds[0]).child("name");
                                    nameRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            name = dataSnapshot.getValue(String.class);

                                            associations.put(name, userIds[0] + "-" + userIds[1]);

                                            userList.add(name);
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.tchatList);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, userList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemName = (String) parent.getItemAtPosition(position);

                String itemConversationId = associations.get(itemName);

                ((Employeur_Main) getActivity()).setConversation(userName, itemName, itemConversationId);
            }
        });

        FloatingActionButton addButton = view.findViewById(R.id.plus);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Employeur_Main) getActivity()).setCreateConversation();
            }
        });

        return view;
    }
}