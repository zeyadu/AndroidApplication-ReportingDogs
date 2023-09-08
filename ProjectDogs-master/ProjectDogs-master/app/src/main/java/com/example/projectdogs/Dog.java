package com.example.projectdogs;

public class Dog {
    private String DogName, DogType, DogFound, DogDescription,DImage, DogId;
    private Long  DogAge;

    public Dog() {
    }
    public Dog(String imguri){
        this.DImage = imguri;
    }

    public String getDogId() {
        return DogId;
    }

    public void setDogId(String dogId) {
        DogId = dogId;
    }

    public String getDogName() {
        return DogName;
    }

    public void setDogName(String dogName) {
        DogName = dogName;
    }

    public String getDogType() {
        return DogType;
    }

    public void setDogType(String dogType) {
        DogType = dogType;
    }

    public String getDogFound() {
        return DogFound;
    }

    public void setDogFound(String dogFound) {
        DogFound = dogFound;
    }

    public String getDogDescription() {
        return DogDescription;
    }

    public void setDogDescription(String dogDescription) {
        DogDescription = dogDescription;
    }

    public Long getDogAge() {
        return DogAge;
    }

    public void setDogAge(Long dogAge) {
        DogAge = dogAge;
    }

    public String getDImage() {
        return DImage;
    }

    public void setDImage(String DImage) {
        this.DImage = DImage;
    }
}
