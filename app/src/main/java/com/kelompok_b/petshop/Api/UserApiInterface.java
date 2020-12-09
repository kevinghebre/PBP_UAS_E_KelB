package com.kelompok_b.petshop.Api;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiInterface {

//    Users
    @GET("users")
    Call<UserResponse> getAllUser();

    @GET("users/{id}")
    Call<UserResponse> getUser(@Path("id") String id);


    @POST("login")
    @FormUrlEncoded
    Call<UserResponse> loginUser(@Field("email") String email,
                                 @Field("password") String password);

    @POST("register")
    @FormUrlEncoded
    Call<UserResponse> registerUser(@Field("name") String nama,
                                    @Field("gender") String gender,
                                    @Field("age") String age,
                                    @Field("image") String image,
                                    @Field("email") String email,
                                    @Field("password") String password);

    @PUT("users/{id}")
    @FormUrlEncoded
    Call<UserResponse> updateUser(@Path("id") String id,
                                  @Field("name") String name,
                                  @Field("gender") String gender,
                                  @Field("age") Double age,
                                  @Field("image") String image);

    //    Pet
    @GET("pet")
    Call<UserResponse> getAllPet();

    @GET("pet/{id}")
    Call<UserResponse> searchPet(@Path("id") String id);

//    @GET("petCat")
//    Call<UserResponse> showCat(@Path("id") String id,
//                               @Field("name") String name,
//                               @Field("gender") String gender,
//                               @Field("age") Double age,
//                               @Field("image") String image);
//
//    @GET("petDog")
//    Call<UserResponse> showDog(@Path("id") String id,
//                               @Field("name") String name,
//                               @Field("gender") String gender,
//                               @Field("age") Double age,
//                               @Field("image") String image);

    @POST("pet")
    Call<UserResponse> addPet(@Path("id") String id,
                              @Field("categori") String categori,
                              @Field("type_name") String type_name,
                              @Field("pet_image") String pet_image,
                              @Field("pet_name") String pet_name,
                              @Field("age") Integer age,
                              @Field("gender") Double gender,
                              @Field("weight") Double weight,
                              @Field("price") Double price);

    @PUT("pet/{id}")
    Call<UserResponse> updatePet(@Path("id") String id,
                                 @Field("categori") String categori,
                                 @Field("type_name") String type_name,
                                 @Field("pet_image") String pet_image,
                                 @Field("pet_name") String pet_name,
                                 @Field("age") Integer age,
                                 @Field("gender") Double gender,
                                 @Field("weight") Double weight,
                                 @Field("price") Double price);

    @DELETE("pet/{id}")
    Call<UserResponse> deletePet(@Path("id") String id);


    //    Food
    @GET("food")
    Call<UserResponse> showAllFood(@Path("id") String id);

    @GET("food/{id}")
    Call<UserResponse> searchFood(@Path("id") String id,
                                  @Field("name") String name,
                                  @Field("gender") String gender,
                                  @Field("age") Double age,
                                  @Field("image") String image);

//    @GET("foodCat")
//    Call<UserResponse> showCatFood(@Path("id") String id,
//                                   @Field("name") String name,
//                                   @Field("gender") String gender,
//                                   @Field("age") Double age,
//                                   @Field("image") String image);
//
//    @GET("foodDog")
//    Call<UserResponse> showDogFood(@Path("id") String id,
//                                   @Field("name") String name,
//                                   @Field("gender") String gender,
//                                   @Field("age") Double age,
//                                   @Field("image") String image);

    @GET("food/{id}")
    Call<UserResponse> searchFood(@Path("id") String id);

    @PUT("food/{id}")
    Call<UserResponse> updateFood(@Path("id") String id,
                                  @Field("category") String category,
                                  @Field("food_name") String food_name,
                                  @Field("supplier") String supplier,
                                  @Field("food_image") String food_image,
                                  @Field("calories") Double calories,
                                  @Field("price") Double price,
                                  @Field("net_weight") Double net_weight,
                                  @Field("stock") Integer stock);

    @DELETE("food/{id}")
    Call<UserResponse> deleteFood(@Path("id") String id);
}
