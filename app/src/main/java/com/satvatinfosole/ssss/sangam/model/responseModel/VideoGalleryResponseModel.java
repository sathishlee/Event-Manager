package com.satvatinfosole.ssss.sangam.model.responseModel;

import java.util.List;

/**
 * Created by SATHISH on 10/31/2018.
 */
public class VideoGalleryResponseModel {

    private List<Video_Gallery_List> Video_Gallery_List;
    private String message;

    public List<VideoGalleryResponseModel.Video_Gallery_List> getVideo_Gallery_List() {
        return Video_Gallery_List;
    }

    public void setVideo_Gallery_List(List<VideoGalleryResponseModel.Video_Gallery_List> video_Gallery_List) {
        Video_Gallery_List = video_Gallery_List;
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

    private String path;
    private String status;


    public static class Video_Gallery_List {
        private String vg_position;
        private String vg_user_permission;
        private String vg_video_name;
        private String vg_title;
        private String vg_mediavenue_id;
        private String vg_media_id;
        private String vg_id;

        public String getVg_position() {
            return vg_position;
        }

        public void setVg_position(String vg_position) {
            this.vg_position = vg_position;
        }

        public String getVg_user_permission() {
            return vg_user_permission;
        }

        public void setVg_user_permission(String vg_user_permission) {
            this.vg_user_permission = vg_user_permission;
        }

        public String getVg_video_name() {
            return vg_video_name;
        }

        public void setVg_video_name(String vg_video_name) {
            this.vg_video_name = vg_video_name;
        }

        public String getVg_title() {
            return vg_title;
        }

        public void setVg_title(String vg_title) {
            this.vg_title = vg_title;
        }

        public String getVg_mediavenue_id() {
            return vg_mediavenue_id;
        }

        public void setVg_mediavenue_id(String vg_mediavenue_id) {
            this.vg_mediavenue_id = vg_mediavenue_id;
        }

        public String getVg_media_id() {
            return vg_media_id;
        }

        public void setVg_media_id(String vg_media_id) {
            this.vg_media_id = vg_media_id;
        }

        public String getVg_id() {
            return vg_id;
        }

        public void setVg_id(String vg_id) {
            this.vg_id = vg_id;
        }
    }
}
