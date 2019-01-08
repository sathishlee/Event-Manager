package com.satvatinfosole.ssss.sangam.interactor;

/**
 * Created by SATHISH on 10/30/2018.
 */
public interface GalleryInteractor {

      void getMediaEventList();
      void getPhotoThumbnail(String media_id,String media_venu_id);
      void getVideoThumbnail(String media_id, String media_venu_id);

      void getPhotoGalleryList(String media_id,String media_venu_id);
      void getVideoGalleryList(String media_id, String media_venu_id);
}
