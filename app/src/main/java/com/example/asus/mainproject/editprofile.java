package com.example.asus.mainproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.asus.mainproject.dataModels.ProfileData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class editprofile extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 2000 ;
    private static final int CAMERA_REQUEST = 1888;

    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        circleImageView = findViewById(R.id.imageviewc);


        get_profile_data();

        get_user_photo();
    }

    private void get_profile_data()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail();

        database.getReference().child("user").child(email.replace(".","")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ProfileData data = dataSnapshot.getValue(ProfileData.class);
                
                System.out.println(   data.name );

                EditText fullname=findViewById(R.id.name);
                EditText mobilenumber=findViewById(R.id.mobilenumber);
                EditText dateofbrith=findViewById(R.id.dateofbrith);
                EditText email=findViewById(R.id.email);
                EditText password=findViewById(R.id.password);
                EditText confirmpassword =findViewById(R.id.confirmpassword);
                EditText address=findViewById(R.id.address);
                EditText city=findViewById(R.id.cities);
                EditText state=findViewById(R.id.states);

                fullname.setText(data.name);

                mobilenumber.setText(data.phone);
                dateofbrith.setText(data.brith);
                email.setText(data.email);
                password.setText(data.password);
                confirmpassword.setText(data.password);
                address.setText(data.address);
                city.setText(data.city);
                state.setText(data.state);

                if(data.gender.equals("Male"))
                {
                    RadioButton male_radio_btn = findViewById(R.id.male_radio_btn);

                    male_radio_btn.setChecked(true);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void save(View view) {
        EditText fullname=findViewById(R.id.name);
        EditText mobilenumber=findViewById(R.id.mobilenumber);
        EditText dateofbrith=findViewById(R.id.dateofbrith);
        EditText email=findViewById(R.id.email);
        EditText password=findViewById(R.id.password);
        EditText confirmpassword =findViewById(R.id.confirmpassword);
        EditText address=findViewById(R.id.address);
        EditText city=findViewById(R.id.cities);
        EditText state=findViewById(R.id.states);






        final String ad=address.getText().toString();
        final String fn=fullname.getText().toString();
        final String mn=mobilenumber.getText().toString();
        final String dfb=dateofbrith.getText().toString();
        final String e=email.getText().toString();
        final String p=password.getText().toString();
        final String cp=confirmpassword.getText().toString();
        final String c=city.getText().toString();
        final String s=state.getText().toString();

        RadioGroup gender_radio_group = findViewById(R.id.gender_radio_group);

        RadioButton selected_radio_btn = findViewById(gender_radio_group.getCheckedRadioButtonId());

        final String gender = selected_radio_btn.getText().toString();


        if(fn.length() <=4 )
        {
            Toast.makeText(editprofile.this , "name not valid" , Toast.LENGTH_SHORT).show();
            return;
        }


        if(mn.length() < 10  )
        {
            Toast.makeText(editprofile.this , "phone number  not valid" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(dfb.length() == 0 )
        {
            Toast.makeText(editprofile.this , "please enter date of birth" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches() )
        {
            Toast.makeText(editprofile.this , "please enter email" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(p.length() <=8)
        {
            Toast.makeText(editprofile.this , " password should contain atleast 8 characters" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(c.length() ==0 )
        {
            Toast.makeText(editprofile.this , "please enter city" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(s.length() ==0 )
        {
            Toast.makeText(editprofile.this , "please enter state" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(ad.length() ==0 )
        {
            Toast.makeText(editprofile.this , "please enter your address" , Toast.LENGTH_SHORT).show();
            return;
        }


        if(gender_radio_group.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(editprofile.this , "please check gender" , Toast.LENGTH_SHORT).show();
            return;
        }


        if( ! p.equals(cp))
        {
            Toast.makeText(editprofile.this , "password and confirm password do  not match" , Toast.LENGTH_SHORT).show();
            return;
        }
finish();





                    ProfileData data = new ProfileData(fn,mn,dfb,e,p,ad,c,s , gender);


                    FirebaseDatabase database = FirebaseDatabase.getInstance();


                    database.getReference().child("user").child(e.replace("." , "")).setValue(data);


                    FirebaseStorage storage = FirebaseStorage.getInstance();

                    circleImageView.setDrawingCacheEnabled(true);
                    circleImageView.buildDrawingCache();

                    Bitmap bitmap = circleImageView.getDrawingCache();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    byte[] data_ = baos.toByteArray();

                    UploadTask uploadTask = storage.getReference().child("profile_images").child(e.replace(".","")).putBytes(data_);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    });








    }
    public void takeImageFromCamera(View view) {

        final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(editprofile.this);
        builder.setTitle("Select Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    dialog.dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (options[item].equals("Choose From Gallery")) {
                    dialog.dismiss();
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, GALLERY_REQUEST);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            circleImageView.setImageBitmap(mphoto);
        }

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");



                circleImageView.setImageBitmap(rotate(bitmap , 90 ));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }



    private void get_user_photo()
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace(".","");

        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference();
        storageRef.child("profile_images/"+email).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(editprofile.this).load(uri).into(circleImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });


    }

    public void backes(View view) {
        finish();
    }
}
