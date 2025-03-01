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

public class LoginFragment extends Fragment {

    FirebaseAuth auth;
    Button login;
    EditText email, pass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        auth = FirebaseAuth.getInstance();
        login = root.findViewById(R.id.login);
        email = root.findViewById(R.id.et_email);
        pass = root.findViewById(R.id.et_password);

        login.setOnClickListener(v->{

        String txt_Email = email.getText().toString();
        String txt_Pass = pass.getText().toString();
        //"abcde123"

        if(txt_Email.isEmpty() || txt_Pass.isEmpty())
        {
            Toast.makeText(getContext(), "Please fill all the fields!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            auth.signInWithEmailAndPassword(txt_Email,txt_Pass).addOnCompleteListener(task->{

                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "signed in!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_login_to_home);
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }
                else
                {
                    Toast.makeText(getContext(), "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                }
            });
        }



        });

        return root;
    }
}