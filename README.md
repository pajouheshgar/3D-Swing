# 3D-Swing
This library is an API to plot 3D shapes in java, using swing. 
Everything is implemented from scratch, from Vector2, and Vector3 Classes
to Projection, Camera, and AnimatedCharacter3D classes.

### Brief Documentation
* Vector2, Mesh2D classes represent 2D points and surfaces with screen coordinates.
* Vector3 class represent a 3D vector and  contains some basic mathematical operations on vectors.
* Mesh3D class represent a 3D polygon surface which is itself defined by its nodes.
* Object3D class represent a basic 3D object which is itself defined by a list of Mesh3D objects.
* Object3DFactory class contains some basic 3D objects such as Cube, Sphere, Pyramid, and ... .
* Character3D class a complex 3D object which is itself defined by a list of list of Object3D objects.
* Character3DFactory class contains 3D characters like a simple human, prison door, key, and ... .
* AnimatedCharacter3D class represent a 3D character which an animation is added to it like rotating or scaling.
* AnimatedCharacter3DFactory class contains some AnimatedCharacter3D objects like rotating key and opening door.
* Camera class represent a camera with its up vector and field of view and looking point.
* Pointlight class represent a point light with its position and its intensity.
* Projection class is responsible to map 3D vectors and 3D meshes
to 2D points and 2D Meshes on screen based on a camera.  Two options are possible, perspective 
projection and orthogonal projection. If point light is enabled it also sets the color of the project meshes.
* ZBuffer class stores distance from camera for each projected mesh and its used to 
plot objects with correct order to avoid collision

### Example
You can run the main function in GameEnv3D class. Its a game environment implemented by 3D Swing api.
You can set the firstPerson parameter true to run the game in first person mode.
You can also enable the Skybox. Some basic keyboard event handles are implemented such as moving the character
and its neck. Proper sounds (which are adopted from Warcraft game!) are added to game environment
according to character actions. I hope this code helps you to make your own game environment using Swing and 3DSwing api.


![third person screenshot](Pictures/3D%20Game%20Environment/3rd%20person.PNG)


![first person screenshot](Pictures/3D%20Game%20Environment/first%20person.PNG)


