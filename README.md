# NewFlockingProject
Planning doc link: https://docs.google.com/document/d/1Va3hJjfK6OiS179qVQ9UYMJPxPHp6qo0UptmhXIeEW4/edit?usp=sharing

In this project we will be creating a flocking simulation that flocks circles to different direections. Everytime a circle is added the circle follws the same direction of the circles which creates a flocking pattern. these boids will bounce off the walls and keep flocking.

First Create a Branch. Here are some instructions on how to do that.

Create branch (only need to do once) git checkout -b dev git push --set-upstream origin dev

Make other branches git checkout dev git checkout -b issue1-class-Item git push --set-upstream origin issue1-class-Item

Finish with branch git checkout dev git merge issue1-class-Item git add * git push

Here are the Methods we used in this project.

# Circle Model Class

cohesion (). Determines the average position of each boid and returns theta.

alignment(). Determines the average direction of each boid and returns theta.

separation(). Determines how far the boids are and moves them away from each other.

newDirection() points the boids in a final direction.

# Vector Class

Normalize the Vectors 

# GUI(Sliders)

Cohesion

Alignment

Separation


