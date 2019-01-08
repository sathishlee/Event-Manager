package com.satvatinfosole.ssss.sangam.model.responseModel;

import java.util.List;

/**
 * Created by SATHISH on 10/30/2018.
 */
public class PhotoGalleryResponseModel {


    private List<Photo_Gallery_List> Photo_Gallery_List;
    private String message;
    private String path;
    private String status;

    public List<PhotoGalleryResponseModel.Photo_Gallery_List> getPhoto_Gallery_List() {
        return Photo_Gallery_List;
    }

    public void setPhoto_Gallery_List(List<PhotoGalleryResponseModel.Photo_Gallery_List> photo_Gallery_List) {
        Photo_Gallery_List = photo_Gallery_List;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public static class Photo_Gallery_List {
        private String pg_position;
        private String pg_user_permission;
        private String pg_image_name;
        private String pg_title;
        private String pg_mediavenue_id;
        private String pg_media_id;
        private String pg_id;

        public String getPg_position() {
            return pg_position;
        }

        public void setPg_position(String pg_position) {
            this.pg_position = pg_position;
        }

        public String getPg_user_permission() {
            return pg_user_permission;
        }

        public void setPg_user_permission(String pg_user_permission) {
            this.pg_user_permission = pg_user_permission;
        }

        public String getPg_image_name() {
            return pg_image_name;
        }

        public void setPg_image_name(String pg_image_name) {
            this.pg_image_name = pg_image_name;
        }

        public String getPg_title() {
            return pg_title;
        }

        public void setPg_title(String pg_title) {
            this.pg_title = pg_title;
        }

        public String getPg_mediavenue_id() {
            return pg_mediavenue_id;
        }

        public void setPg_mediavenue_id(String pg_mediavenue_id) {
            this.pg_mediavenue_id = pg_mediavenue_id;
        }

        public String getPg_media_id() {
            return pg_media_id;
        }

        public void setPg_media_id(String pg_media_id) {
            this.pg_media_id = pg_media_id;
        }

        public String getPg_id() {
            return pg_id;
        }

        public void setPg_id(String pg_id) {
            this.pg_id = pg_id;
        }
    }
}
