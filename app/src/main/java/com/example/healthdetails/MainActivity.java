package com.example.healthdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText dobEditText;
    private EditText ageEditText;
    private EditText emailEditText;
    private TextView emailValidationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the EditText fields
        EditText nameEditText = findViewById(R.id.names);
        EditText currentWeightEditText = findViewById(R.id.email);

        EditText goalWeightEditText = findViewById(R.id.DOB);
        EditText ageEditText = findViewById(R.id.age);
        EditText phoneEditText = findViewById(R.id.Phone);
        EditText addressEditText = findViewById(R.id.address);

        // Find the CheckBox
        android.widget.CheckBox conditionsCheckBox = findViewById(R.id.conditions);

        // Find the RadioGroup
        android.widget.RadioGroup radioGroup = findViewById(R.id.radioGroup);

        // Find the Date of Birth EditText
        dobEditText = findViewById(R.id.DOB);
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Find the Age EditText
        ageEditText = findViewById(R.id.age);

        // Find the email EditText and validation TextView
        emailEditText = findViewById(R.id.email);
        emailValidationTextView = findViewById(R.id.emailValidationTextView);

        // Set an onClickListener for the submit button
        Button submitButton = findViewById(R.id.button);
        EditText finalAgeEditText = ageEditText;
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the email EditText
                String email = emailEditText.getText().toString();

                // Validate the email
                if (!isValidEmail(email)) {
                    // Email is not valid, show an error message
                    Toast.makeText(MainActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Retrieve the phone number EditText
                String phoneNumber = phoneEditText.getText().toString();

                // Validate the phone number using regex
                if (!isValidPhoneNumber(phoneNumber)) {
                    // Phone number is not valid, show an error message
                    Toast.makeText(MainActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Retrieve the date of birth EditText
                String dobStr = dobEditText.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dob = null;
                try {
                    dob = sdf.parse(dobStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }

                // Calculate age
                int age = calculateAge(dob);

                // Set the age in the ageEditText
                finalAgeEditText.setText(String.valueOf(age));

                // Check if the conditions checkbox is checked
                if (!conditionsCheckBox.isChecked()) {
                    // Conditions checkbox is not checked, show an error message
                    Toast.makeText(MainActivity.this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Display details in a popup
                showDetailsPopup(nameEditText.getText().toString(), email, phoneNumber, dobStr, String.valueOf(age));
            }
        });
    }

    private void showDatePickerDialog() {
        // Get current date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display selected date in EditText
                        dobEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression to validate a phone number
        String phoneRegex = "^(?:\\+?\\d{1,3}[- ]?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$";
        return phoneNumber.matches(phoneRegex);
    }

    private int calculateAge(Date dob) {
        Calendar dobCal = Calendar.getInstance();
        dobCal.setTime(dob);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    private boolean isValidEmail(String email) {
        // Simple email validation using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private void showDetailsPopup(String name, String email, String phone, String dob, String age) {
        // Create a dialog with custom layout
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.popup_layout);

        // Set text views with user details
        TextView nameTextView = dialog.findViewById(R.id.names);
        nameTextView.setText("Name: " + name);

        TextView emailTextView = dialog.findViewById(R.id.email);
        emailTextView.setText("Email: " + email);

        TextView phoneTextView = dialog.findViewById(R.id.Phone);
        phoneTextView.setText("Phone: " + phone);

        TextView dobTextView = dialog.findViewById(R.id.DOB);
        dobTextView.setText("Date of Birth: " + dob);

        TextView ageTextView = dialog.findViewById(R.id.age);
        ageTextView.setText("Age: " + age);

        // Show the dialog
        dialog.show();
    }

    // Handle radio button clicks
    public void radioButtonhandler(View view) {
        // Handle radio button clicks here
    }

    // Handle submit button click
    public void submitbuttonHandler(View view) {
        // Handle submit button click here
    }
}
