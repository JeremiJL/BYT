## Repositories
I have to put extra effort into how repositories are implemented. As much boilerplate and
reusable logic should be implemented in the abstract interfaces.

Additionally I should implement update mechanism that is error prone and ensures that any
changes to the domain classes in the system results in those changes being reflected in the database.

Factory Design Pattern (2 points)  
This creational pattern defines an interface for creating an object but lets the subclasses decide which class to instantiate. The factory method lets a class defer instantiation to subclasses. It provides an interface for creating objects in a superclass but allows subclasses to alter the type of objects that will be created.

In our project, there are several database systems we want to support underneath a service called DataAnalyticsEngine. To make things easy, we want to generalize and standardize the way we handle all of them, hence we need a factory to make the database creation and handling process generic. This will allow our analytics service to be written in a database-agnostic way.
 
Adapter Design Pattern (3 points)

This is a structural pattern that allows objects with incompatible interfaces to work together. It acts as a bridge between two incompatible interfaces. This pattern is useful when you want to use existing classes, but their interfaces do not match the one you need.

In our project, we have a simple HR system (a list of employees in a string array) that is supposed to be integrated with a third party billing system (BillingSystem.cs). All you need to do is implement an adapter class (EmployeeAdapter) so that it accepts a given format of data and makes it compatible with a third party billing system (at the end this system should be called in this adapter class with compatible data)


Observer Design Pattern (5 points)

Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically. Widely used for implementing distributed event-handling systems where an object needs to notify other objects about its state changes without knowing who these objects are. In the Observer Design Pattern, an object (called a Subject) maintains a list of its dependents (called Observers). It notifies them automatically whenever any state changes by calling one of their methods. The Other names of this pattern are Producer/Consumer and Publish/Subscribe.

In our project, a simple notification system is to be finished for a mobile phone retailer. You need to implement an Observer class (entity that needs to be notified on a certain trigger) so that it works with the Subject class (message being passed). You need to analyze the information flow in the code and implement only necessary methods in Observer class.
