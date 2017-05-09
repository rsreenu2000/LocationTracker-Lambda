package com.amazonaws.lambda.demo;

public class RequestClass {

   String latitude;
   String longitude;
   String accuracy;

   public RequestClass() {
   }
   
   public RequestClass(String latitude, String longitude, String accuracy) {
       this.latitude = latitude;
       this.longitude = longitude;
       this.accuracy = accuracy;
   }

   public String getlatitude() {
       return latitude;
   }

   public void setlatitude(String latitude) {
       this.latitude = latitude;
   }

   public String getlongitude() {
       return longitude;
   }

   public void setlongitude(String longitude) {
       this.longitude = longitude;
   }

   public String getaccuracy() {
       return accuracy;
   }

   public void setaccuracy(String accuracy) {
       this.accuracy = accuracy;
   }
}
