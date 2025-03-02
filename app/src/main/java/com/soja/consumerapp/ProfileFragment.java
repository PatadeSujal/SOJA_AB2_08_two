package com.soja.consumerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        View root = null;

        if(auth.getCurrentUser()==null)
        {
            root = inflater.inflate(R.layout.fragment_profile_unlogined, container, false);

            Button login = root.findViewById(R.id.login);
            Button signup = root.findViewById(R.id.signup);

            login.setOnClickListener(v->
            {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_profile_to_login);
            });
            signup.setOnClickListener(v->
            {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_profile_to_signup);
            });
        }
        else
        {
            root = inflater.inflate(R.layout.fragment_profile, container, false);

            Button logout = root.findViewById(R.id.logout);
            TextView name = root.findViewById(R.id.name);

            name.setText(auth.getCurrentUser().getDisplayName());

            logout.setOnClickListener(v->{

                auth.signOut();
                Toast.makeText(getContext(), "Signed Out!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_profile_to_home);

            });
        }

        return root;
    }
}