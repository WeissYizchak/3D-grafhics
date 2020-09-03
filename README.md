![Grade: 100](https://img.shields.io/badge/Grade-100-brightgreen.svg) 
# 3D-grafhics
This is my project in **Introduction to Software Engineering** course.  
I did the project with [@Ruli Yakovson](https://github.com/RulyYakovson).

## Description  
The software has the ability to model  

* basic geometrical shapes such:
    * points
    * vectors  
* geometric shapes in 3D:
    * square
    * circle
    * triangle
    * ball
    * cylinder

* sources of lighting such:
    * point lights
    * spote
    * direction (Model for ultra-powerful lighting types).

The software describes a scene composed of many geometric shapes, and many lights,  
then the software simulates taking a picture of the scene and creates a picture of it in a jpg file.  
The picture will resemble the reality considering color, light, shade, transparency and reflection.

## How It Works
step 1:  
Each geometry implements a function that receives a ray (starting point + vector) and returns the point in the space in which the ray strikes, so when we want to take a picture, for each pixel in the image we want to create we sends a ray from the same pixel and then check if the ray striks some geometries, if there is more than one we find the nearest intersection point to the camera.

step 2:  
culculate the color and light of the intersection point.

step 3:  
culculate the shadow of the point.

step 4:  
culculate the transparency and reflection of the intersection point.

## Performence
the app Implement the 3D-DDA algorithm to save performence.

## Exsampels
<img src="https://user-images.githubusercontent.com/35064970/92090860-a1b60180-edd8-11ea-8452-bd735872b0bc.png" alt="drawing" width="200"/>
<img src="https://user-images.githubusercontent.com/35064970/92091728-cf4f7a80-edd9-11ea-9e6b-32f5534017e3.png" alt="drawing" width="200"/>
<img src="https://user-images.githubusercontent.com/35064970/92091846-fc039200-edd9-11ea-8c33-3d70d7503971.png" alt="drawing" width="200"/>


