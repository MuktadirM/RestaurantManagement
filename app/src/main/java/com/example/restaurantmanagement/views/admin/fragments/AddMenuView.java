package com.example.restaurantmanagement.views.admin.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.RequestManager;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentAddMenuViewBinding;
import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.food.FoodViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link AddMenuView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMenuView extends DaggerFragment {
    private FragmentAddMenuViewBinding binding;
    private FoodViewModel viewModel;

    private int fileSizeCheck = 0;
    private Uri FILE_URL;
    private String ImageStoreName;
    private NavController navController;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    public AddMenuView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddMenuView.
     */
    public static AddMenuView newInstance() {
        return new AddMenuView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(FoodViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMenuViewBinding.inflate(inflater, container, false);

        binding.uploadImage.setOnClickListener(v -> pickFile());
        binding.addFood.setOnClickListener( v-> {
            if(FILE_URL != null && fileSizeCheck < 20000){
                viewModel.uploadFoodImage(FILE_URL,getFileExtension(FILE_URL)).observe(getViewLifecycleOwner(),res->{
                   if(res != null){
                       switch (res.status){
                           case SUCCESS:
                               Snackbar.make(v,"Upload Completed ",Snackbar.LENGTH_SHORT).show();
                               assert res.data != null;
                               saveToDatabase(res.data.getImage());
                               break;
                           case ERROR:
                               Snackbar.make(v,"Fail to upload Image ",Snackbar.LENGTH_SHORT).show();
                               break;
                           case LOADING:
                               Snackbar.make(v,"File uploading...",Snackbar.LENGTH_SHORT).show();
                               break;
                       }
                   }
                });
            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void saveToDatabase(String image) {
        String title = binding.title.getText().toString().trim();
        String price = binding.price.getText().toString().trim();
        String calories = binding.calories.getText().toString().trim();
        String description = binding.description.getText().toString().trim();
        Food food = new Food("","","","",title,Double.parseDouble(price),Double.parseDouble(calories),image,description);
        viewModel.addNewMenu(food).observe(getViewLifecycleOwner(),(foodResource -> {
            if(foodResource != null){
                switch (foodResource.status){
                    case SUCCESS:
                        navController.popBackStack();
                        break;
                    case ERROR:
                        Snackbar.make(requireView(),"Fail to upload Image ",Snackbar.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            }
        }));
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
                                    .into(binding.selectedImage);
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