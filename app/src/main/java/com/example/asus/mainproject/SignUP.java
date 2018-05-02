package com.example.asus.mainproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.mainproject.dataModels.ProfileData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.victor.loading.book.BookLoading;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.IllegalFormatCodePointException;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public  class SignUP extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 2000 ;
    TextView show_password;
    EditText password;
    ImageView btn;
    Button register;
    EditText editText_et;
    int year_x,month_x,day_x;
    static final int DAILOG_ID=0;
    BookLoading bk;

    ProgressDialog pd ;

    private static final int CAMERA_REQUEST = 1888;

    CircleImageView circleImageView;

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;


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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        pd = new ProgressDialog(SignUP.this);

        pd.setTitle("Loading ...");
        pd.setMessage("Please wait ..");


        circleImageView = findViewById(R.id.imageviewc);

        password=findViewById(R.id.password);


        show_password=findViewById(R.id.show_password);
        show_password.setVisibility(View.GONE);



        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (password.getText().length()>0){
                    show_password.setVisibility(View.VISIBLE);
                }
                else {
                    show_password.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (show_password.getText()=="Show"){
                    show_password.setText("Hide");
                    password.setTransformationMethod(null);

                    password.setSelection(password.length());
                }
                else{
                    show_password.setText("Show");

                    password.setTransformationMethod(new PasswordTransformationMethod());


                    password.setSelection(password.length());


                }

            }
        });



    }

    public void register(View view) {




        EditText fullname=findViewById(R.id.name);
        EditText mobilenumber=findViewById(R.id.mobilenumber);
        EditText dateofbrith=findViewById(R.id.dateofbrith);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);

        EditText email=findViewById(R.id.email);
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
            fullname.setError("name must be of minimum 4 characters");
            return;
        }


        if(mn.length() < 10  )
        {
           mobilenumber.setError("phone number  not valids");

            return;

        }

        if(dfb.length() == 0 )
        {
            dateofbrith.setError( "please enter date of birth" );
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches() )
        {
            email.setError( "please enter email" );

            return;
        }
        if(p.length() <=8)
        {
            password.setError( "password should contain atleast 8 characters" );
           return;
        }
        if(c.length() ==0 )
        {
            city.setError( "please enter city" );


            return;
        }
        if(s.length() ==0 )
        {
           state.setError( "please enter  state" );

            return;
        }
        if(ad.length() ==0 )
        {
           address.setError( "please enter  your address" );

            return;
        }


        if(gender_radio_group.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(SignUP.this , "please check gender" , Toast.LENGTH_SHORT).show();
            return;
        }


        if( ! p.equals(cp))

        {
            confirmpassword.setError( "password and confirm password do  not match" );

            return;
        }




        pd.show();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                sendCode(mn);

                
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

                    pd.hide();


                   /* Intent i=new Intent(SignUP.this,Homes.class);
                    startActivity(i);

                    finish();*/


                }

                else
                {


                    Toast.makeText(SignUP.this , "email already exist" , Toast.LENGTH_SHORT).show();
                }
            }


        };

        auth.createUserWithEmailAndPassword(e , p).addOnCompleteListener(listener);

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
            DatePickerDialog dpd = new  DatePickerDialog(SignUP.this, dpickerListener, year_x,month_x,day_x);

            long curr_time = System.currentTimeMillis();

            dpd.getDatePicker().setMaxDate(curr_time);

            return dpd;
        }
        return null;
    }


    public void takeImageFromCamera(View view) {

        final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SignUP.this);
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

    public void sendCode(String phoneNumber) {

        System.out.println("phone to verify is "+phoneNumber);

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

                        Toast.makeText(SignUP.this , "verified" , Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {


                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        new VerifyCodeDialog(SignUP.this).show();

                    }
                };



        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);



    }

}
