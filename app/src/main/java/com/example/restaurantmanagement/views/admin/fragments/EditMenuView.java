package com.example.restaurantmanagement.views.admin.fragments;

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
import com.example.restaurantmanagement.databinding.FragmentEditMenuViewBinding;
import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.food.FoodViewModel;
import com.example.restaurantmanagement.views.models.FoodModel;
import com.example.restaurantmanagement.views.models.mapping.FoodModelMapping;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.example.restaurantmanagement.utils.Constrains.FOOD_EDIT;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link EditMenuView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditMenuView extends DaggerFragment {
    private FragmentEditMenuViewBinding binding;
    private FoodViewModel viewModel;
    private FoodModel foodModel;

    private NavController navController;

    private int fileSizeCheck = 0;
    private Uri FILE_URL;
    private String ImageStoreName;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    public EditMenuView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditMenuView.
     */
    // TODO: Rename and change types and number of parameters
    public static EditMenuView newInstance() {
        return new EditMenuView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            foodModel = getArguments().getParcelable(FOOD_EDIT);
        }
        viewModel = new ViewModelProvider(this,providerFactory).get(FoodViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditMenuViewBinding.inflate(inflater,container,false);
        binding.setFood(foodModel);

        binding.uploadImage.setOnClickListener(v -> pickFile());

        binding.edit.setOnClickListener(v->{
            if(FILE_URL != null){
                processImageToUpload();
            }else {
                saveToDatabase(null);
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.cancel.setOnClickListener(v->navController.popBackStack());
    }

    private void processImageToUpload(){
        if(FILE_URL != null && fileSizeCheck < 20000){
            viewModel.uploadFoodImage(FILE_URL,getFileExtension(FILE_URL)).observe(getViewLifecycleOwner(),res->{
                if(res != null){
                    switch (res.status){
                        case SUCCESS:
                            Snackbar.make(getView(),"Upload Completed ",Snackbar.LENGTH_SHORT).show();
                            assert res.data != null;
                            saveToDatabase(res.data.getImage());
                            break;
                        case ERROR:
                            Snackbar.make(getView(),"Fail to upload Image ",Snackbar.LENGTH_SHORT).show();
                            break;
                        case LOADING:
                            Snackbar.make(getView(),"File uploading...",Snackbar.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }
    }

    private void saveToDatabase(String image) {
        String foodImage;
        if(image == null){
            foodImage = foodModel.getImage();
        }else {
            foodImage = image;
        }
        String title = binding.title.getText().toString().trim();
        String price = binding.price.getText().toString().trim();
        String calories = binding.calories.getText().toString().trim();
        String description = binding.description.getText().toString().trim();
        FoodModelMapping foodModelMapping = new FoodModelMapping();
        Food food = foodModelMapping.toDomain(foodModel);
        food.setImage(foodImage);
        food.setTitle(title);
        food.setPrice(Double.parseDouble(price));
        food.setCalories(Double.parseDouble(calories));
        food.setDescription(description);
        viewModel.updateFood(food).observe(getViewLifecycleOwner(),data->{
            if(data != null){
                switch (data.status){
                    case SUCCESS:
                        navController.popBackStack();
                        break;
                    case ERROR:
                        break;
                    case LOADING:
                        break;
                }
            }
        });
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