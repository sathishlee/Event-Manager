package com.satvatinfosole.ssss.sangam.model.responseModel;

import java.util.List;

/**
 * Created by SATHISH on 10/30/2018.
 */
public class MediaEventResponseModel {

    public List<MediaEventResponseModel.Media_details> getMedia_details() {
        return Media_details;
    }

    public void setMedia_details(List<MediaEventResponseModel.Media_details> media_details) {
        Media_details = media_details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private List<Media_details> Media_details;
    private String message;
    private String status;

    public static class Media_details {
        private String media_status;
        private String media_video;
        private String media_audio;
        private String media_photo;
        private String media_title;
        private String media_venue;
        private String media_date;
        private String media_id;

        public String getMedia_status() {
            return media_status;
        }

        public void setMedia_status(String media_status) {
            this.media_status = media_status;
        }

        public String getMedia_video() {
            return media_video;
        }

        public void setMedia_video(String media_video) {
            this.media_video = media_video;
        }

        public String getMedia_audio() {
            return media_audio;
        }

        public void setMedia_audio(String media_audio) {
            this.media_audio = media_audio;
        }

        public String getMedia_photo() {
            return media_photo;
        }

        public void setMedia_photo(String media_photo) {
            this.media_photo = media_photo;
        }

        public String getMedia_title() {
            return media_title;
        }

        public void setMedia_title(String media_title) {
            this.media_title = media_title;
        }

        public String getMedia_venue() {
            return media_venue;
        }

        public void setMedia_venue(String media_venue) {
            this.media_venue = media_venue;
        }

        public String getMedia_date() {
            return media_date;
        }

        public void setMedia_date(String media_date) {
            this.media_date = media_date;
        }

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }
    }
}
