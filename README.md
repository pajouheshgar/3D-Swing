# 3D-Swing
This library is an API to plot 3D shapes in java using the swing library. 
Everything is implemented from scratch, from Vector2, and Vector3 Classes
to Projection, Camera, and AnimatedCharacter3D classes.

### Brief Documentation
* Vector2, Mesh2D classes represent 2D points and surfaces with screen coordinates.
* Vector3 class represents a 3D vector and contains some basic mathematical operations on vectors.
* Mesh3D class represents a 3D polygon surface, which is itself defined by its nodes.
* Object3D class represents a basic 3D object, which is itself defined by a list of Mesh3D objects.
* Object3DFactory class contains some basic 3D objects such as Cube, Sphere, Pyramid, and ...
* Character3D class a complex 3D object which is itself defined by a list of Object3D objects.
* Character3DFactory class contains 3D characters like a simple human, prison door, key, and ...
* AnimatedCharacter3D class represents a 3D character in which an animation is added to it like rotating or scaling.
* AnimatedCharacter3DFactory class contains some AnimatedCharacter3D objects like rotating keys and opening doors.
* Camera class represents a camera with its up vector and field of view and looking point.
* PointLight class represents a point light with its position and its intensity.
* Projection class is responsible for mapping 3D vectors and 3D meshes
to 2D points and 2D Meshes on a screen based on a camera.  Two options are possible, perspective projection and orthogonal projection. If point light is enabled, it also sets the color of the project meshes.
* ZBuffer class stores distance from the camera for each projected mesh and it's used to 
plot objects with correct order to avoid a collision

### Example
You can run the main function in the GameEnv3D class. Its a simple game environment implemented by 3D Swing API.
You can set the firstPerson parameter to run the game in the first-person mode.
You can also enable the Skybox. Some basic keyboard event handles are implemented, such as moving the character
and its neck and changing direction. Proper sounds from the Warcraft III game will be played during the game, according to the character's actions. I hope this code helps you to build your own game environment using Swing and 3DSwing API.


![third person screenshot](Pictures/3D%20Game%20Environment/3rd%20person.PNG)


![first person screenshot](Pictures/3D%20Game%20Environment/first%20person.PNG)


