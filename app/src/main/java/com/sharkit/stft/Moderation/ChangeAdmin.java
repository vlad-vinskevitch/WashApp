package com.sharkit.stft.Moderation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.stft.Notification.ToastComplete;
import com.sharkit.stft.R;
import com.sharkit.stft.settings.Validation;
import com.sharkit.stft.ui.Admin;
import com.sharkit.stft.ui.StaticAdmin;

public class ChangeAdmin extends Fragment {
    private Button register;
    private EditText name;
    private Spinner role;
    private String nameFirm;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.change_admin, container, false);
        findView(root);
        setText();
        return root;
    }

    private void setText() {
        Admin admin = new Admin();
        admin.setPassword(StaticAdmin.getPassword());
        admin.setRole(StaticAdmin.getRole());
        admin.setName(StaticAdmin.getName());
        admin.setEmail(StaticAdmin.getEmail());
        nameFirm = StaticAdmin.getName();


        name.setText(admin.getName());
        register.setOnClickListener(v -> {
            if (Validation.isValidatorName(name.getText().toString())){
                try {
                    throw new ToastComplete(getContext(), "Назва фірми не повина містити симфолів");
                } catch (ToastComplete toastComplete) {
                    toastComplete.printStackTrace();
                }
                return;
            }
            admin.setName(name.getText().toString());
            admin.setRole(role.getSelectedItem().toString());
            changeAdmin(admin);
        });
    }

    private void changeAdmin(Admin admin) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Admins")
                .document(nameFirm)
                .delete();
        db.collection("Admins")
                .document(admin.getName())
                .set(admin)
                .addOnSuccessListener(unused -> {
                    NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
                    navController.navigate(R.id.nav_moderation_list);
                });
    }


    private void findView(View root) {

        register = root.findViewById(R.id.register_xml);
        name = root.findViewById(R.id.name_xml);
        role = root.findViewById(R.id.role_xml);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.level_access,R.layout.support_simple_spinner_dropdown_item);
        role.setAdapter(adapter);
    }
}
