package com.example.ridkins.userplaceholder.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Rud on 8/5/17.
 */
public class Address{
  @SerializedName("zipcode")
  @Expose
  private String zipcode;
  @SerializedName("geo")
  @Expose
  private Geo geo;
  @SerializedName("suite")
  @Expose
  private String suite;
  @SerializedName("city")
  @Expose
  private String city;
  @SerializedName("street")
  @Expose
  private String street;
  public void setZipcode(String zipcode){
   this.zipcode=zipcode;
  }
  public String getZipcode(){
   return zipcode;
  }
  public void setGeo(Geo geo){
   this.geo=geo;
  }
  public Geo getGeo(){
   return geo;
  }
  public void setSuite(String suite){
   this.suite=suite;
  }
  public String getSuite(){
   return suite;
  }
  public void setCity(String city){
   this.city=city;
  }
  public String getCity(){
   return city;
  }
  public void setStreet(String street){
   this.street=street;
  }
  public String getStreet(){
   return street;
  }
}