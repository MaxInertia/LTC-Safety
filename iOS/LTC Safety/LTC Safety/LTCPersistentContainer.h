//
//  LTCPersistentContainer.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@interface LTCPersistentContainer : NSObject
- (instancetype)initWithName:(NSString *)name;
@property (strong, readonly) NSManagedObjectContext *viewContext;
@property (strong, readonly) NSManagedObjectModel *managedObjectModel;
@property (strong, readonly) NSPersistentStoreCoordinator *storeCoordinator;
@end
