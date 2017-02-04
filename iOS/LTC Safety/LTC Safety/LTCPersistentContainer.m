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

@end
