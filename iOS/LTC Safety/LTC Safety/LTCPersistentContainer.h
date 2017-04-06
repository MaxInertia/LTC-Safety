//
//  LTCPersistentContainer.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>
/**
 The LTCPersistentContainer class is an iOS 9 compatible version of the NSPersistentContainer class introduced in iOS 9.
 Its purpose is to load a managed object model from file and set up a persistent store and managed object context that
 can be used to save and load entities in that model.
 
 History properties: Instances of this class should not vary from the time they are created.
 
 Invariance properties: This class assumes that the viewContext, managedObjectModel, and storeCoordinator are valid and non-nil.
 
 */
@interface LTCPersistentContainer : NSObject
/**
 This is the designated initializer used to initialize the view contaxt, managed object model, and store coordinater with the specified xcdatamodel
 
 @param name The name of the xcdatamodel file located within the project resources.
 @return A persistent container initialized with the given name.
 */
- (instancetype)initWithName:(NSString *)name;
/**
 The managed object context used for loading and saving entites.
 */
@property (strong, readonly) NSManagedObjectContext *viewContext;
/**
 The object model representing the xcdatamodel the container was initialized with.
 */
@property (strong, readonly) NSManagedObjectModel *managedObjectModel;
/**
 The store coordinator that the managed object context belongs to.
 */
@property (strong, readonly) NSPersistentStoreCoordinator *storeCoordinator;
@end
