package com.technado.validationdemo

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.Order
import com.mobsandgeeks.saripaar.annotation.Password
import com.technado.validationdemo.databinding.MainActivityBinding

class MainActivity : AppCompatActivity(), Validator.ValidationListener {
    var binding: MainActivityBinding? = null

    @Order(1)
    @Email
    var email: EditText? = null

    @Order(2)
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC)
    var passwordEditText: EditText? = null

    @Order(3)
    @ConfirmPassword
    var confirmPasswordEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        passwordEditText = binding?.edtPassword
        confirmPasswordEditText = binding?.edtConfirmPassword
        email = binding?.edtEmail

        val validator = Validator(this)
        validator.setValidationListener(this)

        binding?.btnValidate?.setOnClickListener(View.OnClickListener {
            validator.validate()
        })
    }

    override fun onValidationSucceeded() {
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error in errors!!) {
            val view: View = error.view
            val message = error.getCollatedErrorMessage(this)
            if (view is EditText) {
                view.setError(message)
                view.requestFocus()
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}