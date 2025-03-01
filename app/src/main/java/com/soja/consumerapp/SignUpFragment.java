package com.soja.consumerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpFragment extends Fragment {
    FirebaseAuth auth;
    Button signup;
    EditText email, pass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        auth = FirebaseAuth.getInstance();
        signup = root.findViewById(R.id.signUp);
        email = root.findViewById(R.id.et_email);
        pass = root.findViewById(R.id.et_password);

        signup.setOnClickListener(v->{

            String txt_Email = email.getText().toString();
            String txt_Pass = pass.getText().toString();

            if(txt_Email.isEmpty() || txt_Pass.isEmpty())
            {
                Toast.makeText(getContext(), "Please fill all the fields!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                auth.createUserWithEmailAndPassword(txt_Email,txt_Pass).addOnCompleteListener(task->{

                    if(task.isSuccessful())
                    {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null)
                        {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName("John Doe")  // Set Display Name
                                    .build();

                            user.updateProfile(profileUpdates);

                            Toast.makeText(getContext(), "Signed Up!", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_signup_to_home);
                            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "This Account Already Exists or Invalid", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return root;
    }
}