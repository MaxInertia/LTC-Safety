//
//  LTCPersistentContainer.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCPersistentContainer.h"
#import <CoreData/CoreData.h>

@interface LTCPersistentContainer ()
@property (strong, readwrite) NSManagedObjectContext *viewContext;
@property (strong, readwrite) NSManagedObjectModel *managedObjectModel;
@property (strong, readwrite) NSPersistentStoreCoordinator *storeCoordinator;
@end

@implementation LTCPersistentContainer

- (NSURL *)_peristentStoreURLForName:(NSString *)name {
    
    NSAssert(name.length > 0, @"Attempted to get the url for a persistant store with an empty name.");
    
    NSURL *documentsUrl = [[[NSFileManager defaultManager] URLsForDirectory:NSDocumentDirectory inDomains:NSUserDomainMask] lastObject];
    
    NSAssert(documentsUrl != nil, @"Received invalid documents URL for the persistent store");
    
    NSString *storeFileName = [name stringByAppendingPathExtension:@"sqlite"];
    
    return [documentsUrl URLByAppendingPathComponent:storeFileName];
}

- (instancetype)initWithName:(NSString *)name {
    if (self = [super init]) {
        
        self.managedObjectModel = [self _loadObjectModelWithName:name];
        
        NSDictionary *entities = self.managedObjectModel.entitiesByName;
        NSAssert(entities[@"LTCConcern"] != nil, @"Unable to locate LTCConcern in object model.");
        NSAssert(entities[@"LTCReporter"] != nil, @"Unable to locate LTCReporter in object model.");
        NSAssert(entities[@"LTCLocation"] != nil, @"Unable to locate LTCLocation in object model.");
        NSAssert(entities[@"LTCConcernStatus"] != nil, @"Unable to locate LTCConcernStatus in object model.");
        
        NSError *error = nil;
        NSURL *storeURL = [self _peristentStoreURLForName:name];
        self.storeCoordinator = [self _loadStoreCoordinatorAtURL:storeURL withObjectModel:self.managedObjectModel error:&error];
        
        NSAssert1(self.storeCoordinator.persistentStores.count == 1, @"Unexpected number of stores for coordinator: %lu", self.storeCoordinator.persistentStores.count);
        
        self.viewContext = [self _loadObjectContextWithStoreCoordinator:self.storeCoordinator];
    }
    NSAssert(self.managedObjectModel, @"Persistant container finished initialization with a nil object model.");
    NSAssert(self.storeCoordinator, @"Persistant container finished initialization with a nil store coordinator.");
    NSAssert(self.storeCoordinator, @"Persistant container finished initialization with a nil managed object context.");
    
    return self;
}

- (NSManagedObjectModel *)_loadObjectModelWithName:(NSString *)name {
    
    NSAssert(self.managedObjectModel == nil, @"Attempted to load a managed object model when one already exists.");
    NSAssert(name.length > 0, @"Attempted to load an object model with an empty name.");
    
    NSURL *url = [[NSBundle mainBundle] URLForResource:name withExtension:@"momd"];
    
    NSAssert(url != nil, @"Unabled to locate object model named: %@", name);
    
    return [[NSManagedObjectModel alloc] initWithContentsOfURL:url];
}

- (NSPersistentStoreCoordinator *)_loadStoreCoordinatorAtURL:(NSURL *)url withObjectModel:(NSManagedObjectModel *)model error:(NSError **)error {
    
    NSAssert(url != nil, @"Attempted to create a store coordinator with a nil url.");
    NSAssert(model != nil, @"Attempted to create a store coordinator with a nil object model.");
    NSAssert(error != nil, @"The load store coordinator error is not being handled.");
    
    NSError *existingCoordinatorError = nil;
    NSPersistentStoreCoordinator *storeCoordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:model];
    [storeCoordinator addPersistentStoreWithType:NSSQLiteStoreType configuration:nil URL:url options:nil error:&existingCoordinatorError];
    
    // Failed to load the data from file, the data may be corrupt
    if (existingCoordinatorError != nil) {
        
        // Remove the corrupt sqlite data and try to load it again
        [[NSFileManager defaultManager] removeItemAtURL:url error:nil];
        
        NSError *newCoordinatorError = nil;
        storeCoordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:model];
        [storeCoordinator addPersistentStoreWithType:NSSQLiteStoreType configuration:nil URL:url options:nil error:&newCoordinatorError];
        
        // Failed to load a datastore after cleaning the data
        if (newCoordinatorError != nil) {
            
            // Return an in-memory store coordinator
            storeCoordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:model];
            [storeCoordinator addPersistentStoreWithType:NSInMemoryStoreType configuration:nil URL:nil options:nil error:error];
            return storeCoordinator;
        } else {
            
            // Return the newly cleaned coordinator
            return storeCoordinator;
        }
    } else {
        // Return the loaded coordinator from file
        return storeCoordinator;
    }
}

- (NSManagedObjectContext *)_loadObjectContextWithStoreCoordinator:(NSPersistentStoreCoordinator *)storeCoordinator {
    
    NSAssert(storeCoordinator != nil, @"Attempted to load the managed object context with a nil store coordinator.");
    
    NSManagedObjectContext *context = [[NSManagedObjectContext alloc] initWithConcurrencyType:NSMainQueueConcurrencyType];
    context.persistentStoreCoordinator = storeCoordinator;
    return context;
}

@end
