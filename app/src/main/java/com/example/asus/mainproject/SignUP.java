package com.example.asus.mainproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public  class SignUP extends AppCompatActivity {

    ImageView btn;
    EditText editText_et;
    int year_x,month_x,day_x;
    static final int DAILOG_ID=0;

    private static final int CAMERA_REQUEST = 1888;

    CircleImageView circleImageView;


    private DatePickerDialog.OnDateSetListener dpickerListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            year_x=i;
            month_x=i1+1;
            day_x=i2;
            editText_et=findViewById(R.id.dateofbrith);
            editText_et.setText(String.valueOf(day_x +"/"+month_x +"/"+ year_x +""));

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        circleImageView = findViewById(R.id.imageviewc);

        read_profile();

    }

    public void register(View view) {
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
            Toast.makeText(SignUP.this , "name not valid" , Toast.LENGTH_SHORT).show();
            return;
        }


        if(mn.length() < 10  )
        {
            Toast.makeText(SignUP.this , "phone number  not valid" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(dfb.length() == 0 )
        {
            Toast.makeText(SignUP.this , "please enter date of birth" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(Patterns.EMAIL_ADDRESS.matcher(e).matches() )
        {
            Toast.makeText(SignUP.this , "please enter email" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(p.length() <=8)
        {
            Toast.makeText(SignUP.this , " password should contain atleast 8 characters" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(c.length() ==0 )
        {
            Toast.makeText(SignUP.this , "please enter city" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(s.length() ==0 )
        {
            Toast.makeText(SignUP.this , "please enter state" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(ad.length() ==0 )
        {
            Toast.makeText(SignUP.this , "please enter your address" , Toast.LENGTH_SHORT).show();
            return;
        }


        if(gender_radio_group.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(SignUP.this , "please check gender" , Toast.LENGTH_SHORT).show();
            return;
        }


        if( ! p.equals(cp))
        {
            Toast.makeText(SignUP.this , "password and confirm password do  not match" , Toast.LENGTH_SHORT).show();
            return;
        }




        FirebaseAuth auth = FirebaseAuth.getInstance();

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                System.out.println(String.valueOf(task.getException()));
                if(task.isSuccessful())
                {

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

                    Intent i=new Intent(SignUP.this,Homes.class);
                    startActivity(i);


                }

                else
                {


                    Toast.makeText(SignUP.this , "email already exist" , Toast.LENGTH_SHORT).show();
                }
            }


        };

        auth.createUserWithEmailAndPassword(e , p).addOnCompleteListener(listener);

    }

    public void read_profile(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProfileData recieved=dataSnapshot.getValue(ProfileData.class);
           //     System.out.println(recieved.address);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database.getReference().child("user").child("uhhuhguhuk".replace("." , "")).addValueEventListener(eventListener);           ;

    }

    public void calandar(View view) {
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DAILOG_ID);

            }
        };
        btn=findViewById(R.id.calandar);
        btn.setOnClickListener(listener);

        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    protected Dialog onCreateDialog (int id){
        if (id==DAILOG_ID){
            return new DatePickerDialog(SignUP.this, dpickerListener, year_x,month_x,day_x);
        }
        return null;
    }


    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            circleImageView.setImageBitmap(mphoto);
        }
    }

}
