package com.example.restaurantmanagement.domain.utils;

import android.net.Uri;

import javax.annotation.Nonnull;

public class FileUploadHelper {
    private Uri uri;
    private String fileExt;
    private String path = "/";
    private boolean isRelated = false;

    public FileUploadHelper() {
    }

    public FileUploadHelper(@Nonnull  Uri uri, @Nonnull String fileExt) {
        this.uri = uri;
        this.fileExt = fileExt;
    }

    public FileUploadHelper(@Nonnull Uri uri,@Nonnull String fileExt, String path) {
        this.uri = uri;
        this.fileExt = fileExt;
        this.path = path;
    }

    public FileUploadHelper(@Nonnull Uri uri,@Nonnull String fileExt, String path, boolean isRelated) {
        this.uri = uri;
        this.fileExt = fileExt;
        this.path = path;
        this.isRelated = isRelated;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRelated() {
        return isRelated;
    }

    public void setRelated(boolean related) {
        isRelated = related;
    }
}
