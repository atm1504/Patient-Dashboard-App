package com.atm1504.gosocio.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitApi {

    @Multipart
    @POST("/doctors/signin")
    fun loginDoctor(@Part("email") email: RequestBody, @Part("password") password: RequestBody): Call<LoginResponse>

    @Multipart
    @POST("/patients/{id}/record")
    fun uploadPrescriptionbyId(@Part("symptoms") symptoms: RequestBody, @Part("medicine") medicine: RequestBody, @Part("dose") dose: RequestBody, @Part("date") date: RequestBody, @Path("id") userId :String): Call<PrescriptionResponse>

    @Multipart
    @POST("/patients/signin")
    fun loginPatient(@Part("email") email: RequestBody, @Part("password") password: RequestBody): Call<LoginResponse>

    @Multipart
    @POST("/doctors/signup")
    fun signupDoctor(
        @Part("email") email: RequestBody, @Part("password") password: RequestBody, @Part("name") name: RequestBody, @Part(
            "phone"
        ) phone: RequestBody, @Part("days") days: RequestBody,
        @Part("time") time: RequestBody, @Part("venue") venue: RequestBody, @Part("specialization") specialization:RequestBody
    ): Call<SignupResponse>

    @Multipart
    @POST("/patients/signup")
    fun signupPatient(
        @Part("email") email: RequestBody, @Part("password") password: RequestBody, @Part("name") name: RequestBody, @Part(
            "phone"
        ) phone: RequestBody, @Part("days") days: RequestBody,
        @Part("time") time: RequestBody, @Part("venue") venue: RequestBody  ): Call<SignupResponse>

    @Multipart
    @POST("reports.json")
    fun getReportedTasks(
        @Part("email") email: RequestBody, @Part("access_token") access_token: RequestBody
    ): Call<ReportedTasksResponse>

    @Multipart
    @POST("report.json")
    fun getReport(
        @Part("email") email: RequestBody, @Part("access_token") access_token: RequestBody, @Part("id") id: RequestBody
    ): Call<ReportResponse>

    @GET("roads.json")
    fun getRoads(): Call<RoadsResponse>

    @Multipart
    @POST("submitReport")
    fun submitReport(
        @Part("email") email: RequestBody, @Part("access_token") access_token: RequestBody, @Part("complain") complain: RequestBody, @Part(
            "road_name"
        ) road_name: RequestBody, @Part("latitude") latitude: RequestBody, @Part("longitude") longitude: RequestBody, @Part(
            "is_at_site"
        ) is_at_site: RequestBody, @Part("image") image: RequestBody
    ): Call<SubmitReportResponse>

    companion object Factory {
        fun create(): RetrofitApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://atm1504.in/json/")
                .build()

            return retrofit.create(RetrofitApi::class.java)
        }
    }
}