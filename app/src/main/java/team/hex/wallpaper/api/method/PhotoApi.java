package team.hex.wallpaper.api.method;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import team.hex.wallpaper.api.model.Photo;

/**
 * Created by alireza on 6/30/17.
 */

public interface PhotoApi {

    String ORDER_BY_LATEST = "latest";
    String ORDER_BY_OLDEST = "oldest";
    String ORDER_BY_POPULAR = "popular";

    @GET("photos")
    Call<List<Photo>> getPhotos(@Query("page") int page,
                                @Query("per_page") int per_page,
                                @Query("order_by") String order_by);

    @GET("photos/curated")
    Call<List<Photo>> getCuratedPhotos(@Query("page") int page,
                                       @Query("per_page") int per_page,
                                       @Query("order_by") String order_by);

    @GET("categories/{id}/photos")
    Call<List<Photo>> getPhotosInAGivenCategory(@Path("id") int id,
                                                @Query("page") int page,
                                                @Query("per_page") int per_page);

    @GET("photos/{id}")
    Call<Photo> getAPhoto(@Path("id") String id);

    @GET("photos/{id}")
    Call<Photo> getAPhoto(@Path("id") String id,
                          @Query("w") int w,
                          @Query("h") int h);


    @GET("collections/{id}/photos")
    Call<List<Photo>> getCollectionPhotos(@Path("id") int id,
                                          @Query("page") int page,
                                          @Query("per_page") int per_page);

    @GET("collections/curated/{id}/photos")
    Call<List<Photo>> getCuratedCollectionPhotos(@Path("id") int id,
                                                 @Query("page") int page,
                                                 @Query("per_page") int per_page);

    @GET("photos/random")
    Call<List<Photo>> getRandomPhotos(@Query("tag") Integer categoryId,
                                      @Query("featured") Boolean featured,
                                      @Query("username") String username,
                                      @Query("query") String query,
                                      @Query("orientation") String orientation,
                                      @Query("count") int count);
}
