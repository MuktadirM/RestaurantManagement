package com.example.restaurantmanagement.views.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.RequestManager;
import com.example.restaurantmanagement.databinding.FragmentEditProfileViewBinding;
import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.auth.AuthViewModel;
import com.example.restaurantmanagement.views.models.UserProfile;
import com.example.restaurantmanagement.views.models.mapping.ProfileToUserProfile;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.example.restaurantmanagement.utils.Constrains.EDIT_PROFILE;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link EditProfileView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileView extends DaggerFragment {
    private FragmentEditProfileViewBinding binding;
    private AuthViewModel viewModel;
    private final ProfileToUserProfile toUserProfile = new ProfileToUserProfile();
    private Profile profile;
    private UserProfile editedProfile;

    private int fileSizeCheck = 0;
    private Uri FILE_URL;
    private String ImageStoreName;
    private NavController navController;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    public EditProfileView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditProfileView.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileView newInstance() {
        return new EditProfileView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            editedProfile = getArguments().getParcelable(EDIT_PROFILE);
        }
        viewModel = new ViewModelProvider(this,providerFactory).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileViewBinding.inflate(inflater,container,false);

        binding.setProfile(editedProfile);
        binding.uploadImage.setOnClickListener(v->{
            pickFile();
        });
        binding.updateProfile.setOnClickListener(v->{
            if(FILE_URL != null){
                processFileToUpload();
            }
            else {
                saveWithoutImage();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void processFileToUpload() {
        if (FILE_URL != null) {
            viewModel.uploadImage(FILE_URL, getFileExtension(FILE_URL)).observe(getViewLifecycleOwner(),
                    imageUploadResource -> {
                        if(imageUploadResource != null){
                            switch (imageUploadResource.status){
                                case SUCCESS:
                                    assert imageUploadResource.data != null;
                                    if(imageUploadResource.data.isUploaded()){
                                        saveInfoWithImage(imageUploadResource.data.getImage());
                                        Toast.makeText(getContext(),"Image Upload completed",Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case ERROR:
                                    Toast.makeText(getContext(),"Can't upload image",Toast.LENGTH_LONG).show();
                                case LOADING:
                                    break;
                            }
                        }
                    });
        }

    }

    private void saveWithoutImage() {
        profile = toUserProfile.toModel(editedProfile);
        profile.setName(binding.name.getText().toString().trim());
        profile.setEmail(binding.email.getText().toString().trim());
        profile.setPhone(binding.phone.getText().toString().trim());
        profile.setAddress(binding.address.getText().toString().trim());
        profile.setIc(binding.ic.getText().toString().trim());
        viewModel.updateProfile(profile).observe(getViewLifecycleOwner(), profileResource -> {
            if(profileResource !=null){
                switch (profileResource.status) {
                    case SUCCESS:
                        updateCompleted();
                        break;
                    case ERROR:
                        Toast.makeText(getContext(),"User Information can't update",Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            }
        });
    }

    private void saveInfoWithImage(String imageUrl){
        profile = toUserProfile.toModel(editedProfile);
        profile.setName(binding.name.getText().toString().trim());
        profile.setEmail(binding.email.getText().toString().trim());
        profile.setPhone(binding.phone.getText().toString().trim());
        profile.setAddress(binding.address.getText().toString().trim());
        profile.setIc(binding.ic.getText().toString().trim());
        profile.setImage(imageUrl);
        viewModel.updateProfile(profile).observe(getViewLifecycleOwner(), profileResource -> {
            if(profileResource !=null){
                switch (profileResource.status) {
                    case SUCCESS:
                        Toast.makeText(getContext(),"User Information updated",Toast.LENGTH_SHORT).show();
                        updateCompleted();
                        break;
                    case ERROR:
                        Toast.makeText(getContext(),"User Information can't update",Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            }
        });
    }

    private void updateCompleted(){
        navController.popBackStack();
    }

    /**
     * @method for image pickup
     * */
    private void pickFile() {

        String[] mimeTypes =
                {"image/*"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType(mimeTypes[0]);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        imagePickActivityResultLauncher.launch(Intent.createChooser(intent, "Select File"));
    }

    /**
     * new Way for activity for result
     * */
    ActivityResultLauncher<Intent> imagePickActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        if (data == null) {
                            //Display an error msg in ui
                            return;
                        }
                        try {
                            FILE_URL = data.getData();
                            ImageStoreName = ("File".concat(" ") + System.currentTimeMillis() + "." + getFileExtension(FILE_URL));
                            String fileSize = getFileSize(FILE_URL);
                            binding.showSelectedFileSize.setText(fileSize.concat(" ").concat("Has been selected"));
                            requestManager.load(FILE_URL)
                                    .centerCrop()
                                    .into(binding.userImage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    private String getFileExtension(Uri uri) {
        if(getActivity() == null){
            return "";
        }
        ContentResolver FileEXT =  getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(FileEXT.getType(uri));

    }

    private  String getFileSize(Uri fileName){
        if(getActivity() == null ){
            return "";
        }
        ContentResolver FileEXT =  getActivity().getContentResolver();
        @SuppressLint("Recycle") Cursor returnCursor = FileEXT.query(fileName, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        fileSizeCheck =(int)((returnCursor.getLong(sizeIndex))/(1024));
        return "File Name : "+returnCursor.getString(nameIndex)+ "\nFile Size : "+(returnCursor.getLong(sizeIndex))/(1024)+" KB";
    }



}