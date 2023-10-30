package com.example.projet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAdapter extends FirebaseRecyclerAdapter<OfferInscrits, MyAdapter.MyViewHolder> {

    private DatabaseReference database;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private Context context;

    public MyAdapter(@NonNull FirebaseRecyclerOptions<OfferInscrits> options, Context context) {
        super(options);
        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.inscrits_offer_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull OfferInscrits offer) {
        holder.titleTextView.setText(offer.getName());
        holder.descriptionTextView.setText(offer.getDescription());

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersReference = databaseReference.child("users");
        DatabaseReference typeOfCurrentUserRef = usersReference.child(currentUser.getUid()).child("typeOfUser");
        typeOfCurrentUserRef.addValueEventListener(new ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(DataSnapshot dataSnapshot) {
                                                           String typeOfUser = dataSnapshot.getValue(String.class);

                                                           if (typeOfUser.equals("Registered")) {
                                                               holder.itemView.setOnClickListener(new View.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(View view) {
                                                                       // Create a dialog with the custom layout
                                                                       AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                                                                       // Inflate the custom layout
                                                                       View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.a_inscrit_offre_click, null);
                                                                       TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
                                                                       TextView descriptionTextView = dialogView.findViewById(R.id.descriptionTextView);
                                                                       TextView jobTextView = dialogView.findViewById(R.id.jobTextView);
                                                                       TextView salaryTextView = dialogView.findViewById(R.id.salaryTextView);
                                                                       TextView categoryTextView = dialogView.findViewById(R.id.categoryTextView);
                                                                       TextView periodTextView = dialogView.findViewById(R.id.periodTextView);
                                                                       Button backButton = dialogView.findViewById(R.id.backButton);
                                                                       Button applyButton = dialogView.findViewById(R.id.applyButton);

                                                                       // Set the offer details in the dialog
                                                                       titleTextView.setText(offer.getName());
                                                                       descriptionTextView.setText(offer.getDescription());
                                                                       jobTextView.setText(offer.getJob());
                                                                       salaryTextView.setText(offer.getSalary());
                                                                       categoryTextView.setText(offer.getCategory());
                                                                       periodTextView.setText(offer.getPeriod());

                                                                       // Set the custom view in the dialog
                                                                       builder.setView(dialogView);

                                                                       // Create and show the dialog
                                                                       AlertDialog dialog = builder.create();
                                                                       dialog.show();

                                                                       // Set a click listener for the back button
                                                                       backButton.setOnClickListener(new View.OnClickListener() {
                                                                           @Override
                                                                           public void onClick(View v) {
                                                                               // Dismiss the dialog when the back button is clicked
                                                                               dialog.dismiss();
                                                                           }
                                                                       });

                                                                       // Set a click listener for the apply button
                                                                       applyButton.setOnClickListener(new View.OnClickListener() {
                                                                           @Override
                                                                           public void onClick(View v) {
                                                                               // Save the user's application in the database
                                                                               String offerId = getRef(position).getKey();
                                                                               String userId = currentUser.getUid();
                                                                               String userEmail = currentUser.getEmail();
                                                                               saveApplication(offerId, userId, userEmail, offer.getName(), offer.getJob(), view);

                                                                               // Display a success message
                                                                               Toast.makeText(v.getContext(), "Application submitted successfully", Toast.LENGTH_SHORT).show();

                                                                               // Dismiss the dialog
                                                                               dialog.dismiss();
                                                                           }
                                                                       });
                                                                   }
                                                               });
                                                           }
                                                       }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveApplication(String offerId, String userId, String userEmail, String offerName, String job, View itemView) {
        if (offerId != null && userId != null) {
            DatabaseReference candidaturesRef = database.child("candidatures");

            // Create a new child under "candidatures" with a unique ID
            DatabaseReference newCandidatureRef = candidaturesRef.push();

            // Set the values for the candidature
            newCandidatureRef.child("offerId").setValue(offerId);
            newCandidatureRef.child("userId").setValue(userId);
            newCandidatureRef.child("userEmail").setValue(userEmail);
            newCandidatureRef.child("offerName").setValue(offerName);
            newCandidatureRef.child("job").setValue(job);
        } else {
            Toast.makeText(itemView.getContext().getApplicationContext(), "Error: Offer ID or User ID is null", Toast.LENGTH_SHORT).show();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
